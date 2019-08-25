package main.java.task;

import java.time.LocalDateTime;
import main.java.parser.DateParser;

public class DeadlineTask extends Task {

  public static final char DEADLINE_TASK_SYMBOL = 'D';
  public static final String ARGUMENTS_SEPARATOR = "/by";
  private LocalDateTime by;

  public DeadlineTask(String description, LocalDateTime by) {
    super(description);
    this.by = by;
  }

  @Override
  public String toString() {
    return String
        .format("[%c]%s (:by %s)", DeadlineTask.DEADLINE_TASK_SYMBOL, super.toString(), DateParser.dateToString(by));
  }

  public String serialise() {
    return String.format(
        "%c %s %s %s",
        DeadlineTask.DEADLINE_TASK_SYMBOL,
        super.serialise(),
        DeadlineTask.ARGUMENTS_SEPARATOR,
        DateParser.dateToString(by)
    );
  }
}
