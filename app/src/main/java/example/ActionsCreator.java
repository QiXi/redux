package example;


import android.os.Bundle;

import ru.qixi.redux.Action;
import ru.qixi.redux.Dispatcher;
import ru.qixi.redux.ParamAction;


class ActionsCreator {

    private static ActionsCreator     instance;
    final          Dispatcher<Action> dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    void dispatch(String type) {
        dispatcher.dispatch(ParamAction.type(type));
    }

    void dispatch(String type, String key, String value) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }
        Bundle params = new Bundle();
        params.putString(ParamAction.ACTION_TYPE_KEY, type);
        params.putString(key, value);
        dispatcher.dispatch(new ParamAction(0, params));
    }

    void dispatch(String type, String key, long value) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }
        Bundle params = new Bundle();
        params.putString(ParamAction.ACTION_TYPE_KEY, type);
        params.putLong(key, value);
        dispatcher.dispatch(new ParamAction(0, params));
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }

    void create(String text) {
        dispatch(TodoActions.TODO_CREATE, TodoActions.KEY_TEXT, text);
    }

    void destroy(long id) {
        dispatch(TodoActions.TODO_DESTROY, TodoActions.KEY_ID, id);
    }

    void undoDestroy() {
        dispatch(TodoActions.TODO_UNDO_DESTROY);
    }

    void toggleComplete(TodoData todo) {
        long id = todo.getId();
        String actionType = todo.isComplete() ? TodoActions.TODO_UNDO_COMPLETE : TodoActions.TODO_COMPLETE;
        dispatch(actionType, TodoActions.KEY_ID, id);
    }

    void toggleCompleteAll() {
        dispatch(TodoActions.TODO_TOGGLE_COMPLETE_ALL);
    }

    void destroyCompleted() {
        dispatch(TodoActions.TODO_DESTROY_COMPLETED);
    }

}