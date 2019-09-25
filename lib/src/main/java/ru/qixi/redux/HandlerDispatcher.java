package ru.qixi.redux;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class HandlerDispatcher implements Dispatcher {

    private static final int               MSG_EVENT        = 9991;
    private static final int               MSG_CHANGE_EVENT = 9992;
    private static       HandlerDispatcher instance;

    private Handler             handler    = new Handler(Looper.getMainLooper(), new IncomingHandlerCallback());
    private Set<ActionListener> dispatcher = Collections.synchronizedSet(new HashSet<ActionListener>());
    private Set<EventListener>  events     = Collections.synchronizedSet(new HashSet<EventListener>());

    public static HandlerDispatcher get() {
        if (instance == null) {
            instance = new HandlerDispatcher();
        }
        return instance;
    }

    HandlerDispatcher() {

    }

    public void register(ActionListener callback) {
        dispatcher.add(callback);
    }

    public void unregister(ActionListener callback) {
        dispatcher.remove(callback);
    }

    @Override
    public void register(EventListener callback) {
        events.add(callback);
    }

    @Override
    public void unregister(EventListener callback) {
        events.remove(callback);
    }

    @Override
    public void dispatch(Action action) {
        Message msg = handler.obtainMessage(MSG_EVENT, action);
        handler.sendMessage(msg);
    }

    public void emitChange(ReduxStore.StoreChangeEvent event) {
        Message msg = handler.obtainMessage(MSG_CHANGE_EVENT, event);
        handler.sendMessage(msg);
    }

    private void dispatchAction(Action action) {
        for (ActionListener listener : dispatcher) {
            listener.handleAction(action);
        }
    }

    private void dispatchChangeEvent(ReduxStore.StoreChangeEvent event) {
        for (EventListener listener : events) {
            listener.handleEvent(event);
        }
    }

    private class IncomingHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message pMessage) {
            switch (pMessage.what) {
                case MSG_EVENT:
                    dispatchAction((Action) pMessage.obj);
                    break;
                case MSG_CHANGE_EVENT:
                    dispatchChangeEvent((ReduxStore.StoreChangeEvent) pMessage.obj);
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}