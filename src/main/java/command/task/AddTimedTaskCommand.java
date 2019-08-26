package command.task;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import parser.TaskParser;
import task.Task;
import type.ErrorOutputter;

abstract class AddTimedTaskCommand extends AddTaskCommand {

  private final BiFunction<String, LocalDateTime, Task> constructor;

  AddTimedTaskCommand(ErrorOutputter errorOutputter,
      Consumer<Task> taskAdder, BiFunction<String, LocalDateTime, Task> constructor) {
    super(errorOutputter, taskAdder);
    this.constructor = constructor;
  }

  abstract String separator();

  @Override
  Task generateTask(String arguments) {
    return TaskParser.generateTimedTaskParser(separator(),
        constructor, errorOutputter
    ).apply(arguments);
  }
}
