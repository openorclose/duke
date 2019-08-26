package task;

public class ToDoTask extends Task {

  public static final char TO_DO_SYMBOL = 'T';

  public ToDoTask(String description) {
    super(description);
  }

  @Override
  public char symbol() {
    return ToDoTask.TO_DO_SYMBOL;
  }
}
