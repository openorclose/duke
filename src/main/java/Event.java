import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task{
    private String date;
    private Date dateTime;

    Event(String name, String date){
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
        return "[E][" + getState() + "] " + getName() + " (at: " + (date != null ? date : new SimpleDateFormat("MMM dd yyyy',' hh:mmaa").format(dateTime)) + ")";
    }
}
