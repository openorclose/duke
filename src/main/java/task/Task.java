package main.java.task;

public class Task {

  private static char DONE_SYMBOL = '\u2713'; // tick
  private static char NOT_DONE_SYMBOL = '\u2718'; //cross
  private boolean isDone = false;
  private String description;

  Task(String description) {
    this.description = description;
  }

  public void markAsDone() {
    this.isDone = true;
  }

  @Override
  public String toString() {
    return String.format("[%c] %s", getStatusIcon(), description);
  }

  public String serialise() {
    return String.format("%b %s", isDone, description);
  }

  private char getStatusIcon() {
    return isDone ? DONE_SYMBOL : NOT_DONE_SYMBOL;
  }
}