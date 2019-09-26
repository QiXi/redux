package example;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import ru.qixi.redux.Cancelable;
import ru.qixi.redux.HandlerDispatcher;
import ru.qixi.redux.Presenter;
import ru.qixi.redux.Store;


public class TodoPresenter extends Presenter<TodoView, TodoViewModel> {

    private HandlerDispatcher dispatcher;
    private ActionsCreator    actionsCreator;
    private Cancelable        subscribe;

    TodoPresenter(HandlerDispatcher dispatcher) {
        super(new Store(new TodoReducer(), getStateList()));
        this.dispatcher = dispatcher;
        this.actionsCreator = ActionsCreator.get(dispatcher);
    }

    static Map<CharSequence, TodoViewModel> getStateList() {
        Map<CharSequence, TodoViewModel> result = new HashMap<>();
        result.put(TodoActions.CATEGORY, new TodoViewModel());
        return result;
    }

    @Override
    public void onStateChanged(TodoViewModel state, CharSequence key) {
        String category = (String) key;
        switch (category) {
            case TodoActions.CATEGORY:
                TodoViewModel model = store.getState(category);
                view.updateUI(model.getTodos());
                if (model.canUndo()) {
                    view.showSnackbar("Element deleted");
                }
                break;
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
