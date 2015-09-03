package com.xiaomei.yanyu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BizResult {

    private int code;

    private JsonElement msg;

    public JsonElement getMessage() {
        return msg;
    }
}
