package parser;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import type.ErrorOutputter;
import ui.Ui;

public class ParserUtils {

  public static Consumer<String> generateConsumerExpectingInteger(Consumer<Integer> consumer, ErrorOutputter errorOutputter) {
    return integerString -> {
      try {
        consumer.accept(Integer.parseInt(integerString));
      } catch (NumberFormatException e) {
        errorOutputter.accept("Opps! I expected an integer as the argument");
      }
    };
  }

  public static <R> Function<String, R> generateFunctionSplittingAtFirstSpace(
      BiFunction<String, String, R> biFunction) {
    return spaceSeparatedString -> {
      String[] arguments = spaceSeparatedString.split(" ", 2);
      return biFunction.apply(arguments[0], arguments[1]);
    };
  }

  public static Consumer<String> generateConsumerToParseTwoArguments(String splitAt,
      BiConsumer<String, String> consumer, ErrorOutputter errorOutputter) {
    return argumentString -> generateFunctionToParseTwoArguments(splitAt, (left, right) -> {
      consumer.accept(left, right);
      return null;
    }, errorOutputter).apply(argumentString);
  }

  public static <R> Function<String, R> generateFunctionToParseTwoArguments(String splitAt,
      BiFunction<String, String, R> biFunction, ErrorOutputter errorOutputter) {
    return argumentString -> {
      if (!argumentString.contains(splitAt)) {
        errorOutputter.accept(String.format("Opps! I expected two arguments separated by '%s'.\n", splitAt));
        return null;
      }
      argumentString += " ";
      String[] arguments = argumentString.split(splitAt);
      int length = arguments.length;
      if (length > 2) {
        String first = arguments[0];
        String last = arguments[length - 1];
        String allButFirst = argumentString.substring(first.length() + splitAt.length());
        String allButLast = argumentString
            .substring(0, argumentString.length() - last.length() - splitAt.length());
        errorOutputter.accept(String.format("Opps! I see %d '%s's, and cannot be sure what you wanted. \n"
                + "For example, you could mean:\n\n"
                + "\t%s\n\t%s\n\t%s\n\n"
                + "\t---OR---\n\n"
                + "\t%s\n\t%s\n\t%s\n\n"
                + "Do not use '%s' in your arguments to avoid this issue.\n",
            length - 1, splitAt, first.trim(), splitAt, allButFirst.trim(), allButLast.trim(),
            splitAt,
            last.trim(), splitAt));
      } else {
        String left = arguments[0].trim();
        String right = arguments[1].trim();
        if ("".equals(left + right)) {
          errorOutputter.accept(String.format(
              "Opps! I could not find anything to the left and right of '%s'.\n",
              splitAt));
        } else if (left.equals("")) {
          errorOutputter.accept(String.format(
              "Opps! I could not find anything to the left of '%s'.\n",
              splitAt));
        } else if (right.equals("")) {
          errorOutputter.accept(String.format(
              "Opps! I could not find anything to the right of '%s'.\n",
              splitAt));
        } else {
          return biFunction.apply(left, right);
        }
      }
      return null;
    };
  }
}
