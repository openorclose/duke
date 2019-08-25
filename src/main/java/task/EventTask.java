package main.java.task;

import java.time.LocalDateTime;
import main.java.parser.DateParser;

public class EventTask extends Task {

  private LocalDateTime at;
  public static final char EVENT_TASK_SYMBOL = 'E';
  public static final String ARGUMENTS_SEPARATOR = "/at";

  public EventTask(String description, LocalDateTime at) {
    super(description);
    this.at = at;
  }

  @Override
  public String toString() {
    return String.format("[%c]%s (:at %s)", EventTask.EVENT_TASK_SYMBOL, super.toString(), DateParser.dateToString(at));
  }

  public String serialise() {
    return String.format(
        "%c %s %s %s",
        EventTask.EVENT_TASK_SYMBOL,
        super.serialise(),
        EventTask.ARGUMENTS_SEPARATOR,
        DateParser.dateToString(at)
    );
  }
}
