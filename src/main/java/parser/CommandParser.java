package parser;

import command.Command;
import java.util.function.Consumer;
import ui.Ui;

public class CommandParser {

  private Parser<Void> parser;

  public CommandParser(Ui ui) {
    this.parser = new Parser<Void>(ui);
  }

  public void add(Command command) {
    addCommand(command.name(), command::execute);
  }

  public void addCommand(String command, Consumer<String> evaluator) {
    parser.addSubParser(command, string -> {
      evaluator.accept(string);
      return null;
    });
  }

  public void parseAndExecuteCommand(String userInput) {
    parser.parse(userInput);
  }
}
