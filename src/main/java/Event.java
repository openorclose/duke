public class Event extends Task{
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
