package command.task;

import java.util.function.Consumer;
import task.EventTask;
import task.Task;
import type.ErrorOutputter;

public class AddEventTaskCommand extends AddTimedTaskCommand {

  public AddEventTaskCommand(ErrorOutputter errorOutputter,
      Consumer<Task> taskAdder) {
    super(errorOutputter, taskAdder, EventTask::new);
  }

  @Override
  String separator() {
    return EventTask.ARGUMENTS_SEPARATOR;
  }

  @Override
  public String name() {
    return "event";
  }
}
