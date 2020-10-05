package ru.qixi.redux;

public interface Action {

    int getId();

    CharSequence getType();

    Payload getPayload();

    Object getData();

    boolean useHandler();

}