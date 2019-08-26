package task;

import java.time.LocalDateTime;

/**
 * The type Event task.
 */
public class EventTask extends TimedTask {

  private static final String SEPARATOR = "at";
  /**
   * The separator for parsing of arguments.
   */
  public static final String ARGUMENTS_SEPARATOR = "/" + SEPARATOR;
  /**
   * The symbol representing an event.
   */
  public static final char EVENT_TASK_SYMBOL = 'E';

  /**
   * Instantiates a new Event task.
   *
   * @param description the description
   * @param at          the time of the event
   */
  public EventTask(String description, LocalDateTime at) {
    super(description, at);
  }

  @Override
  public String separator() {
    return EventTask.SEPARATOR;
  }

  @Override
  public char symbol() {
    return EventTask.EVENT_TASK_SYMBOL;
  }
}
