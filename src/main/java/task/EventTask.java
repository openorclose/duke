package task;

import java.time.LocalDateTime;

public class EventTask extends TimedTask {

  private static final String SEPARATOR = "at";
  public static final String ARGUMENTS_SEPARATOR = "/" + SEPARATOR;
  public static final char EVENT_TASK_SYMBOL = 'E';

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
