package ru.qixi.redux;

import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class Store<State> implements Cursor, Dispatcher<Action> {

    final           Reducer<State>           reducer;
    final           Set<StateChangeListener> subscribers      = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    protected final Set<StateChangeListener> pendingAdditions = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    protected final Set<StateChangeListener> pendingRemovals  = Collections.synchronizedSet(new HashSet<StateChangeListener>());
    final           State                    state;

    public Store(Reducer<State> reducer, State state) {
        this.reducer = reducer;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public Cancelable subscribe(final StateChangeListener subscriber) {
        pendingAdditions.add(subscriber);
        return new Cancelable() {
            @Override
            public void cancel() {
                pendingRemovals.add(subscriber);
            }
        };
    }

    @Override
    public void dispatch(int action) {
        dispatch(DataAction.id(action));
    }

    @Override
    public void dispatch(int action, Object payload) {
        dispatch(DataAction.obj(action, payload));
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
        commitUpdates();
        for (StateChangeListener<State> subscriber : subscribers) {
            subscriber.onStateChanged(state, payload);
        }
    }

    public void commitUpdates() {
        Log.d("Store",
                String.format("commitUpdates subscribers=%d +%d -%d",
                        subscribers.size(), pendingAdditions.size(), pendingRemovals.size()));
        if (!pendingAdditions.isEmpty()) {
            subscribers.addAll(pendingAdditions);
            pendingAdditions.clear();
        }
        if (!pendingRemovals.isEmpty()) {
            subscribers.removeAll(pendingRemovals);
            pendingRemovals.clear();
        }
    }

}