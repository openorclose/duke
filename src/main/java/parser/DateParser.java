package parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Parser to parse Dates.
 */
public class DateParser {

  private static final DateTimeFormatter FORMAT =
      DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
          .withResolverStyle(
              ResolverStyle.STRICT
          );

  /**
   * String to LocalDateTime.
   *
   * @param dateString the date string
   * @return the LocalDateTime
   * @throws DateTimeParseException illegal date exception
   */
  public static LocalDateTime stringToDate(String dateString) throws DateTimeParseException {
    return LocalDateTime.parse(dateString, DateParser.FORMAT);
  }

  /**
   * Date to string.
   *
   * @param date the date
   * @return the stringified date
   */
  public static String dateToString(LocalDateTime date) {
    return date.format(DateParser.FORMAT);
  }
}
