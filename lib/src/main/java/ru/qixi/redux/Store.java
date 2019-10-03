package ru.qixi.redux;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Store<State> implements Cursor, Dispatcher<Action> {

    final Reducer<State>           reducer;
    final Set<StateChangeListener> subscribers = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    final State                    state;

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
    public void dispatch(Action action) {
        State state = reduce(action);
        emitStoreChange(state, action.getPayload());
    }

    private State reduce(Action action) {
        if (state != null) {
            return reducer.reduce(state, action);
        }
        return null;
    }

    private void emitStoreChange(State state, Payload payload) {
        for (StateChangeListener<State> subscriber : subscribers) {
            subscriber.onStateChanged(state, payload);
        }
    }

}