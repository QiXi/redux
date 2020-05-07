package ru.qixi.miniredux;

public interface Reducer<State> {

    State reduce(State state, int action, Object payload);

}