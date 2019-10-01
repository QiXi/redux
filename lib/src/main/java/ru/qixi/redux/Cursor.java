package ru.qixi.redux;

public interface Cursor<State> {

    State getState();

    Cancelable subscribe(StateChangeListener<State> listener);

}