import java.util.ArrayList;
import ui.Ui;

public class UiWrapper extends Ui {

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

  public int getMessagesSize() {
    return printedMessages.size();
  }

  public String getLastMessage() {
    if (printedMessages.size() == 0) {
      return null;
    }
    return printedMessages.get(printedMessages.size() - 1);
  }

  public int getErrorsSize() {
    return errorMessages.size();
  }

  public String getLastError() {
    if (errorMessages.size() == 0) {
      return null;
    }
    return errorMessages.get(errorMessages.size() - 1);
  }

  public boolean wasExitCalled() {
    return wasExitCalled;
  }
}
