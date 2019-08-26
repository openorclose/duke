package store;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import type.ErrorOutputter;

/**
 * Task storer and retriever.
 */
public class Store {

  private static final String DEFAULT_SAVE_FILE_PATH = "data.txt";
  private String filePath = Store.DEFAULT_SAVE_FILE_PATH;
  private ErrorOutputter errorOutputter;

  /**
   * Instantiates a new Store.
   */
  public Store() {

  }

  /**
   * Instantiates a new Store.
   *
   * @param errorOutputter the error outputter
   */
  public Store(ErrorOutputter errorOutputter) {
    this.errorOutputter = errorOutputter;
  }

  /**
   * Instantiates a new Store.
   *
   * @param filePath       the file path
   * @param errorOutputter the error outputter
   */
  public Store(String filePath, ErrorOutputter errorOutputter) {
    this.filePath = filePath;
    this.errorOutputter = errorOutputter;
  }

  /**
   * Retrieve data as scanner.
   *
   * @return the scanner
   */
  public Scanner retrieveDataAsScanner() {
    try {
      return new Scanner(new File(filePath));
    } catch (FileNotFoundException e) {
      saveDataIntoFile("");
      return retrieveDataAsScanner();
    }
  }

  /**
   * Save data into file.
   *
   * @param data the data
   */
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
