package command;

/**
 * Command to print the Task list.
 */
public class PrintListCommand implements Command {

  private final Runnable listPrinter;

  /**
   * Instantiates a new PrintListCommand.
   *
   * @param listPrinter the list printer
   */
  public PrintListCommand(Runnable listPrinter) {
    assert (listPrinter != null);
    this.listPrinter = listPrinter;
  }

  @Override
  public String name() {
    return "list";
  }

  @Override
  public void execute(String arguments) {
    listPrinter.run();
  }
}
