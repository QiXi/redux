package ru.qixi.redux;

public interface Reducer<State> {

    State reduce(State state, Action action);

}