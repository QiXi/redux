package ru.qixi.miniredux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ru.qixi.redux.Cancelable;


public class Store<State> implements Cursor<State>, Dispatcher {

    protected final Reducer<State>           reducer;
    protected final Set<StateChangeListener> subscribers = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    protected final State                    state;

    public Store(Reducer<State> reducer, State state) {
        this.reducer = reducer;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public Cancelable subscribe(final StateChangeListener subscriber) {
        subscribers.add(subscriber);
        return new Cancelable() {
            @Override
            public void cancel() {
                subscribers.remove(subscriber);
            }
        };
    }

    @Override
    public void dispatch(int action) {
        dispatch(action, null);
    }

    @Override
    public void dispatch(int action, Object payload) {
        State state = reduce(action, payload);
        emitStoreChange(state, action, payload);
    }

    private State reduce(int action, Object payload) {
        if (state != null) {
            return reducer.reduce(state, action, payload);
        }
        return null;
    }

    private void emitStoreChange(State state, int action, Object payload) {
        for (StateChangeListener<State> subscriber : subscribers) {
            subscriber.onStateChanged(state, action, payload);
        }
    }

}