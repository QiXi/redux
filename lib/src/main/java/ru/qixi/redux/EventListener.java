package ru.qixi.redux;

public interface EventListener {

    void handleEvent(ReduxStore.StoreChangeEvent action);

}