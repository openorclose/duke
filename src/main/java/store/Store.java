package main.java.store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import main.java.ui.Ui;

public class Store {

  private static final String SAVE_FILE_PATH = "data.txt";

  public static Scanner retrieveDataAsScanner() {
    try {
      return new Scanner(new File(Store.SAVE_FILE_PATH));
    } catch (FileNotFoundException e) {
      saveDataIntoFile("");
      return retrieveDataAsScanner();
    }
  }

  public static void saveDataIntoFile(String data) {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Store.SAVE_FILE_PATH));
      bufferedWriter.write(data);
      bufferedWriter.flush();
    } catch (IOException e) {
      Ui.error("Opps! Unable to save to file.");
    }
  }
}
