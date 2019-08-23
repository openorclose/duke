import java.io.*;
import java.util.*;

public class Duke implements Serializable {
    // constants
    private static final String INDENT = "    ";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String SAVE_LOCATION = "data/duke.txt";

    // main loop behavior constants
    private static final int CONTINUE = 1;
    private static final int EXIT = 2;

    // class members
    // iostreams
    private InputStream inStream;
    private BufferedReader in;
    private OutputStream outStream;
    private Writer out;
    // data members
    private List<Task> todoList;

    private boolean Save() {
        try {
            FileOutputStream fileOut;
            File file = new File(SAVE_LOCATION);
            file.createNewFile();
            fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.todoList);
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean Load() {
        try {
            FileInputStream fileIn;
            File file = new File(SAVE_LOCATION);
            file.createNewFile();
            fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Task> incomingList = (List) in.readObject();
            this.todoList = incomingList;
            in.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public boolean copyState(Duke other) {
        try {
            this.todoList = other.getList();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Task> getList() {
        return new ArrayList(todoList);
    }

    public static void main(String[] args) {
        new Duke(System.in, System.out).run();
    }

    // takes in a input stream and output stream
    public Duke(InputStream inStream, OutputStream outStream) {
        this.inStream = inStream;
        this.in = new BufferedReader(new InputStreamReader(inStream));
        this.outStream = outStream;
        this.out = new PrintWriter(outStream);

        this.todoList = new ArrayList<>();
    }

    // runs duke
    // returns 0 on successful
    // returns 1 on ioexception / unsuccessful
    public int run() {
        int returnCode = 0;
        try {
            greet();
            Load();
            mainLoop();
        } catch (IOException e) {
            e.printStackTrace();
            returnCode = 1;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e1) {

            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e2) {

            }
        }
        return returnCode;
    }

    // prints out bye message to out stream
    private void bye() throws IOException {
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.write(String.format("%s Bye. Hope to see you again soon!\n", INDENT));
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
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
        String command = in.readLine();
        if (command == null) {
            // EOF
            return EXIT;
        }
        try {
            if (command.equals("bye")) {
                return EXIT;
            } else if (command.equals("list")) {
                listList(command);
                // check if it is `done` command
            } else if (command.matches("(done )[\\d]{1,9}")) {
                done(command);
                // verify command is correct for delete
            } else if (command.matches("(delete )[\\d]{1,9}")) {
                delete(command);
                // verify command is correct for todo/deadline/event
            } else if (command.matches("(todo|deadline|event).*")) {
                addToList(command);
            } else {
                throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        } catch (DukeException e) {
            out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            out.write(String.format("%s %s\n", INDENT, e.getMessage()));
            out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            out.flush();
        }
        return CONTINUE;
    }

    // input expected: "delete n"
    // deletes task n
    // throws duke exception if there is no task n
    private void delete(String command) throws IOException, DukeException {
        Scanner sc = new Scanner(command).useDelimiter("[\\D]+");
        int thingToDo = sc.nextInt(); // one indexed
        Task removedTask;
        try {
            removedTask = todoList.remove(thingToDo - 1);
            out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            out.write(String.format("%s Noted. I've removed this task: \n", INDENT));
            out.write(String.format("%s   %s\n", INDENT, removedTask));
            out.write(String.format("%s  Now you have %d tasks in the list.\n", INDENT, todoList.size()));
            out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
            out.flush();
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format("\u2639 OOPS!!! There is no task %d.", thingToDo));
        }
        Save();
    }

    // input expected: "done n"
    // marks task n as done
    // throws duke exception if there is no task n
    private void done(String command) throws IOException, DukeException {
        Scanner sc = new Scanner(command).useDelimiter("[\\D]+");
        int thingToDo = sc.nextInt(); // one indexed
        try {
            todoList.get(thingToDo - 1).setState(Task.DONE);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(String.format("\u2639 OOPS!!! There is no task %d.", thingToDo));
        }
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.write(String.format("%s Nice! I've marked this task as done: \n", INDENT));
        out.write(String.format("%s   %s\n", INDENT, todoList.get(thingToDo - 1)));
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
        Save();
    }

    // input expected: "list"
    // input not checked
    // prints out list of tasks to out stream
    private void listList(String command) throws IOException {
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        int counter = 1;
        for (Task item : todoList) {
            out.write(String.format("%s %d.%s\n", INDENT, counter++, item));
        }
        if (counter == 1) {
            out.write(String.format("%s Such empty, much wow\n", INDENT));
        }
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
    }


    private void addToList(String command) throws IOException, DukeException {
        if (command.matches("(.*(?<=\\s)(/at|/by)(?>\\s|$|\\z).*){2,}")) {
            throw new DukeException("\u2639 OOPS!!! There are too many flags in the Task.");
        }
        Scanner sc = new Scanner(command).useDelimiter("((?<=todo)|(?<=deadline)|(?<=event)|(?<=\\s)/at|(?<=\\s)/by)(?>[\\s$])");
        String typeOfTask = sc.next();
        switch (typeOfTask) {
        case "todo":
            try {
                todoList.add(Task.parseTodo(sc.next().trim()));
            } catch (NoSuchElementException e) {
                throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
            }
            break;
        case "deadline":
            if (!command.matches("deadline.*/by.*")) {
                throw new DukeException("\u2639 OOPS!!! A deadline must have a /by flag.");
            }
            try {
                todoList.add(Task.parseDeadline(sc.next().trim(), sc.next().trim()));
            } catch (NoSuchElementException e) {
                throw new DukeException("\u2639 OOPS!!! A deadline must have a description and a date.");
            }
            break;
        case "event":
            if (!command.matches("event.*/at.*")) {
                throw new DukeException("\u2639 OOPS!!! An event must have a /at flag.");
            }
            try {
                todoList.add(Task.parseEvent(sc.next().trim(), sc.next().trim()));
            } catch (NoSuchElementException e) {
                throw new DukeException("\u2639 OOPS!!! An event must have a description and a date.");
            }
            break;
        }
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.write(String.format("%s Got it. I've added this task: \n", INDENT));
        out.write(String.format("%s   %s\n", INDENT, todoList.get(todoList.size() - 1)));
        out.write(String.format("%s  Now you have %d tasks in the list.\n", INDENT, todoList.size()));
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
        Save();
    }

    private void echo(String command) throws IOException {
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.write(String.format("%s%s\n", INDENT, command));
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
    }

    private void greet() throws IOException {
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.write(String.format("%s Hello! I'm Duke\n", INDENT));
        out.write(String.format("%s What can I do for you?\n", INDENT));
        out.write(String.format("%s%s\n", INDENT, HORIZONTAL_LINE));
        out.flush();
    }

}

class DukeException extends Exception {
    DukeException(String error) {
        super(error);
    }
}

class DukeError extends Error {

}