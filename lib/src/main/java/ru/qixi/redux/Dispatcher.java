package ru.qixi.redux;

public interface Dispatcher<T extends Action> {

    void dispatch(int action);

    void dispatch(int action, Object payload);

    void dispatch(T action);
}