import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task{
    private String date;
    private Date dateTime;

    Deadline(String name, String date){
        super(name);
        try{
            dateTime = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(date);
        }catch (ParseException e){
            e.printStackTrace();
            this.date = date;
        }
    }

    @Override
    public String toString() {
        return "[D][" + getState() + "] " + getName() + " (by: " + (date != null ? date : new SimpleDateFormat("MMM dd yyyy',' hh:mmaa").format(dateTime)) + ")";
    }
}
