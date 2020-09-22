package ru.qixi.redux;

import java.util.HashMap;
import java.util.Map;


public class ParamAction implements Action {

    public static final String ACTION_TYPE_KEY    = "type";
    public static final String PAYLOAD_OBJECT_KEY = "obj";

    private final int                 id;
    private final Map<String, Object> data;
    private final Payload             payload;

    public static Action id(int id) {
        return new ParamAction(id, null);
    }

    public static Action object(int id, Object object) {
        Map<String, Object> data = new HashMap<>();
        data.put(PAYLOAD_OBJECT_KEY, object);
        return new ParamAction(id, data);
    }

    public ParamAction(final int id, final Map<String, Object> data) {
        this.id = id;
        this.data = data;
        this.payload = new Payload() {
            @Override
            public int getId() {
                return id;
            }

            @Override
            public int getArg1() {
                return (int) getParam(Payload.ARG1_KEY);
            }

            @Override
            public int getArg2() {
                return (int) getParam(Payload.ARG2_KEY);
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
    public CharSequence getType() {
        return (CharSequence) getParam(ACTION_TYPE_KEY);
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    public Object getParam(CharSequence key) {
        return (data != null) ? data.get(key) : null;
    }

    public Object getObject() {
        return getParam(PAYLOAD_OBJECT_KEY);
    }

    @Override
    public String toString() {
        return String.format("ParamAction{id:%d}", id);
    }

}