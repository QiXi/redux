package example;


import java.util.ArrayList;
import java.util.List;


class TodoViewModel {

    final List<TodoData> todos;
    TodoData lastDeleted;


    TodoViewModel() {
        todos = new ArrayList<>();
    }


    List<TodoData> getTodos() {
        return todos;
    }


    boolean canUndo() {
        return lastDeleted != null;
    }

}