package main.java;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.java.parser.CommandParser;
import main.java.parser.ParserUtils;
import main.java.parser.TaskParser;
import main.java.store.Store;
import main.java.task.DeadlineTask;
import main.java.task.EventTask;
import main.java.task.Task;
import main.java.task.ToDoTask;
import main.java.ui.Ui;

public class Duke {

  private Scanner scanner = new Scanner(System.in);

  private ArrayList<Task> list = new ArrayList<>();

  private CommandParser commandParser = new CommandParser();

  private TaskParser taskParser = new TaskParser();

  public static void main(String[] args) {
    String logo = " ____        _        \n"
        + "|  _ \\ _   _| | _____ \n"
        + "| | | | | | | |/ / _ \\\n"
        + "| |_| | |_| |   <  __/\n"
        + "|____/ \\__,_|_|\\_\\___|\n";
    Ui.println("Hello from\n" + logo);
    Ui.println("What can I do for you?");

    Duke duke = new Duke();
    duke.startRepl();
  }

  public Duke() {
    commandParser.addCommand("bye", unused -> {
      Ui.println("Bye. Hope to see you again soon!");
      System.exit(0);
    });
    commandParser.addCommand("list", unused -> printList());
    commandParser.addCommand("done", ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
      try {
        findInListThenMarkAsDone(oneBasedIndex);
      } catch (IndexOutOfBoundsException e) {
        Ui.error(String.format("Opps! I could not find item %d in your list.\n",
            oneBasedIndex));
      }
    }));
    commandParser
        .addCommand("delete", ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
          try {
            findInListThenDelete(oneBasedIndex);
          } catch (IndexOutOfBoundsException e) {
            Ui.error(String.format("Opps! I could not delete item %d in your list as it does not exist.\n",
                    oneBasedIndex));
          }
        }));
    commandParser.addCommand("todo", description -> {
      if (description.equals("")) {
        Ui.error("Opps! I expected a description of your todo task.");
      } else {
        addTaskToList(new ToDoTask(description));
      }
    });
    commandParser.addCommand("event", argumentsString -> {
      Task task = TaskParser.generateTimedTaskParser(EventTask.ARGUMENTS_SEPARATOR,
          EventTask::new).apply(argumentsString);
      if (task != null) {
        addTaskToList(task);
      }
    });
    commandParser.addCommand("deadline", argumentsString -> {
      Task task = TaskParser.generateTimedTaskParser(DeadlineTask.ARGUMENTS_SEPARATOR,
          DeadlineTask::new).apply(argumentsString);
      if (task != null) {
        addTaskToList(task);
      }
    });
    loadFromDiskToList();
  }

  private void startRepl() {
    while (true) {
      commandParser.parseAndExecuteCommand(scanner.nextLine().trim());
      saveListToDisk();
    }
  }

  private void loadFromDiskToList() {
    Scanner saveFileScanner = Store.retrieveDataAsScanner();
    while (saveFileScanner.hasNextLine()) {
      String serialized = saveFileScanner.nextLine();
      list.add(taskParser.fromSerial(serialized));
    }
  }

  private void saveListToDisk() {
    Store.saveDataIntoFile(list.stream().map(Task::serialise).collect(Collectors.joining("\n")));
  }

  private void findInListThenMarkAsDone(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    Task item = list.get(itemIndex);
    item.markAsDone();
    Ui.println("Nice! I've marked this task as done:");
    Ui.println(item.toString());
  }

  private void addTaskToList(Task task) {
    list.add(task);
    Ui.println("Got it. I've added this task: ");
    Ui.println(task.toString());
  }

  private void findInListThenDelete(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    Task taskToDelete = list.remove(itemIndex);
    Ui.println("Noted. I've removed this task: ");
    Ui.println(taskToDelete.toString());
    printNumberOfItemsInList();
  }

  private void printNumberOfItemsInList() {
    Ui.printf("Now you have %d task(s) in the list.\n", list.size());
  }

  private void printList() {
    int length = list.size();
    if (length == 0) {
      Ui.println("Yay! You don't have any tasks!");
    } else if (length == 1) {
      Ui.println("You have one task in your list:");
      Ui.println("1. " + list.get(0));
    } else {
      Ui.println("Here are the tasks in your list:");
      for (int itemIndex = 0; itemIndex < length; itemIndex += 1) {
        int oneBasedIndex = itemIndex + 1;
        Ui.printf("%d. %s\n", oneBasedIndex, list.get(itemIndex));
      }
    }
  }
}
