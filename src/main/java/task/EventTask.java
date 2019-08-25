package main.java.task;

import java.time.LocalDateTime;
import main.parser.DateParser;

public class EventTask extends Task {

  private LocalDateTime at;

  public EventTask(String description, LocalDateTime at) {
    super(description);
    this.at = at;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (:at %s)", super.toString(), DateParser.dateToString(at));
  }
}
