package parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateParser {

  private static final DateTimeFormatter FORMAT =
      DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
          .withResolverStyle(
              ResolverStyle.STRICT
          );

  public static LocalDateTime stringToDate(String dateString) throws DateTimeParseException {
    return LocalDateTime.parse(dateString, DateParser.FORMAT);
  }

  public static String dateToString(LocalDateTime date) {
    return date.format(DateParser.FORMAT);
  }
}
