package parser;

import java.util.function.Consumer;

public class CommandParser {
  private Parser<Void> parser = new Parser<>();

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
