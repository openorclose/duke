import java.io.Serializable;

class Task implements Serializable {
    // state constants
    static final char DONE = '\u2713';
    static final char NOT_DONE = '\u2717';

    private char state; // dont use boolean
    private String name;

    Task (String name){
        this.name = name;
        this.state = NOT_DONE;
    }

    public static Todo parseTodo(String name) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("\u2639 OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(name);
    }

    public static Deadline parseDeadline(String name, String date) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("\u2639 OOPS!!! The description of a deadline cannot be empty.");
        } else if(date.isEmpty()){
            throw new DukeException("\u2639 OOPS!!! The date of a deadline cannot be empty.");
        }
        return new Deadline(name, date);
    }

    public static Event parseEvent(String name, String date) throws DukeException {
        if(name.isEmpty()){
            throw new DukeException("\u2639 OOPS!!! The description of an event cannot be empty.");
        } else if(date.isEmpty()){
            throw new DukeException("\u2639 OOPS!!! The date of an event cannot be empty.");
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
