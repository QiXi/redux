package example;


import java.util.ArrayList;
import java.util.List;

import ru.qixi.redux.ViewModel;


class TodoViewModel implements ViewModel {

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