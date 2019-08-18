public class Deadline extends Task{
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
