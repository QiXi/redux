package ru.qixi.redux;


import java.util.HashMap;
import java.util.Map;


public class ParamAction implements Action {

    private final int                 id;
    private final String              type;
    private final Map<String, Object> data;

    ParamAction(int id, String type, Map<String, Object> data) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public static Builder type(String type) {
        return new Builder().with(type);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Payload getPayload() {
        return null;
    }

    @Override
    public Map getData() {
        return data;
    }


    @Override
    public int getArg1() {
        return 0;
    }

    @Override
    public int getArg2() {
        return 0;
    }

    @Override
    public Object getObject() {
        return type;
    }

    public static class Builder {

        private int                 id;
        private String              type;
        private Map<String, Object> data;

        Builder with(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Type may not be null.");
            }
            this.type = type;
            this.data = new HashMap<>();
            return this;
        }

        Builder with(int id) {
            this.id = id;
            this.data = new HashMap<>();
            return this;
        }

        public Builder bundle(String key, Object value) {
            if (key == null) {
                throw new IllegalArgumentException("Key may not be null.");
            }

            if (value == null) {
                throw new IllegalArgumentException("Value may not be null.");
            }
            data.put(key, value);
            return this;
        }

        public ParamAction build() {
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("At least one key is required.");
            }
            return new ParamAction(id, type, data);
        }
    }

}