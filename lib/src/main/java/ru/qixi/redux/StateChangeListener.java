package ru.qixi.redux;

public interface StateChangeListener<State> {

    void onStateChanged(State state, Payload payload);

}