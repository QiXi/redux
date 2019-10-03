package ru.qixi.redux;

public abstract class Presenter<V, State> implements StateChangeListener<State> {

    protected V            view;
    protected Store<State> store;
    protected Cancelable   subscribe;

    public Presenter(Store<State> store) {
        this.store = store;
    }

    public void attachView(V view) {
        this.view = view;
        this.subscribe = store.subscribe(this);
    }

    public void detachView() {
        this.subscribe.cancel();
    }

}