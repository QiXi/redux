package ru.qixi.redux;

public interface StateChangeListener<State> {

    void onStateChanged(State event, Payload payload);

}