package ru.qixi.redux;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;


public abstract class RootReducer<State> implements Reducer<State> {

    SparseArray<List<Reducer<State>>> array;

    public RootReducer() {
        this.array = new SparseArray<>();
    }

    @Override
    public State reduce(State appState, Action action) {
        List<Reducer<State>> list = getReducerList(action.getId());
        if (list == null) {
            Log.w("Reducer", String.format("no reducer for action:%s", action));
            return appState;
        }
        for (Reducer reducer : list) {
            reducer.reduce(appState, action);
        }
        return appState;
    }

    protected List<Reducer<State>> getReducerList(int key) {
        return this.array.get(key);
    }

    protected void addReducer(int key, Reducer<State> reducer) {
        List<Reducer<State>> list = this.array.get(key);
        if (list == null) {
            list = new ArrayList<>();
            this.array.put(key, list);
        }
        if (!list.contains(reducer)) {
            list.add(reducer);
        }
    }

    protected void removeReducer(int key, Reducer<State> reducer) {
        List<Reducer<State>> list = getReducerList(key);
        if (list != null) {
            list.remove(reducer);
        }
    }

}