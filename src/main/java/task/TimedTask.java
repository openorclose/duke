package task;

import java.time.LocalDateTime;
import parser.DateParser;

public abstract class TimedTask extends Task {

  private LocalDateTime time;

  public TimedTask(String description, LocalDateTime time) {
    super(description);
    this.time = time;
  }

  public abstract String separator();

  @Override
  public String serialize() {
    return String.format(
        "%s /%s %s",
        super.serialize(),
        separator(),
        DateParser.dateToString(time)
    );
  }

  @Override
  public String toString() {
    return String
        .format("%s (:%s %s)", super.toString(), separator(), DateParser.dateToString(time));
  }
}
