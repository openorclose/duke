import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Scanner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.Store;

public class StoreTest {

  private static String testFile;
  private static final String testData = "123123";

  @BeforeAll
  static void initTestFile() {
    String path = "test";
    while (new File(path + ".txt").exists()) {
      path += "1";
    }
    StoreTest.testFile = path + ".txt";
  }

  @Test
  void testSaveAndRetrieveData() {
    UiWrapper ui = new UiWrapper();
    Store store = new Store(testFile, ui::error);
    store.saveDataIntoFile(testData);
    assertEquals(0, ui.getErrorsSize());
    Scanner scanner = store.retrieveDataAsScanner();
    assertEquals(0, ui.getErrorsSize());
    int lines = 0;
    while (scanner.hasNextLine()) {
      lines++;
      if (lines == 1) {
        assertEquals(testData, scanner.nextLine());
      } else {
        scanner.nextLine();
      }
    }
    assertEquals(1, lines);
    scanner.close();
  }

  @AfterAll
  static void deleteTestFile() {
    assertTrue(new File(testFile).delete());
  }
}
