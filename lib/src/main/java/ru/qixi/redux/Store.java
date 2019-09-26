package ru.qixi.redux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Store<State> implements Cursor, Dispatcher<Action> {

    final Reducer<State>           reducer;
    final Set<StateChangeListener> listeners = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    final Map<CharSequence, State> state;

    public Store(Reducer<State> reducer, Map<CharSequence, State> state) {
        this.reducer = reducer;
        this.state = state;
    }

    public State getState(CharSequence key) {
        return state.get(key);
    }

    @Override
    public Cancelable subscribe(final StateChangeListener listener) {
        listeners.add(listener);
        return new Cancelable() {
            @Override
            public void cancel() {
                listeners.remove(listener);
            }
        };
    }

    @Override
    public void dispatch(Action action) {
        State state = reduce(action);
        emitStoreChange(state, action.getCategory());
    }

    private State reduce(Action action) {
        State state = this.state.get(action.getCategory());
        if (state != null) {
            return reducer.reduce(state, action);
        }
        return null;
    }

    private void emitStoreChange(State state, CharSequence category) {
        for (StateChangeListener<State> listener : listeners) {
            listener.onStateChanged(state, category);
        }
    }

}