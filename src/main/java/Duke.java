package main.java;

import java.util.Scanner;

public class Duke {

  private Scanner scanner = new Scanner(System.in);

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
      }
      System.out.println(userInput);
    }
  }
}
