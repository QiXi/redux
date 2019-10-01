package example;

import android.text.TextUtils;

import ru.qixi.redux.Cancelable;
import ru.qixi.redux.HandlerDispatcher;
import ru.qixi.redux.Payload;
import ru.qixi.redux.Presenter;
import ru.qixi.redux.Store;


public class TodoPresenter extends Presenter<TodoView, TodoViewModel> {

    private HandlerDispatcher dispatcher;
    private ActionsCreator    actionsCreator;
    private Cancelable        subscribe;

    TodoPresenter(HandlerDispatcher dispatcher) {
        super(new Store(new TodoReducer(), buildState()));
        this.dispatcher = dispatcher;
        this.actionsCreator = ActionsCreator.get(dispatcher);
    }

    static TodoViewModel buildState() {
        return new TodoViewModel();
    }

    @Override
    public void onStateChanged(TodoViewModel state, Payload payload) {
        view.updateUI(state.getTodos());
        if (state.canUndo()) {
            view.showSnackbar("Element deleted");
        }
    }

    void onResume() {
        subscribe = dispatcher.subscribe(store);
    }

    void onPause() {
        subscribe.cancel();
    }

    void onClickCheckAll() {
        checkAll();
    }

    void onClickAdd(String inputText) {
        addTodo(inputText);
        view.resetMainInput();
    }

    void onClickClearCompleted() {
        clearCompleted();
        view.resetMainCheck();
    }

    private void checkAll() {
        actionsCreator.toggleCompleteAll();
    }

    void onClickUndo() {
        actionsCreator.undoDestroy();
    }

    private void clearCompleted() {
        actionsCreator.destroyCompleted();
    }

    private void addTodo(String inputText) {
        if (validateInput(inputText)) {
            actionsCreator.create(inputText);
        }
    }

    private boolean validateInput(String inputText) {
        return !TextUtils.isEmpty(inputText);
    }

    ActionsCreator getActionsCreator() {
        return actionsCreator;
    }

}
