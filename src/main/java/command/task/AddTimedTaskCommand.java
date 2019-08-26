package command.task;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import parser.TaskParser;
import task.Task;
import type.ErrorOutputter;

/**
 * Command to add a TimedTask.
 */
abstract class AddTimedTaskCommand extends AddTaskCommand {

  private final BiFunction<String, LocalDateTime, Task> constructor;

  /**
   * Instantiates a new AddTimedTaskCommand.
   *
   * @param errorOutputter the error outputter
   * @param taskAdder      the task adder
   * @param constructor    the constructor
   */
  AddTimedTaskCommand(ErrorOutputter errorOutputter,
      Consumer<Task> taskAdder, BiFunction<String, LocalDateTime, Task> constructor) {
    super(errorOutputter, taskAdder);
    this.constructor = constructor;
  }

  /**
   * Separator string.
   *
   * @return separator
   */
  abstract String separator();

  @Override
  Task generateTask(String arguments) {
    return TaskParser.generateTimedTaskParser(separator(),
        constructor, errorOutputter
    ).apply(arguments);
  }
}
