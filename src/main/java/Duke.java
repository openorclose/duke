package main.java;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.java.parser.CommandParser;
import main.java.parser.ParserUtils;
import main.java.store.Store;
import main.java.task.DeadlineTask;
import main.java.task.EventTask;
import main.java.task.Task;
import main.java.parser.TaskParser;
import main.java.task.ToDoTask;
import main.java.parser.DateParser;

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
    System.out.println("Hello from\n" + logo);
    System.out.println("What can I do for you?");

    Duke duke = new Duke();
    duke.startRepl();
  }

  public Duke() {
    commandParser.addCommand("bye", unused -> {
      System.out.println("Bye. Hope to see you again soon!");
      System.exit(0);
    });
    commandParser.addCommand("list", unused -> printList());
    commandParser.addCommand("done", ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
      try {
        findInListThenMarkAsDone(oneBasedIndex);
      } catch (IndexOutOfBoundsException e) {
        System.out.printf("Opps! I could not find item %d in your list.\n",
            oneBasedIndex);
      }
    }));
    commandParser
        .addCommand("delete", ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
          try {
            findInListThenDelete(oneBasedIndex);
          } catch (IndexOutOfBoundsException e) {
            System.out
                .printf("Opps! I could not delete item %d in your list as it does not exist.\n",
                    oneBasedIndex);
          }
        }));
    commandParser.addCommand("todo", description -> {
      if (description.equals("")) {
        System.out.println("Opps! I expected a description of your todo task.");
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
    System.out.println("Nice! I've marked this task as done:");
    System.out.println(item);
  }

  private void addTaskToList(Task task) {
    list.add(task);
    System.out.println("Got it. I've added this task: ");
    System.out.println(task);
  }

  private void findInListThenDelete(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    Task taskToDelete = list.remove(itemIndex);
    System.out.println("Noted. I've removed this task: ");
    System.out.println(taskToDelete);
    printNumberOfItemsInList();
  }

  private void printNumberOfItemsInList() {
    System.out.printf("Now you have %d task(s) in the list.\n", list.size());
  }

  private void printList() {
    int length = list.size();
    if (length == 0) {
      System.out.println("Yay! You don't have any tasks!");
    } else if (length == 1) {
      System.out.println("You have one task in your list:");
      System.out.println("1. " + list.get(0));
    } else {
      System.out.println("Here are the tasks in your list:");
      for (int itemIndex = 0; itemIndex < length; itemIndex += 1) {
        int oneBasedIndex = itemIndex + 1;
        System.out.printf("%d. %s\n", oneBasedIndex, list.get(itemIndex));
      }
    }
  }
}
