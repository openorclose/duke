package main.java.task;

public class DeadlineTask extends Task {

  public static final char DEADLINE_TASK_SYMBOL = 'D';
  public static final String ARGUMENTS_SEPARATOR = "/by";
  private String by;

  public DeadlineTask(String description, String by) {
    super(description);
    this.by = by;
  }

  @Override
  public String toString() {
    return String
        .format("[%c]%s (:by %s)", DeadlineTask.DEADLINE_TASK_SYMBOL, super.toString(), by);
  }

  public String serialise() {
    return String.format(
        "%c %s %s %s",
        DeadlineTask.DEADLINE_TASK_SYMBOL,
        super.serialise(),
        DeadlineTask.ARGUMENTS_SEPARATOR,
        by
    );
  }
}
