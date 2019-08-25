package task;

public class ToDoTask extends Task {

  public static final char TO_DO_SYMBOL = 'T';

  public ToDoTask(String description) {
    super(description);
  }

  @Override
  public String toString() {
    return String.format("[%c]%s", ToDoTask.TO_DO_SYMBOL, super.toString());
  }

  public String serialise() {
    return String.format("%c %s", ToDoTask.TO_DO_SYMBOL, super.serialise());
  }
}
