package ru.qixi.redux;

public interface Payload {

    String ARG1_KEY   = "arg1";
    String ARG2_KEY   = "arg2";
    String OBJECT_KEY = "obj";

    int getId();

    int getArg1();

    int getArg2();

    Object getObject();

}