import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskList implements Serializable, Iterable<Task> {
    private List<Task> todoList;

    private TaskList(){
        todoList = new ArrayList<>();
    }

    public static TaskList newTaskList(){
        return new TaskList();
    }


    public Task remove(int i) {
        return todoList.remove(i);
    }

    public int size() {
        return todoList.size();
    }

    public Task get(int i) {
        return todoList.get(i);
    }

    public void add(Task task) {
        todoList.add(task);
    }

    @NotNull
    @Override
    public Iterator<Task> iterator() {
        return todoList.iterator();
    }
}
