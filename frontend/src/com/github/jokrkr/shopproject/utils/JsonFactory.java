package com.github.jokrkr.shopproject.utils;

import org.json.JSONObject;

public class JsonFactory {

    public static JSONObject createLoginJson(String username, String password) {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        return json;
    }

    public static JSONObject createJson() {
        return new JSONObject();
        // todo make additional functions for other use-cases
    }
}
