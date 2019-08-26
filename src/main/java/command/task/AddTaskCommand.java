package command.task;

import command.Command;
import java.util.function.Consumer;
import task.Task;
import type.ErrorOutputter;

/**
 * Command to add a Task.
 */
public abstract class AddTaskCommand implements Command {

  /**
   * The Error outputter.
   */
  protected final ErrorOutputter errorOutputter;
  private final Consumer<Task> taskAdder;

  /**
   * Instantiates a new AddTaskCommand.
   *
   * @param errorOutputter the error outputter
   * @param taskAdder      the task adder
   */
  AddTaskCommand(ErrorOutputter errorOutputter, Consumer<Task> taskAdder) {
    this.errorOutputter = errorOutputter;
    this.taskAdder = taskAdder;
  }

  /**
   * Generate the task.
   *
   * @param arguments the arguments
   * @return task
   */
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
