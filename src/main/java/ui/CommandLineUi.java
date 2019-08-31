package ui;

/**
 * Displayer of results to the user.
 */
public class CommandLineUi implements Ui {

  /**
   * Print without new line.
   *
   * @param string the string
   */
  @Override
  public void print(String string) {
    System.out.print(string);
  }

  /**
   * Print an error.
   *
   * @param message the message
   */
  @Override
  public void error(String message) {
    System.out.println(message);
  }

  /**
   * Exits the application.
   *
   * @param message the message
   */
  @Override
  public void exit(String message) {
    System.out.println(message);
    System.exit(0);
  }
}
