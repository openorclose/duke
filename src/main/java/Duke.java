package main.java;

import java.util.ArrayList;
import java.util.Scanner;
import main.java.task.DeadlineTask;
import main.java.task.EventTask;
import main.java.task.Task;
import main.java.task.ToDoTask;

public class Duke {

  private Scanner scanner = new Scanner(System.in);

  private ArrayList<Task> list = new ArrayList<>();

  private Parser parser = new Parser();

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
    parser.addCommand("bye", unused -> {
      System.out.println("Bye. Hope to see you again soon!");
      System.exit(0);
    });
    parser.addCommand("list", unused -> printList());
    parser.addCommand("done", oneBasedIndexString -> {
      int oneBasedIndex = 0;
      try {
        oneBasedIndex = Integer.parseInt(oneBasedIndexString);
        findInListThenMarkAsDone(oneBasedIndex);
      } catch (NumberFormatException e) {
        System.out
            .println("Opps! I expected an integer corresponding to the task you want to remove.");
      } catch (IndexOutOfBoundsException e) {
        System.out.printf("Opps! I could not find item %d in your list.\n", oneBasedIndex);
      }
    });
    parser.addCommand("todo", description -> {
      if (description.equals("")) {
        System.out.println("Opps! I expected a description of your todo task.");
      } else {
        addTaskToList(new ToDoTask(description));
      }
    });
    parser.addCommand("event",
        Parser.generateConsumerToParseTwoArguments("/at",
            (description, at) -> addTaskToList(new EventTask(description, at))));
    parser.addCommand("deadline",
        Parser.generateConsumerToParseTwoArguments("/by",
            (description, by) -> addTaskToList(new DeadlineTask(description, by))));
  }

  private void startRepl() {
    while (true) {
      parser.parseAndExecuteCommand(scanner.nextLine().trim());
    }
  }

  private void findInListThenMarkAsDone(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    list.get(itemIndex).markAsDoneAndPrint();
  }

  private void addTaskToList(Task task) {
    list.add(task);
    System.out.println("Got it. I've added this task: ");
    System.out.println(task);
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
