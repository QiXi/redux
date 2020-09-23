package ru.qixi.redux;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class AsyncStore<State> extends Store<State> {

    private static final int MSG_EVENT = 9991;

    private Handler handler = new Handler(Looper.getMainLooper(), new IncomingHandlerCallback());


    public AsyncStore(Reducer<State> reducer, State state) {
        super(reducer, state);
    }

    @Override
    public void dispatch(Action action) {
        Message msg = handler.obtainMessage(MSG_EVENT, action);
        handler.sendMessage(msg);
    }

    public void dispatch(Action action, boolean useAsync) {
        if (useAsync) {
            dispatch(action);
        } else {
            super.dispatch(action);
        }
    }

    private class IncomingHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message pMessage) {
            switch (pMessage.what) {
                case MSG_EVENT:
                    dispatch((Action) pMessage.obj, false);
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}