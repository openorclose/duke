package command.task;

import command.Command;
import java.util.function.Consumer;
import task.Task;
import type.ErrorOutputter;

public abstract class AddTaskCommand implements Command {

  protected final ErrorOutputter errorOutputter;
  private final Consumer<Task> taskAdder;

  AddTaskCommand(ErrorOutputter errorOutputter, Consumer<Task> taskAdder) {
    this.errorOutputter = errorOutputter;
    this.taskAdder = taskAdder;
  }

  abstract Task generateTask(String arguments);

  @Override
  public void execute(String arguments) {
    if (arguments.equals("")) {
      errorOutputter.accept("Opps! I expected a description of your task.");
    } else {
      Task task = generateTask(arguments);
      if (task != null) {
        taskAdder.accept(task);
      }
    }
  }
}
