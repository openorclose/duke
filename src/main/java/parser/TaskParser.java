package main.java.parser;

import java.util.function.Function;
import main.java.task.DeadlineTask;
import main.java.task.EventTask;
import main.java.task.Task;
import main.java.task.ToDoTask;

public class TaskParser {

  private Parser<Task> parser = new Parser<>();

  public TaskParser() {
    addTaskSubParser(ToDoTask.TO_DO_SYMBOL, ToDoTask::new);
    addTaskSubParser(EventTask.EVENT_TASK_SYMBOL,
        ParserUtils.generateFunctionToParseTwoArguments(EventTask.ARGUMENTS_SEPARATOR,
            EventTask::new
        ));
    addTaskSubParser(DeadlineTask.DEADLINE_TASK_SYMBOL,
        ParserUtils.generateFunctionToParseTwoArguments(DeadlineTask.ARGUMENTS_SEPARATOR,
            DeadlineTask::new));
  }

  private void addTaskSubParser(char type, Function<String, Task> creator) {
    Function<String, Task> isDoneIntermediateParser = ParserUtils
        .generateFunctionSplittingAtFirstSpace((isDoneString, rest) -> {
          boolean isDone = Boolean.parseBoolean(isDoneString);
          Task task = creator.apply(rest);
          if (isDone) {
            task.markAsDone();
          }
          return task;
        });
    parser.addSubParser(String.valueOf(type), isDoneIntermediateParser);
  }

  public Task fromSerial(String serial) {
    return parser.parse(serial);
  }
}
