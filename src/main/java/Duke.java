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
    private List<String> todoList;

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
        switch (command){
            case "bye":
                bye();
                return EXIT;
            case "list":
                listList(command);
                break;
            default:
                addToList(command);
                break;
        }
        return CONTINUE;
    }

    private void listList(String command) throws IOException {
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        int counter = 0;
        for(String item: todoList){
            out.write(INDENT + " " + counter++ + ". " + item + "\n");
        }
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.flush();
    }

    private void addToList(String command) throws IOException {
        todoList.add(command);
        out.write(INDENT + HORIZONTAL_LINE + "\n");
        out.write(INDENT + " added: " + command + "\n");
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