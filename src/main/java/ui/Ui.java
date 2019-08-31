package ui;

/**
 * The Ui interface to interact with the user.
 */
public interface Ui {

  /**
   * Print.
   *
   * @param string the string
   */
  void print(String string);

  /**
   * Println.
   *
   * @param string the string
   */
  default void println(String string) {
    print(string + '\n');
  }

  /**
   * Printf.
   *
   * @param format the format
   * @param args   the args
   */
  default void printf(String format, Object... args) {
    print(String.format(format, args));
  }

  /**
   * Error.
   *
   * @param message the message
   */
  void error(String message);

  /**
   * Exit.
   *
   * @param message the message
   */
  void exit(String message);
}
