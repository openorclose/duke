import java.io.IOException;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.Ui;

/**
 * The GUI for Duke.
 */
public class DukeGui extends Application implements Ui {

  private static final String FX_BACKGROUND_COLOR = "-fx-background-color: ";
  /**
   * The scroll pane.
   */
  public ScrollPane scrollPane;
  /**
   * The text field for users to input their commands.
   */
  public TextField commandField;
  /**
   * The Output v box to show user inputs and Duke's outputs.
   */
  public VBox outputVBox;
  private Consumer<String> commandExecutor;

  private enum Color {
    /**
     * Error color.
     */
    ERROR,
    /**
     * Success color.
     */
    SUCCESS,
    /**
     * User input color.
     */
    USER_INPUT;

    public String toString() {
      switch (this) {
      case ERROR:
        return "rgba(200, 0, 0, 0.4)";
      case SUCCESS:
        return "rgba(0, 200, 0, 0.4)";
      default:
        return "rgba(100, 100, 100, 0.4)";
      }
    }
  }

  /**
   * Instantiates a new Duke gui.
   */
  public DukeGui() {
    Duke duke = new Duke(this);
    commandExecutor = duke::parseAndExecuteCommand;
  }

  private void appendText(String text, Color color) {
    Label label = new Label();
    label.setStyle(FX_BACKGROUND_COLOR + color.toString());
    label.setText(text);
    outputVBox.getChildren().add(label);
  }

  @Override
  public void print(String string) {
    appendText(string, Color.SUCCESS);
  }

  @Override
  public void error(String message) {
    appendText(message, Color.ERROR);
  }

  @Override
  public void exit(String message) {
    Platform.exit();
  }

  @Override
  public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/gui.fxml"));
    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.setTitle("DUKE");
    stage.show();
    root.requestFocus();
  }


  /**
   * Execute command if enter pressed on the user input.
   *
   * @param keyEvent the key event
   */
  public void executeCommandIfEnterPressed(KeyEvent keyEvent) {
    if (keyEvent.getCode() == KeyCode.ENTER) {
      String input = commandField.getText();
      appendText(input, Color.USER_INPUT);
      commandExecutor.accept(input);
      commandField.setText("");
      scrollPane.vvalueProperty().bind(outputVBox.heightProperty());
    }
  }
}
