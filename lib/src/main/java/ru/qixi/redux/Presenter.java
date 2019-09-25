package ru.qixi.redux;

public abstract class Presenter<T> implements EventListener {

    protected T          view;
    protected ReduxStore store;

    public Presenter(ReduxStore store) {
        this.store = store;
    }

    public void attachView(T view) {
        this.view = view;
        store.dispatcher.register(this);
    }

    public void detachView() {
        store.dispatcher.unregister(this);
    }

}