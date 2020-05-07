package ru.qixi.miniredux;

import ru.qixi.redux.Cancelable;


public interface Cursor<State> {

    State getState();

    Cancelable subscribe(StateChangeListener<State> listener);

}