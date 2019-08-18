package main.java.task;

public class EventTask extends Task {

  private String at;

  public EventTask(String description, String at) {
    super(description);
    this.at = at;
  }

  @Override
  public String toString() {
    return String.format("[E]%s (:at %s)", super.toString(), at);
  }
}
