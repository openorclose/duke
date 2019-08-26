package parser;

import java.util.HashMap;
import java.util.function.Function;
import ui.Ui;

/**
 * Parser to parse arguments.
 *
 * @param <T> return type of a parse
 */
public class Parser<T> {

  private HashMap<String, Function<String, T>> commands = new HashMap<>();
  private Ui ui;

  /**
   * Instantiates a new Parser.
   *
   * @param ui the ui
   */
  public Parser(Ui ui) {
    this.ui = ui;
  }

  /**
   * Adds the command name and the evaluator to the parser.
   *
   * @param name      the name
   * @param evaluator the evaluator
   */
  public void addSubParser(String name, Function<String, T> evaluator) {
    commands.put(name, evaluator);
  }

  /**
   * Parse user input into the return type T.
   *
   * @param userInput the user input
   * @return T
   */
  public T parse(String userInput) {
    String trimmedInput = userInput.trim();
    String command = trimmedInput.split(" ")[0];
    if (commands.containsKey(command)) {
      return commands.get(command).apply(trimmedInput.substring(command.length()).trim());
    } else {
      ui.error(String.format("Opps! I did not understand what you meant by '%s'\n", trimmedInput));
      return null;
    }
  }


}
