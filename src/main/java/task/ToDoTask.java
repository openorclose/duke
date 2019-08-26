package task;

/**
 * The type ToDo task.
 */
public class ToDoTask extends Task {

  /**
   * The symbol representing a todo.
   */
  public static final char TO_DO_SYMBOL = 'T';

  /**
   * Instantiates a new To do task.
   *
   * @param description the description
   */
  public ToDoTask(String description) {
    super(description);
  }

  @Override
  public char symbol() {
    return ToDoTask.TO_DO_SYMBOL;
  }
}
