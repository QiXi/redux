package ru.qixi.redux;

public interface Dispatcher<T> {

    void dispatch(T action);

}