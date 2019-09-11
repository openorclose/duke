package task;

/**
 * The type Task.
 */
public abstract class Task {

  private static char DONE_SYMBOL = '/'; // tick
  private static char NOT_DONE_SYMBOL = 'X'; //cross
  private boolean isDone = false;
  private String description;

  /**
   * Instantiates a new Task.
   *
   * @param description the description
   */
  public Task(String description) {
    this.description = description;
  }

  /**
   * Mark as done.
   */
  public void markAsDone() {
    this.isDone = true;
  }

  /**
   * The char representing the task.
   *
   * @return the char
   */
  public abstract char symbol();

  @Override
  public String toString() {
    return String.format("[%c][%c] %s", symbol(), getStatusIcon(), description);
  }

  /**
   * Serialize task for easier storage.
   *
   * @return the serialized string
   */
  public String serialize() {
    return String.format("%c %b %s", symbol(), isDone, description);
  }

  private char getStatusIcon() {
    return isDone ? DONE_SYMBOL : NOT_DONE_SYMBOL;
  }

  public boolean matches(String query) {
    return description.toLowerCase().contains(query.toLowerCase());
  }

  public boolean isDone() {
    return this.isDone;
  }
}
