package main.java.task;

import java.time.LocalDateTime;
import main.parser.DateParser;

public class DeadlineTask extends Task {

  private LocalDateTime by;

  public DeadlineTask(String description, LocalDateTime by) {
    super(description);
    this.by = by;
  }

  @Override
  public String toString() {
    return String.format("[D]%s (:by %s)", super.toString(), DateParser.dateToString(by));
  }
}
