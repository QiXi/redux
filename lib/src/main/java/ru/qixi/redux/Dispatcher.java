package ru.qixi.redux;

public interface Dispatcher {

    void register(ActionListener callback);

    void unregister(ActionListener callback);

    void register(EventListener callback);

    void unregister(EventListener callback);

    void dispatch(Action action);

    void emitChange(ReduxStore.StoreChangeEvent event);

}