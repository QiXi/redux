package ru.qixi.redux;

import java.util.Map;

public interface Action {

    CharSequence getType();

    CharSequence getCategory();

    Map getData();

}