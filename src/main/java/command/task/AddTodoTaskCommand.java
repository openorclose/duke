package command.task;

import java.util.function.Consumer;
import task.Task;
import task.ToDoTask;
import type.ErrorOutputter;

/**
 * Command to add a Todo.
 */
public class AddTodoTaskCommand extends AddTaskCommand {

  /**
   * Instantiates a new AddTodoTaskCommand.
   *
   * @param errorOutputter the error outputter
   * @param taskAdder      the task adder
   */
  public AddTodoTaskCommand(ErrorOutputter errorOutputter,
      Consumer<Task> taskAdder) {
    super(errorOutputter, taskAdder);
  }

  @Override
  Task generateTask(String arguments) {
    return new ToDoTask(arguments);
  }

  @Override
  public String name() {
    return "todo";
  }
}
