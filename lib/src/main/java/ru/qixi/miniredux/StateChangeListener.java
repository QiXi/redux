package ru.qixi.miniredux;

public interface StateChangeListener<State> {

    void onStateChanged(State state, int action, Object payload);

}