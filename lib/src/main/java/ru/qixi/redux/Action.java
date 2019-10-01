package ru.qixi.redux;

import java.util.Map;


public interface Action extends Payload {

    CharSequence getType();

    Payload getPayload();

    Map getData();

}