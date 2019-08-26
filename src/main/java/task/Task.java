package task;

public abstract class Task {

  private static char DONE_SYMBOL = '/'; // tick
  private static char NOT_DONE_SYMBOL = 'X'; //cross
  private boolean isDone = false;
  private String description;

  public Task(String description) {
    this.description = description;
  }

  public void markAsDone() {
    this.isDone = true;
  }

  public abstract char symbol();

  @Override
  public String toString() {
    return String.format("[%c][%c] %s", symbol(), getStatusIcon(), description);
  }

  public String serialise() {
    return String.format("%c %b %s", symbol(), isDone, description);
  }

  private char getStatusIcon() {
    return isDone ? DONE_SYMBOL : NOT_DONE_SYMBOL;
  }
}
