package ru.qixi.redux;

public interface StateChangeListener<S> {

    void onStateChanged(S event, CharSequence category);

}