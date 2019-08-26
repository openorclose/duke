import command.ByeCommand;
import command.DeleteCommand;
import command.FindCommand;
import command.MarkAsDoneCommand;
import command.PrintListCommand;
import command.task.AddDeadlineTaskCommand;
import command.task.AddEventTaskCommand;
import command.task.AddTodoTaskCommand;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import parser.CommandParser;
import parser.TaskParser;
import store.Store;
import task.Task;
import ui.Ui;

public class Duke {

  private Scanner scanner = new Scanner(System.in);

  private ArrayList<Task> list = new ArrayList<>();

  private Ui ui = new Ui();

  private CommandParser commandParser;

  private TaskParser taskParser;

  private Store store = new Store(ui::error);

  public static void main(String[] args) {
    Duke duke = new Duke();
    duke.printWelcome();
    duke.startRepl();
  }

  public Duke(Ui ui, Store store) {
    this.ui = ui;
    this.store = store;
    taskParser = new TaskParser(ui);
    init();
  }

  public Duke() {
    taskParser = new TaskParser(ui);
    init();
  }

  private void init() {
    commandParser = new CommandParser(ui);
    commandParser.add(new ByeCommand(ui::exit));
    commandParser.add(new PrintListCommand(this::printList));
    commandParser.add(new MarkAsDoneCommand(ui::error, this::findInListThenMarkAsDone));
    commandParser.add(new DeleteCommand(ui::error, this::findInListThenDelete));
    commandParser.add(new AddTodoTaskCommand(ui::error, this::addTaskToList));
    commandParser.add(new AddEventTaskCommand(ui::error, this::addTaskToList));
    commandParser.add(new AddDeadlineTaskCommand(ui::error, this::addTaskToList));
    commandParser.add(new FindCommand(ui, this::findTasksMatchingQuery));
    loadFromDiskToList();
  }

  private void printWelcome() {
    String logo = " ____        _        \n"
        + "|  _ \\ _   _| | _____ \n"
        + "| | | | | | | |/ / _ \\\n"
        + "| |_| | |_| |   <  __/\n"
        + "|____/ \\__,_|_|\\_\\___|\n";
    ui.println("Hello from\n" + logo);
    ui.println("What can I do for you?");

  }

  private void startRepl() {
    while (true) {
      parseAndExecuteCommand(scanner.nextLine().trim());
    }
  }

  public void parseAndExecuteCommand(String command) {
    commandParser.parseAndExecuteCommand(command);
    saveListToDisk();
  }

  private void loadFromDiskToList() {
    Scanner saveFileScanner = store.retrieveDataAsScanner();
    while (saveFileScanner.hasNextLine()) {
      String serialized = saveFileScanner.nextLine();
      list.add(taskParser.fromSerial(serialized));
    }
  }

  private void saveListToDisk() {
    store.saveDataIntoFile(list.stream().map(Task::serialise).collect(Collectors.joining("\n")));
  }

  private void findInListThenMarkAsDone(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    Task item = list.get(itemIndex);
    item.markAsDone();
    ui.println("Nice! I've marked this task as done:");
    ui.println(item.toString());
  }

  private void addTaskToList(Task task) {
    list.add(task);
    ui.println("Got it. I've added this task: ");
    ui.println(task.toString());
  }

  private void findInListThenDelete(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    Task taskToDelete = list.remove(itemIndex);
    ui.println("Noted. I've removed this task: ");
    ui.println(taskToDelete.toString());
    printNumberOfItemsInList();
  }

  private void printNumberOfItemsInList() {
    ui.printf("Now you have %d task(s) in the list.\n", list.size());
  }

  private void printList() {
    int length = list.size();
    if (length == 0) {
      ui.println("Yay! You don't have any tasks!");
    } else if (length == 1) {
      ui.println("You have one task in your list:");
      ui.println("1. " + list.get(0));
    } else {
      ui.println("Here are the tasks in your list:");
      for (int itemIndex = 0; itemIndex < length; itemIndex += 1) {
        int oneBasedIndex = itemIndex + 1;
        ui.printf("%d. %s\n", oneBasedIndex, list.get(itemIndex));
      }
    }
  }

  private ArrayList<Task> findTasksMatchingQuery(String query) {
    return list
        .stream()
        .filter(task -> task.matches(query))
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
