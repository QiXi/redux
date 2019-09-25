package ru.qixi.redux;

public interface Store {

    ViewModel getState(CharSequence key);

}