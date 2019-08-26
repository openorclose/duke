package command;

public class PrintListCommand implements Command {

  private final Runnable listPrinter;

  public PrintListCommand(Runnable listPrinter) {
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
