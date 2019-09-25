package ru.qixi.redux;


public interface Reducer {

    ViewModel reduce(ViewModel viewModel, Action action);

}