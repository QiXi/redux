package example;

public class TodoData implements Cloneable, Comparable<TodoData> {
    long    id;
    boolean complete;
    String  text;

    public TodoData(long id, String text) {
        this.id = id;
        this.text = text;
        this.complete = false;
    }

    public TodoData(long id, String text, boolean complete) {
        this.id = id;
        this.text = text;
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    @Override
    public TodoData clone() {
        return new TodoData(id, text, complete);
    }

    @Override
    public int compareTo(TodoData todo) {
        if (id == todo.getId()) {
            return 0;
        } else if (id < todo.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

}
