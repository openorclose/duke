package main.java;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class Parser {

  private HashMap<String, Consumer<String>> commands = new HashMap<>();

  void addCommand(String name, Consumer<String> evaluator) {
    commands.put(name, evaluator);
  }

  void parseAndExecuteCommand(String userInput) {
    String trimmedInput = userInput.trim();
    String command = trimmedInput.split(" ")[0];
    if (commands.containsKey(command)) {
      commands.get(command).accept(trimmedInput.substring(command.length()).trim());
    } else {
      System.out
          .printf("Opps! I did not understand what you meant by '%s'\n", trimmedInput);
    }
  }

  static Consumer<String> generateConsumerToParseTwoArguments(String splitAt,
      BiConsumer<String, String> consumer) {
    return argumentString -> {
      if (!argumentString.contains(splitAt)) {
        System.out.printf("Opps! I expected two arguments separated by '%s'.\n", splitAt);
        return;
      }
      String[] arguments = (argumentString + " ").split(splitAt);
      int length = arguments.length;
      if (length > 2) {
        String first = arguments[0];
        String last = arguments[length - 1];
        String allButFirst = argumentString.substring(first.length() + splitAt.length());
        String allButLast = argumentString
            .substring(0, argumentString.length() - last.length() - splitAt.length());
        System.out.printf("Opps! I see %d '%s's, and cannot be sure what you wanted. \n"
                + "For example, you could mean:\n\n"
                + "\t%s\n\t%s\n\t%s\n\n"
                + "\t---OR---\n\n"
                + "\t%s\n\t%s\n\t%s\n\n"
                + "Do not use '%s' in your arguments to avoid this issue.\n",
            length - 1, splitAt, first.trim(), splitAt, allButFirst.trim(), allButLast.trim(),
            splitAt,
            last.trim(), splitAt);
      } else {
        String left = arguments[0].trim();
        String right = arguments[1].trim();
        if ("".equals(left + right)) {
          System.out.printf(
              "Opps! I could not find anything to the left and right of '%s'.\n",
              splitAt);
        } else if (left.equals("")) {
          System.out.printf(
              "Opps! I could not find anything to the left of '%s'.\n",
              splitAt);
        } else if (right.equals("")) {
          System.out.printf(
              "Opps! I could not find anything to the right of '%s'.\n",
              splitAt);
        } else {
          consumer.accept(left, right);
        }
      }
    };
  }
}
