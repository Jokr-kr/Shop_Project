package com.github.jokrkr.shopproject.utils;

import org.json.JSONObject;

public class JsonFactory {

    public static JSONObject createLoginJson(String username, String password) {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        return json;
    }

    public static JSONObject createCustomJson(Object... pairs) {
        JSONObject json = new JSONObject();
        for (int i = 0; i < pairs.length; i += 2) {
            if (i + 1 < pairs.length) {
                json.put(pairs[i].toString(), pairs[i + 1]);
            } else {
                throw new IllegalArgumentException("Invalid number of arguments, key without value.");
            }
        }
        return json;
    }

    public static JSONObject createJson() {
        return new JSONObject();
        // todo make additional functions for other use-cases
    }


}
