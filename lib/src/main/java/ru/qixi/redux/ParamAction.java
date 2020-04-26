package ru.qixi.redux;


import android.os.Bundle;

import androidx.annotation.NonNull;


public class ParamAction implements Action {

    public static final String ACTION_TYPE_KEY = "type";

    public static final String PAYLOAD_ARG1_KEY   = "arg1";
    public static final String PAYLOAD_ARG2_KEY   = "arg2";
    public static final String PAYLOAD_OBJECT_KEY = "obj";

    private final int     id;
    private final Bundle  data;
    private final Payload payload;

    public static Action id(final int id) {
        return new ParamAction(id, null);
    }

    public static Action type(final String type) {
        Bundle params = new Bundle();
        params.putString(ACTION_TYPE_KEY, type);
        return new ParamAction(0, params);
    }

    public ParamAction(final int id, final Bundle data) {
        this.id = id;
        this.data = data;
        this.payload = new Payload() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public int getArg1() {
                return data.getInt(PAYLOAD_ARG1_KEY);
            }

            @Override
            public int getArg2() {
                return data.getInt(PAYLOAD_ARG2_KEY);
            }

            @Override
            public Object getObject() {
                return data.get(PAYLOAD_OBJECT_KEY);
            }
        };
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return data.getString(ACTION_TYPE_KEY);
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

    @Override
    public Bundle getData() {
        return data;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("{id:%d data:%s}", id, data);
    }

}