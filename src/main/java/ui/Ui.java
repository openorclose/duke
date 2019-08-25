package main.java.ui;

public class Ui {
  public static void print(String string) {
    System.out.print(string);
  }

  public static void println(String string) {
    System.out.println(string);
  }

  public static void printf(String format, Object... args) {
    System.out.printf(format, args);
  }

  public static void error(String message) {
    System.out.println(message);
  }
}
