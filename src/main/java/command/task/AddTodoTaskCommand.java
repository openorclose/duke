package command.task;

import java.util.function.Consumer;
import task.Task;
import task.ToDoTask;
import type.ErrorOutputter;

public class AddTodoTaskCommand extends AddTaskCommand {

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
