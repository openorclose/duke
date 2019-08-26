package command.task;

import java.util.function.Consumer;
import task.DeadlineTask;
import task.Task;
import type.ErrorOutputter;

/**
 * Command to add a Deadline.
 */
public class AddDeadlineTaskCommand extends AddTimedTaskCommand {

  /**
   * Instantiates a AddDeadlineTaskCommand.
   *
   * @param errorOutputter the error outputter
   * @param taskAdder      the task adder
   */
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
