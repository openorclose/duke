package command;

import java.util.function.Consumer;
import parser.ParserUtils;
import type.ErrorOutputter;

/**
 * Command to delete a task.
 */
public class DeleteCommand implements Command {

  private ErrorOutputter errorOutputter;
  private final Consumer<Integer> deleter;

  /**
   * Instantiates a new DeleteCommand.
   *
   * @param errorOutputter the error outputter
   * @param deleter        the deleter
   */
  public DeleteCommand(ErrorOutputter errorOutputter, Consumer<Integer> deleter) {
    this.errorOutputter = errorOutputter;
    this.deleter = deleter;
  }

  @Override
  public String name() {
    return "delete";
  }

  @Override
  public void execute(String arguments) {
    ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
      try {
        deleter.accept(oneBasedIndex);
      } catch (IndexOutOfBoundsException e) {
        errorOutputter.accept(
            String.format("Opps! I could not delete item %d in your list as it does not exist.\n",
                oneBasedIndex));
      }
    }, errorOutputter).accept(arguments);
  }
}
