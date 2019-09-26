package example;


import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ru.qixi.redux.Action;
import ru.qixi.redux.Reducer;


public class TodoReducer implements Reducer<TodoViewModel> {

    @Override
    public TodoViewModel reduce(TodoViewModel viewModel, Action action) {
        long id;
        switch ((String) action.getType()) {
            case TodoActions.TODO_CREATE:
                String text = ((String) action.getData().get(TodoActions.KEY_TEXT));
                create(viewModel, text);
                break;

            case TodoActions.TODO_DESTROY:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                destroy(viewModel, id);
                break;

            case TodoActions.TODO_UNDO_DESTROY:
                undoDestroy(viewModel);
                break;

            case TodoActions.TODO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(viewModel, id, true);
                break;

            case TodoActions.TODO_UNDO_COMPLETE:
                id = ((long) action.getData().get(TodoActions.KEY_ID));
                updateComplete(viewModel, id, false);
                break;

            case TodoActions.TODO_DESTROY_COMPLETED:
                destroyCompleted(viewModel);
                break;

            case TodoActions.TODO_TOGGLE_COMPLETE_ALL:
                updateCompleteAll(viewModel);
                break;
        }

        return viewModel;//TOdO
    }


    private void addElement(List<TodoData> todos, TodoData clone) {
        todos.add(clone);
    }


    private void create(TodoViewModel model, String text) {
        long id = System.currentTimeMillis();
        TodoData todo = new TodoData(id, text);
        addElement(model.todos, todo);
        Collections.sort(model.todos);
    }


    private void destroy(TodoViewModel model, long id) {
        Iterator<TodoData> iter = model.todos.iterator();
        while (iter.hasNext()) {
            TodoData todo = iter.next();
            if (todo.getId() == id) {
                model.lastDeleted = todo.clone();
                iter.remove();
                break;
            }
        }
    }


    private void undoDestroy(TodoViewModel model) {
        if (model.lastDeleted != null) {
            addElement(model.todos, model.lastDeleted.clone());
            Collections.sort(model.todos);
            model.lastDeleted = null;
        }
    }


    private void updateComplete(TodoViewModel model, long id, boolean complete) {
        TodoData todo = getById(model.todos, id);
        if (todo != null) {
            todo.setComplete(complete);
        }
    }


    private TodoData getById(List<TodoData> list, long id) {
        Iterator<TodoData> iter = list.iterator();
        while (iter.hasNext()) {
            TodoData todo = iter.next();
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }


    private void destroyCompleted(TodoViewModel model) {
        Iterator<TodoData> iter = model.todos.iterator();
        while (iter.hasNext()) {
            TodoData todo = iter.next();
            if (todo.isComplete()) {
                iter.remove();
            }
        }
    }


    private void updateCompleteAll(TodoViewModel model) {
        if (areAllComplete(model.todos)) {
            updateAllComplete(model.todos, false);
        } else {
            updateAllComplete(model.todos, true);
        }
    }


    private boolean areAllComplete(List<TodoData> list) {
        for (TodoData todo : list) {
            if (!todo.isComplete()) {
                return false;
            }
        }
        return true;
    }


    private void updateAllComplete(List<TodoData> list, boolean complete) {
        for (TodoData todo : list) {
            todo.setComplete(complete);
        }
    }

}