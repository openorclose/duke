package command;

import java.util.ArrayList;
import java.util.function.Function;
import task.Task;
import ui.Ui;

public class FindCommand implements Command {

  private final Ui ui;
  private final Function<String, ArrayList<Task>> finder;

  public FindCommand(Ui ui, Function<String, ArrayList<Task>> finder) {

    this.ui = ui;
    this.finder = finder;
  }

  @Override
  public String name() {
    return "find";
  }

  @Override
  public void execute(String arguments) {
    ArrayList<Task> tasks = finder.apply(arguments);
    if (tasks.size() == 0) {
      ui.error("Opps! I couldn't find a task the contains:\n\t" + arguments);
    } else {
      ui.println("Here are the tasks I found matching your query:");
      tasks.stream().map(Task::toString).forEach(ui::println);
    }
  }
}
