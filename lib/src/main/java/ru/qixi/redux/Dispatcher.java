package ru.qixi.redux;

public interface Dispatcher<T extends Action> {

    void dispatch(T action);
}