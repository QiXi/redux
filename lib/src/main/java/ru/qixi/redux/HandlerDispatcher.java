package ru.qixi.redux;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HandlerDispatcher implements Dispatcher<Action> {

    private static final int               MSG_EVENT = 9991;
    private static       HandlerDispatcher instance;

    private Handler                 handler     = new Handler(Looper.getMainLooper(), new IncomingHandlerCallback());
    private Set<Dispatcher<Action>> subscribers = Collections.synchronizedSet(new HashSet<Dispatcher<Action>>());

    public static HandlerDispatcher get() {
        if (instance == null) {
            instance = new HandlerDispatcher();
        }
        return instance;
    }

    HandlerDispatcher() {

    }

    @Override
    public void dispatch(Action action) {
        Message msg = handler.obtainMessage(MSG_EVENT, action);
        handler.sendMessage(msg);
    }

    public Cancelable subscribe(final Dispatcher<Action> subscriber) {
        subscribers.add(subscriber);
        return new Cancelable() {
            @Override
            public void cancel() {
                subscribers.remove(subscriber);
            }
        };
    }

    private void dispatchAction(Action action) {
        for (Dispatcher<Action> subscriber : subscribers) {
            subscriber.dispatch(action);
        }
    }

    private class IncomingHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message pMessage) {
            switch (pMessage.what) {
                case MSG_EVENT:
                    dispatchAction((Action) pMessage.obj);
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}