package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import type.ErrorOutputter;

public class Store {

  private static final String DEFAULT_SAVE_FILE_PATH = "data.txt";
  private String filePath = Store.DEFAULT_SAVE_FILE_PATH;
  private ErrorOutputter errorOutputter;

  public Store() {

  }

  public Store(ErrorOutputter errorOutputter) {
    this.errorOutputter = errorOutputter;
  }

  public Store(String filePath, ErrorOutputter errorOutputter) {
    this.filePath = filePath;
    this.errorOutputter = errorOutputter;
  }

  public Scanner retrieveDataAsScanner() {
    try {
      return new Scanner(new File(filePath));
    } catch (FileNotFoundException e) {
      saveDataIntoFile("");
      return retrieveDataAsScanner();
    }
  }

  public void saveDataIntoFile(String data) {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
      bufferedWriter.write(data);
      bufferedWriter.flush();
      bufferedWriter.close();
    } catch (IOException e) {
      errorOutputter.accept("Opps! Unable to save to file.");
    }
  }
}
