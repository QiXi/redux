package ru.qixi.redux;


import java.util.HashMap;
import java.util.Map;


public class ParamAction implements Action {

    private final String              type;
    private final String              category;
    private final Map<String, Object> data;

    ParamAction(String type, String category, Map<String, Object> data) {
        this.type = type;
        this.category = category;
        this.data = data;
    }

    public static Builder type(String type) {
        return new Builder().with(type);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public Map getData() {
        return data;
    }

    public static class Builder {

        private String              type;
        private String              category;
        private Map<String, Object> data;

        Builder with(String type) {
            if (type == null) {
                throw new IllegalArgumentException("Type may not be null.");
            }
            this.type = type;
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

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public ParamAction build() {
            if (type == null || type.isEmpty()) {
                throw new IllegalArgumentException("At least one key is required.");
            }
            return new ParamAction(type, category, data);
        }
    }

}