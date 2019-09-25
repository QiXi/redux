package example;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import ru.qixi.redux.Dispatcher;
import ru.qixi.redux.Presenter;
import ru.qixi.redux.ReduxStore;
import ru.qixi.redux.ViewModel;


public class TodoPresenter extends Presenter<TodoView> {

    private Dispatcher     dispatcher;
    private ActionsCreator actionsCreator;


    TodoPresenter(Dispatcher dispatcher) {
        super(new ReduxStore(dispatcher, new TodoReducer(), getStateList()));
        this.dispatcher = dispatcher;
        this.actionsCreator = ActionsCreator.get(dispatcher);
    }

    static Map<CharSequence, ViewModel> getStateList() {
        Map<CharSequence, ViewModel> result = new HashMap<>();
        result.put(TodoActions.CATEGORY, new TodoViewModel());
        return result;
    }

    @Override
    public void handleEvent(ReduxStore.StoreChangeEvent event) {
        String category = (String) event.getCategory();
        switch (category) {
            case TodoActions.CATEGORY:
                TodoViewModel model = (TodoViewModel) store.getState(category);
                view.updateUI(model.getTodos());
                if (model.canUndo()) {
                    view.showSnackbar("Element deleted");
                }
                break;
        }
    }

    void onResume() {
        dispatcher.register(store);
    }

    void onPause() {
        dispatcher.unregister(store);
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
