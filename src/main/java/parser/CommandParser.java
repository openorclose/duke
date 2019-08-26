package parser;

import command.Command;
import java.util.function.Consumer;
import ui.Ui;

/**
 * Parser for commands.
 */
public class CommandParser {

  private Parser<Void> parser;

  /**
   * Instantiates a new Command parser.
   *
   * @param ui the ui
   */
  public CommandParser(Ui ui) {
    this.parser = new Parser<Void>(ui);
  }

  /**
   * Add.
   *
   * @param command the command
   */
  public void add(Command command) {
    addCommand(command.name(), command::execute);
  }

  /**
   * Add command.
   *
   * @param command   the command
   * @param evaluator the evaluator
   */
  public void addCommand(String command, Consumer<String> evaluator) {
    parser.addSubParser(command, string -> {
      evaluator.accept(string);
      return null;
    });
  }

  /**
   * Parse and execute command.
   *
   * @param userInput the user input
   */
  public void parseAndExecuteCommand(String userInput) {
    parser.parse(userInput);
  }
}
