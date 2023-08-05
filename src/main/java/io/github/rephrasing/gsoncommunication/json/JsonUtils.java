package io.github.rephrasing.gsoncommunication.json;

import com.google.gson.JsonObject;

public class JsonUtils {

    public static JsonObject singleValue(String key, String value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, int value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, long value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, double value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, float value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, boolean value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject singleValue(String key, char value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }
}
