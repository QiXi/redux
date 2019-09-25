package example;


import ru.qixi.redux.Dispatcher;
import ru.qixi.redux.ParamAction;


class ActionsCreator {

    private static ActionsCreator instance;
    final          Dispatcher     dispatcher;

    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    void dispatch(String type, Object... data) {
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        ParamAction.Builder actionBuilder = ParamAction.type(type).category(TodoActions.CATEGORY);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        dispatcher.dispatch(actionBuilder.build());
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