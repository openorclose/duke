package main.java.parser;

import java.util.HashMap;
import java.util.function.Function;

public class Parser<T> {

  private HashMap<String, Function<String, T>> commands = new HashMap<>();

  public void addSubParser(String name, Function<String, T> evaluator) {
    commands.put(name, evaluator);
  }

  public T parse(String userInput) {
    String trimmedInput = userInput.trim();
    String command = trimmedInput.split(" ")[0];
    if (commands.containsKey(command)) {
      return commands.get(command).apply(trimmedInput.substring(command.length()).trim());
    } else {
      System.out
          .printf("Opps! I did not understand what you meant by '%s'\n", trimmedInput);
      return null;
    }
  }


}
