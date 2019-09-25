package ru.qixi.redux;

import java.util.Map;


public class ReduxStore implements Store, ActionListener {

    final Reducer                      reducer;
    final Dispatcher                   dispatcher;
    final Map<CharSequence, ViewModel> state;

    public ReduxStore(Dispatcher dispatcher, Reducer reducer, Map<CharSequence, ViewModel> state) {
        this.dispatcher = dispatcher;
        this.reducer = reducer;
        this.state = state;
    }

    public ViewModel getState(CharSequence key) {
        return state.get(key);
    }

    @Override
    public void handleAction(Action action) {
        reduce(action);
        emitStoreChange(action.getCategory());
    }

    private void reduce(Action action) {
        ViewModel model = state.get(action.getCategory());
        if (model != null) {
            reducer.reduce(model, action);
        }
    }

    protected void emitStoreChange(final CharSequence category) {
        dispatcher.emitChange(new StoreChangeEvent() {
            @Override
            public CharSequence getCategory() {
                return category;
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
    }

    public interface StoreChangeEvent {

        CharSequence getCategory();

    }

}