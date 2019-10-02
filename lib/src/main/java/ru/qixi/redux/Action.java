package ru.qixi.redux;

import android.os.Bundle;


public interface Action {

    int getId();

    CharSequence getType();

    Payload getPayload();

    Bundle getData();

}