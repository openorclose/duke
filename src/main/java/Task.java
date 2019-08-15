package main.java;

public class Task {
  private static char DONE_SYMBOL = '\u2713'; // tick
  private static char NOT_DONE_SYMBOL = '\u2718'; //cross
  private boolean isDone = false;
  private String description;

  Task(String description) {
    this.description = description;
  }

  void markAsDoneAndPrint() {
    this.isDone = true;
    System.out.println("Nice! I've marked this task as done:");
    System.out.println(this);
  }

  public String toString() {
    return String.format("[%c] %s", getStatusIcon(), description);
  }

  private char getStatusIcon() {
    return isDone ? DONE_SYMBOL : NOT_DONE_SYMBOL;
  }
}
