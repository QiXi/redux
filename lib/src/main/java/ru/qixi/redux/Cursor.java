package ru.qixi.redux;

public interface Cursor<State> {

    State getState(CharSequence key);

    Cancelable subscribe(StateChangeListener<State> listener);

}