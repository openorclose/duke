package command;


import java.util.function.Consumer;

public class ByeCommand implements Command {
  private Consumer<String> exitter;

  public ByeCommand(Consumer<String> exitter) {
    this.exitter = exitter;
  }

  @Override
  public String name() {
    return "bye";
  }

  @Override
  public void execute(String arguments) {
    exitter.accept("Bye. Hope to see you again soon!");
  }
}
