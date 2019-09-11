import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.Test;
import store.Store;

public class DukeTest {

  @Test
  void testTodo() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("todo work");
    duke.parseAndExecuteCommand("list");
    assertEquals("1. [T][X] work\n", ui.getLastMessage());
  }

  @Test
  void testEvent() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("event test /at 12/12/2012 1212");
    duke.parseAndExecuteCommand("list");
    assertEquals("1. [E][X] test (:at 12/12/2012 1212)\n", ui.getLastMessage());
  }

  @Test
  void testEventWrongArguments1() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("event test /at abc");
    duke.parseAndExecuteCommand("list");
    assertEquals("Opps! I expected a date in the format: DD/MM/YYYY HHmm\n"
        + "\t(e.g. 31/01/2019 2359)", ui.getLastError());
  }

  @Test
  void testEventWrongArguments2() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("event test /notat 123");
    duke.parseAndExecuteCommand("list");
    assertEquals("Opps! I expected two arguments separated by '/at'.\n",
        ui.getLastError());
  }

  @Test
  void testDeadline() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("deadline homework /by 12/12/2012 1212");
    duke.parseAndExecuteCommand("list");
    assertEquals("1. [D][X] homework (:by 12/12/2012 1212)\n", ui.getLastMessage());
  }

  @Test
  void testCommands() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new StoreWrapper());
    duke.parseAndExecuteCommand("list");
    assertEquals("Yay! You don't have any tasks!\n", ui.getLastMessage());
  }

  @Test
  void testDeleteAndSaveAndLoadTasks() {
    UiWrapper ui = new UiWrapper();
    Duke duke = new Duke(ui, new Store("testing.txt", ui::error));
    duke.parseAndExecuteCommand("todo abc");
    duke.parseAndExecuteCommand("event 123123 /at 12/12/2018 2000");
    duke.parseAndExecuteCommand("list");
    assertEquals("2. [E][X] 123123 (:at 12/12/2018 2000)\n", ui.getLastMessage());
    duke.parseAndExecuteCommand("delete 1");
    duke.parseAndExecuteCommand("list");
    assertEquals("1. [E][X] 123123 (:at 12/12/2018 2000)\n", ui.getLastMessage());

    assertTrue(new File("testing.txt").delete());
  }
}
