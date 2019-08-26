import java.util.Scanner;
import store.Store;

public class StoreWrapper extends Store {

  @Override
  public void saveDataIntoFile(String data) {
  }

  @Override
  public Scanner retrieveDataAsScanner() {
    return new Scanner("");
  }
}
