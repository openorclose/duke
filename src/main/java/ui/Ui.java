package ui;

public class Ui {
  public void print(String string) {
    System.out.print(string);
  }

  public void println(String string) {
    System.out.println(string);
  }

  public void printf(String format, Object... args) {
    System.out.printf(format, args);
  }

  public void error(String message) {
    System.out.println(message);
  }

  public void exit(String message) {
    System.out.println(message);
    System.exit(0);
  }
}
