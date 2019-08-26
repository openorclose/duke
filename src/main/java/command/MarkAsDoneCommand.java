package command;

import java.util.function.Consumer;
import parser.ParserUtils;
import type.ErrorOutputter;

/**
 * Command to mark a task as done.
 */
public class MarkAsDoneCommand implements Command {

  private ErrorOutputter errorOutputter;
  private final Consumer<Integer> doneMarker;

  /**
   * Instantiates a new MarkAsDoneCommand.
   *
   * @param errorOutputter the error outputter
   * @param doneMarker     the done marker
   */
  public MarkAsDoneCommand(ErrorOutputter errorOutputter, Consumer<Integer> doneMarker) {
    this.errorOutputter = errorOutputter;
    this.doneMarker = doneMarker;
  }

  @Override
  public String name() {
    return "done";
  }

  @Override
  public void execute(String arguments) {
    ParserUtils.generateConsumerExpectingInteger(oneBasedIndex -> {
      try {
        doneMarker.accept(oneBasedIndex);
      } catch (IndexOutOfBoundsException e) {
        errorOutputter.accept(String.format("Opps! I could not find item %d in your list.\n",
            oneBasedIndex));
      }
    }, errorOutputter).accept(arguments);
  }
}
