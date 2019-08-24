package main.java.task;

public class EventTask extends Task {

  public static final char EVENT_TASK_SYMBOL = 'E';
  public static final String ARGUMENTS_SEPARATOR = "/at";

  private String at;

  public EventTask(String description, String at) {
    super(description);
    this.at = at;
  }

  @Override
  public String toString() {
    return String.format("[%c]%s (:at %s)", EventTask.EVENT_TASK_SYMBOL, super.toString(), at);
  }

  public String serialise() {
    return String.format(
        "%c %s %s %s",
        EventTask.EVENT_TASK_SYMBOL,
        super.serialise(),
        EventTask.ARGUMENTS_SEPARATOR,
        at
    );
  }
}
