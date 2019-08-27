import java.io.*;
import java.util.*;

public class Duke implements Serializable {
    // constants
    private static final String INDENT = "    ";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    // main loop behavior constants
    private static final int CONTINUE = 1;
    private static final int EXIT = 2;

    // class members
    // data members
    private Ui ui;
    private TaskList<Task> todoList;
    private Storage storage;
    private Parser parser;

    public static void main(String[] args) {
        new Duke(System.in, System.out, "data/duke.txt").run();
    }

    // takes in a input stream and output stream
    public Duke(InputStream inStream, OutputStream outStream, String saveLocation) {
        this.ui = Ui.newUi(inStream, outStream);

        this.todoList = new TaskList();
        this.storage = Storage.newStorage(saveLocation);
    }

    // runs duke
    // returns 0 on successful
    // returns 1 on ioexception / unsuccessful
    public int run() {
        int returnCode = 0;
        try {
            greet();
            Object obj = storage.load();
            todoList = obj == null ? new TaskList() : (TaskList) obj;
            mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
            returnCode = 1;
        } finally {
            ui.close();
        }
        return returnCode;
    }

    // prints out bye message to out stream
    private void bye() throws IOException {
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.write(String.format("%s Bye. Hope to see you again soon!\n", INDENT));
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
    }

    // main update loop where processinput is continuously called
    private void mainLoop() throws IOException {
        int code;
        do {
            code = processInput();
            if (code == EXIT) {
                bye();
            }
        } while (code == CONTINUE);
    }

    // main logic
    // returns EXIT if signalling to exit main loop
    // returns CONTINUE if to continue with loop
    private int processInput() throws IOException {
        String command = ui.readLine();
        if (command == null) {
            // EOF
            return EXIT;
        }
        try {
            String[] commandArgs = Parser.returnArgs(command);
            switch (commandArgs[0]){
            case "bye":
                return EXIT;
            case "list":
                listList(commandArgs);
                break;
            case "done":
                done(commandArgs);
                break;
            case "delete":
                delete(commandArgs);
                break;
            case "todo":
            case "deadline":
            case "event":
                addToList(commandArgs);
                break;
            default:
                throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            ui.write(String.format("%s %s\n", INDENT, e.getMessage()));
            ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            ui.flush();
        }
        return CONTINUE;
    }

    // input expected: "delete n"
    // deletes task n
    // throws duke exception if there is no task n
    private void delete(String[] args) throws IOException, DukeException {
        if (args.length != 2){
            throw new DukeException("\u2639 OOPS!!! Done function needs exactly one argument.");
        }
        if (!args[1].matches("\\d{1,9}")){
            throw new DukeException("\u2639 OOPS!!! Task number is too big.");
        }
        int thingToDo = Integer.parseInt(args[1]);
        Task removedTask;
        try {
            removedTask = todoList.remove(thingToDo - 1);
            ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            ui.write(String.format("%s Noted. I've removed this task: \n", INDENT));
            ui.write(String.format("%s   %s\n", INDENT, removedTask));
            ui.write(String.format("%s  Now you have %d tasks in the list.\n", INDENT, todoList.size()));
            ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            ui.flush();
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format("\u2639 OOPS!!! There is no task %d.", thingToDo));
        }
        storage.save(todoList);
    }

    // input expected: "done n"
    // marks task n as done
    // throws duke exception if there is no task n
    private void done(String[] args) throws IOException, DukeException {
        if (args.length != 2){
            throw new DukeException("\u2639 OOPS!!! Done function needs exactly one argument.");
        }
        if (!args[1].matches("\\d{1,9}")){
            throw new DukeException("\u2639 OOPS!!! Task number is too big.");
        }
        int thingToDo = Integer.parseInt(args[1]);
        try {
            todoList.get(thingToDo - 1).setState(Task.DONE);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format("\u2639 OOPS!!! There is no task %d.", thingToDo));
        }
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.write(String.format("%s Nice! I've marked this task as done: \n", INDENT));
        ui.write(String.format("%s   %s\n", INDENT, todoList.get(thingToDo - 1)));
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
        storage.save(todoList);
    }

    // input expected: "list"
    // input not checked
    // prints out list of tasks to out stream
    private void listList(String[] args) throws IOException {
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        int counter = 1;
        for (Task item : todoList) {
            ui.write(String.format("%s %d.%s\n", INDENT, counter++, item));
        }
        if (counter == 1) {
            ui.write(String.format("%s Such empty, much wow\n", INDENT));
        }
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
    }


    private void addToList(String[] args) throws IOException, DukeException {
        switch (args[0]) {
        case "todo":
            if(args.length != 2){
                throw new DukeException("\u2639 OOPS!!! Todo must have exactly one argument.");
            }
            try {
                todoList.add(Task.parseTodo(args[1]));
            } catch (NoSuchElementException e) {
                throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
            }
            break;
        case "deadline":
            try {
                todoList.add(Task.parseDeadline(args[1], args[2]));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException("\u2639 OOPS!!! A deadline must have a description and a date.");
            }
            break;
        case "event":
            try {
                todoList.add(Task.parseEvent(args[1], args[2]));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException("\u2639 OOPS!!! An event must have a description and a date.");
            }
            break;
        }
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.write(String.format("%s Got it. I've added this task: \n", INDENT));
        ui.write(String.format("%s   %s\n", INDENT, todoList.get(todoList.size() - 1)));
        ui.write(String.format("%s  Now you have %d tasks in the list.\n", INDENT, todoList.size()));
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
        storage.save(todoList);
    }

    private void echo(String command) throws IOException {
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.write(String.format("%s%s\n", INDENT, command));
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
    }

    private void greet() throws IOException {
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.write(String.format("%s Hello! I'm Duke\n", INDENT));
        ui.write(String.format("%s What can I do for you?\n", INDENT));
        ui.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        ui.flush();
    }

}

class DukeException extends Exception {
    DukeException(String error) {
        super(error);
    }
}

class DukeError extends Error {

}