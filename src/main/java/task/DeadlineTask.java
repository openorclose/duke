package task;

import java.time.LocalDateTime;

/**
 * The type Deadline task.
 */
public class DeadlineTask extends TimedTask {

  /**
   * The symbol representing a deadline.
   */
  public static final char DEADLINE_TASK_SYMBOL = 'D';
  private static final String SEPARATOR = "by";
  /**
   * The separator for parsing of arguments.
   */
  public static final String ARGUMENTS_SEPARATOR = "/" + SEPARATOR;

  /**
   * Instantiates a new Deadline task.
   *
   * @param description the description
   * @param by          the deadline
   */
  public DeadlineTask(String description, LocalDateTime by) {
    super(description, by);
  }

  @Override
  public String separator() {
    return DeadlineTask.SEPARATOR;
  }

  @Override
  public char symbol() {
    return DeadlineTask.DEADLINE_TASK_SYMBOL;
  }
}
