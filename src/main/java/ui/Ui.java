package ui;

/**
 * Displayer of results to the user.
 */
public class Ui {

  /**
   * Print without new line.
   *
   * @param string the string
   */
  public void print(String string) {
    System.out.print(string);
  }

  /**
   * Print with new line.
   *
   * @param string the string
   */
  public void println(String string) {
    System.out.println(string);
  }

  /**
   * Print using a format.
   *
   * @param format the format
   * @param args   the args
   */
  public void printf(String format, Object... args) {
    System.out.printf(format, args);
  }

  /**
   * Print an error.
   *
   * @param message the message
   */
  public void error(String message) {
    System.out.println(message);
  }

  /**
   * Exits the application.
   *
   * @param message the message
   */
  public void exit(String message) {
    System.out.println(message);
    System.exit(0);
  }
}
