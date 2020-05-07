package ru.qixi.miniredux;

public interface Dispatcher {

    void dispatch(int action);

    void dispatch(int action, Object payload);

}