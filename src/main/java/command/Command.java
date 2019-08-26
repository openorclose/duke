package command;

/**
 * Command interface.
 */
public interface Command {

  /**
   * The name used to execute the command.
   * e.g. in the command "todo work", "todo" is the name.
   * @return name
   */
  String name();

  /**
   * The arguments to the command.
   * e.g. in the command "todo work", "work" is the argument.
   * e.g. in the command "event party /at 12/12/2012 1212",
   * "party /at 12/12/2012 1212" are the arguments.
   * Note that the arguments are in one long string.
   * @param arguments the arguments
   */
  void execute(String arguments);
}
