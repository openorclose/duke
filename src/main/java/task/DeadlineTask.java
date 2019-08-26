package task;

import java.time.LocalDateTime;

public class DeadlineTask extends TimedTask {

  public static final char DEADLINE_TASK_SYMBOL = 'D';
  private static final String SEPARATOR = "by";
  public static final String ARGUMENTS_SEPARATOR = "/" + SEPARATOR;

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
