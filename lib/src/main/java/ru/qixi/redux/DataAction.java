package ru.qixi.redux;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;


public class DataAction implements Action {

    public static final String ACTION_TYPE_KEY = "type";

    public static final String PAYLOAD_ARG1_KEY   = "arg1";
    public static final String PAYLOAD_ARG2_KEY   = "arg2";
    public static final String PAYLOAD_OBJECT_KEY = "obj";

    private final int                 id;
    private final Map<String, Object> data;
    private final Payload             payload;

    public static Action id(final int id) {
        return new DataAction(id, null);
    }

    public static Action obj(final int id, final Object object) {
        Map<String, Object> params = new HashMap<>();
        params.put(PAYLOAD_OBJECT_KEY, object);
        return new DataAction(id, params);
    }

    public static Action def(final int id, final int type, final int arg1, final int arg2, final Object object) {
        Map<String, Object> params = new HashMap<>();
        params.put(ACTION_TYPE_KEY, type);
        params.put(PAYLOAD_ARG1_KEY, arg1);
        params.put(PAYLOAD_ARG2_KEY, arg2);
        params.put(PAYLOAD_OBJECT_KEY, object);
        return new DataAction(id, params);
    }

    public DataAction(final int id, final Map<String, Object> data) {
        this.id = id;
        this.data = data;
        this.payload = new Payload() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public int getArg1() {
                return (int) data.get(PAYLOAD_ARG1_KEY);
            }

            @Override
            public int getArg2() {
                return (int) data.get(PAYLOAD_ARG2_KEY);
            }

            @Override
            public Object getObject() {
                return data.get(PAYLOAD_OBJECT_KEY);
            }

            @Override
            public Object getObject(String key) {
                return data.get(key);
            }
        };
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return (String) data.get(ACTION_TYPE_KEY);
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public boolean useHandler() {
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("{id:%d data:%s}", id, data);
    }
}