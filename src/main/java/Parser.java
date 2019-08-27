import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    public static String[] checkRegex = {
            "bye", // bye
            "list", // list
            "todo .+", // todo
            "deadline .+? /by .+?", // deadline
            "event .+? /at .+?", // event
    };
    public static String[] returnArgs(String input) {
        Scanner sc = new Scanner(input).useDelimiter("(?<= )");
        List<String> ar = new ArrayList<>();
        String first = sc.next().trim();
        ar.add(first);
        switch (first){
        case "deadline":
            sc.useDelimiter(" /by ");
            break;
        case "event":
            sc.useDelimiter(" /at ");
            break;
        default:
            sc.useDelimiter("^$");
            break;
        }
        while(sc.hasNext()){
            ar.add(sc.next());
        }
        String[] rtv = new String[ar.size()];
        ar.toArray(rtv);
        return rtv;
    }
    public static boolean checkValidCommand(String input) throws DukeException {
        for(String regex: checkRegex){
            if(input.matches(regex)){
                return true;
            }
        }
        return false;
    }
}