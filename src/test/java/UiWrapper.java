import java.util.ArrayList;
import ui.Ui;

/**
 * The type Ui wrapper that outputs to an arraylist for easier tesing.
 */
public class UiWrapper implements Ui {

  private ArrayList<String> printedMessages = new ArrayList<>();
  private ArrayList<String> errorMessages = new ArrayList<>();
  private boolean wasExitCalled = false;

  public void print(String string) {
    printedMessages.add(string);
  }

  public void println(String string) {
    printedMessages.add(string + "\n");
  }

  public void printf(String format, Object... args) {
    printedMessages.add(String.format(format, args));
  }

  public void error(String message) {
    errorMessages.add(message);
  }

  public void exit(String message) {
    wasExitCalled = true;
    printedMessages.add(message + "\n");
  }

  /**
   * Gets messages size.
   *
   * @return the messages size
   */
  public int getMessagesSize() {
    return printedMessages.size();
  }

  /**
   * Gets last message.
   *
   * @return the last message
   */
  public String getLastMessage() {
    if (printedMessages.size() == 0) {
      return null;
    }
    return printedMessages.get(printedMessages.size() - 1);
  }

  /**
   * Gets errors size.
   *
   * @return the errors size
   */
  public int getErrorsSize() {
    return errorMessages.size();
  }

  /**
   * Gets last error.
   *
   * @return the last error
   */
  public String getLastError() {
    if (errorMessages.size() == 0) {
      return null;
    }
    return errorMessages.get(errorMessages.size() - 1);
  }

  /**
   * Returns true if exit called, false if not.
   *
   * @return whether exit was called
   */
  public boolean wasExitCalled() {
    return wasExitCalled;
  }
}
