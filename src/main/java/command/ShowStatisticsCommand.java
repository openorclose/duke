package command;

import type.ErrorOutputter;

/**
 * Command to show statistics.
 */
public class ShowStatisticsCommand implements Command {

  private final ErrorOutputter errorOutputter;
  private final Runnable statisticsPrinter;

  /**
   * Instantiates a new Show statistics command.
   *
   * @param errorOutputter    the error outputter
   * @param statisticsPrinter the statistics printer
   */
  public ShowStatisticsCommand(ErrorOutputter errorOutputter, Runnable statisticsPrinter) {

    this.errorOutputter = errorOutputter;
    this.statisticsPrinter = statisticsPrinter;
  }

  @Override
  public String name() {
    return "stats";
  }

  @Override
  public void execute(String arguments) {
    try {
      statisticsPrinter.run();
    } catch (Exception e) {
      errorOutputter.accept("You have no tasks!");
    }
  }
}
