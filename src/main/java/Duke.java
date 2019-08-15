package main.java;

import java.util.ArrayList;
import java.util.Scanner;

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
      } else {
        addToList(userInput);
      }
    }
  }

  private void findInListThenMarkAsDone(int oneBasedIndex) {
    int itemIndex = oneBasedIndex - 1;
    list.get(itemIndex).markAsDoneAndPrint();
  }

  private void addToList(String userInput) {
    list.add(new Task(userInput));
    System.out.println("added: " + userInput);
  }

  private void printList() {
    for (int itemIndex = 0; itemIndex < list.size(); itemIndex += 1) {
      int oneBasedIndex = itemIndex + 1;
      System.out.printf("%d. %s\n", oneBasedIndex, list.get(itemIndex));
    }
  }
}
