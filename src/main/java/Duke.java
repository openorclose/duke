import java.awt.image.TileObserver;
import java.io.*;
import java.util.*;

public class Duke {
    // printing constants
    private static final String INDENT = "    ";
    private static final String HORIZONTAL_LINE = "____________________________________________________________";

    // main loop behavior constants
    private static final int CONTINUE = 0;
    private static final int EXIT = 1;

    // class members
    // iostreams
    private InputStream inStream;
    private BufferedReader in;
    private OutputStream outStream;
    private Writer out;
    // data members
    private List<Task> todoList;

    public static void main(String[] args){
        new Duke(System.in, System.out).run();
    }

    public Duke(InputStream inStream, OutputStream outStream){
        this.inStream = inStream;
        this.in = new BufferedReader(new InputStreamReader(inStream));
        this.outStream = outStream;
        this.out = new PrintWriter(outStream);

        this.todoList = new ArrayList<>();
    }

    public int run(){
        try {
            greet();
            mainLoop();
        } catch (IOException e) {
            System.out.println("Stream supplied to Duke is not a valid stream");
            return 1;
        }
        return 0;
    }

    private void bye() throws IOException {
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " Bye. Hope to see you again soon!" + "\n");
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void mainLoop() throws IOException{
        while(processInput() == CONTINUE);
    }

    private int processInput() throws IOException {
        String command = in.readLine();
        try {
            switch (command) {
                case "bye":
                    bye();
                    return EXIT;
                case "list":
                    listList(command);
                    break;
                default:
                    // regex matching for more complex commands

                    // check if it is `done` command
                    if (command.matches("(done )[\\d]+")) {
                        done(command);
                        break;
                        // verify command is correct for todo/deadline/event
                    } else if (command.matches("(delete )[\\d]+")) {
                        delete(command);
                        break;
                        // verify command is correct for todo/deadline/event
                    } else if (command.matches("(todo).*")) {
                        addToList(command);
                    } else if (command.matches("(deadline).*")) {
                        addToList(command);
                    } else if (command.matches("(event).*")) {
                        addToList(command);
                    } else {
                        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
            }
        } catch (DukeException e){
            out.write(INDENT + HORIZONTAL_LINE + "\n");
            out.write(INDENT + e.getMessage() + "\n");
            out.write(INDENT + HORIZONTAL_LINE + "\n");
            out.flush();
        }
        return CONTINUE;
    }

    private void delete(String command) throws IOException, DukeException{
        Scanner sc = new Scanner(command).useDelimiter("[\\D]+");
        int thingToDo = sc.nextInt(); // one indexed
        Task removedTask;
        try {
            removedTask = todoList.remove(thingToDo - 1);
            out.write(INDENT + HORIZONTAL_LINE + "\n");
            out.write(INDENT + " Noted. I've removed this task: " + "\n");
            out.write(INDENT + "   " + removedTask + "\n");
            out.write(INDENT + " Now you have " + todoList.size() + " tasks in the list." + "\n");
            out.write(INDENT + HORIZONTAL_LINE + "\n");
            out.flush();
        } catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("☹ OOPS!!! There is no task " + thingToDo + ".");
        }
    }

    private void done(String command) throws IOException, DukeException{
        Scanner sc = new Scanner(command).useDelimiter("[\\D]+");
        int thingToDo = sc.nextInt(); // one indexed
        try {
            todoList.get(thingToDo - 1).setState(Task.DONE);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new DukeException("☹ OOPS!!! There is no task " + thingToDo + ".");
        }
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " Nice! I've marked this task as done: " + "\n");
        out.write(INDENT + "   " + todoList.get(thingToDo - 1) + "\n");
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void listList(String command) throws IOException {
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        int counter = 1;
        for(Task item: todoList){
            out.write(INDENT + " " + counter++ + "." + item + "\n");
        }
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void addToList(String command) throws IOException, DukeException {
        if (command.matches("(.*(?<=\\s)(/at|/by)(?>\\s|$|\\z).*){2,}")){
            throw new DukeException("☹ OOPS!!! There are too many flags in the Task.");
        }
        Scanner sc = new Scanner(command).useDelimiter("((?<=todo)|(?<=deadline)|(?<=event)|(?<=\\s)/at|(?<=\\s)/by)(?>[\\s$])");
        String typeOfTask = sc.next();
        switch (typeOfTask) {
            case "todo":
                try {
                    todoList.add(Task.parseTodo(sc.next().trim()));
                } catch (NoSuchElementException e){
                    throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                }
                break;
            case "deadline":
                if(!command.matches("deadline.*/by.*")){
                    throw new DukeException("☹ OOPS!!! A deadline must have a /by flag.");
                }
                try{
                    todoList.add(Task.parseDeadline(sc.next().trim(), sc.next().trim()));
                } catch (NoSuchElementException e){
                    throw new DukeException("☹ OOPS!!! A deadline must have a description and a date.");
                }
                break;
            case "event":
                if(!command.matches("event.*/at.*")){
                    throw new DukeException("☹ OOPS!!! An event must have a /at flag.");
                }
                try{
                    todoList.add(Task.parseEvent(sc.next().trim(), sc.next().trim()));
                } catch (NoSuchElementException e){
                    throw new DukeException("☹ OOPS!!! An event must have a description and a date.");
                }
                break;
        }
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " Got it. I've added this task: " + "\n");
        out.write(INDENT + "   " + todoList.get(todoList.size() - 1) + "\n");
        out.write(INDENT + " Now you have " + todoList.size() + " tasks in the list." + "\n");
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void echo(String command) throws IOException {
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " " + command + "\n");
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void greet() throws IOException {
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " Hello! I'm Duke" + "\n");
        out.write(INDENT + " What can I do for you?" + "\n");
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

}

class Todo extends Task{
    Todo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T][" + getState() + "] " + getName();
    }
}

class Deadline extends Task{
    private String date;

    Deadline(String name, String date){
        super(name);
        this.date = date;
    }

    @Override
    public String toString() {
        return "[D][" + getState() + "] " + getName() + " (by: " + date + ")";
    }
}

class Event extends Task{
    private String date;

    Event(String name, String date){
        super(name);
        this.date = date;
    }

    @Override
    public String toString() {
        return "[E][" + getState() + "] " + getName() + " (at: " + date + ")";
    }
}

class Task{
    // state constants
    static final char DONE = '✓';
    static final char NOT_DONE = '✗';

    private char state; // dont use boolean
    private String name;

    Task (String name){
        this.name = name;
        this.state = NOT_DONE;
    }

    public static Todo parseTodo(String name) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(name);
    }

    public static Deadline parseDeadline(String name, String date) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if(date.isEmpty()){
            throw new DukeException("☹ OOPS!!! The date of a deadline cannot be empty.");
        }
        return new Deadline(name, date);
    }

    public static Event parseEvent(String name, String date) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("☹ OOPS!!! The description of an event cannot be empty.");
        } else if(date.isEmpty()){
            throw new DukeException("☹ OOPS!!! The date of an event cannot be empty.");
        }
        return new Event(name, date);
    }

    public void setState(char state) {
        this.state = state;
    }

    public char getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + state + "] " + name;
    }
}

class DukeException extends Exception{
    DukeException(String error){
        super(error);
    }
}

class DukeError extends Error{

}