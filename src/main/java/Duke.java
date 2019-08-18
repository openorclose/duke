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

  public static void main(String[] args) {
    String logo = " ____        _        \n"
        + "|  _ \\ _   _| | _____ \n"
        + "| | | | | | | |/ / _ \\\n"
        + "| |_| | |_| |   <  __/\n"
        + "|____/ \\__,_|_|\\_\\___|\n";
    System.out.println("Hello from\n" + logo);
    System.out.println("What can I do for you?");

    Duke duke = new Duke();
    duke.processUserInputsUntilBye();
    System.out.println("Bye. Hope to see you again soon!");
  }

  public Duke() {
  }

  private void processUserInputsUntilBye() {
    while (true) {
      String userInput = scanner.nextLine();
      if (userInput.equals("bye")) {
        break;
      } else if (userInput.equals("list")) {
        printList();
      } else if (userInput.startsWith("done ")) {
        int oneBasedIndex = Integer.parseInt(userInput.substring("done ".length()));
        findInListThenMarkAsDone(oneBasedIndex);
      } else if (userInput.startsWith("todo ")) {
        addTaskToList(new ToDoTask(userInput.substring("todo ".length())));
      } else if (userInput.startsWith("deadline ")) {
        String[] arguments = userInput.substring("deadline ".length()).split(" /by ");
        String deadlineName = arguments[0];
        String by = arguments[1];
        addTaskToList(new DeadlineTask(deadlineName, by));
      } else if (userInput.startsWith("event ")) {
        String[] arguments = userInput.substring("event ".length()).split(" /at ");
        String eventName = arguments[0];
        String at = arguments[1];
        addTaskToList(new EventTask(eventName, at));
      }
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
    System.out.printf("Now you have %d tasks in the list.\n", list.size());
  }

  private void printList() {
    for (int itemIndex = 0; itemIndex < list.size(); itemIndex += 1) {
      int oneBasedIndex = itemIndex + 1;
      System.out.printf("%d. %s\n", oneBasedIndex, list.get(itemIndex));
    }
  }
}
