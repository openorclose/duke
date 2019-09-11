package command;

import type.ErrorOutputter;

public class ShowStatisticsCommand implements Command {

  private final ErrorOutputter errorOutputter;
  private final Runnable statisticsPrinter;

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
