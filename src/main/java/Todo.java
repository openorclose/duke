class Todo extends Task{
    Todo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T][" + getState() + "] " + getName();
    }
}
