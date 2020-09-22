package ru.qixi.redux;


import android.os.Bundle;

import androidx.annotation.NonNull;


public class BundleAction implements Action {

    public static final String ACTION_TYPE_KEY = "type";

    private final int     id;
    private final Bundle  data;
    private final Payload payload;

    public static Action id(final int id) {
        return new BundleAction(id, null);
    }

    public static Action type(final String type) {
        Bundle params = new Bundle();
        params.putString(ACTION_TYPE_KEY, type);
        return new BundleAction(0, params);
    }

    public BundleAction(final int id, final Bundle data) {
        this.id = id;
        this.data = data;
        this.payload = new Payload() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public int getArg1() {
                return data.getInt(Payload.ARG1_KEY);
            }

            @Override
            public int getArg2() {
                return data.getInt(Payload.ARG2_KEY);
            }

            @Override
            public Object getObject() {
                return data.get(Payload.OBJECT_KEY);
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