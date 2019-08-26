package command.task;

import java.util.function.Consumer;
import task.DeadlineTask;
import task.Task;
import type.ErrorOutputter;

public class AddDeadlineTaskCommand extends AddTimedTaskCommand {

  public AddDeadlineTaskCommand(ErrorOutputter errorOutputter,
      Consumer<Task> taskAdder) {
    super(errorOutputter, taskAdder, DeadlineTask::new);
  }

  @Override
  String separator() {
    return DeadlineTask.ARGUMENTS_SEPARATOR;
  }

  @Override
  public String name() {
    return "deadline";
  }
}
