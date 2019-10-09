package ru.qixi.redux;

public abstract class ActionCreator {

    protected Dispatcher<Action> dispatcher;

    public ActionCreator(Dispatcher<Action> dispatcher) {
        this.dispatcher = dispatcher;
    }

}