package com.xiaomei.yanyu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BizResult {

    public static final int SUCCESS = 0;

    private int code;

    private JsonElement msg;

    public int getCode() {
        return code;
    }

    public JsonElement getMessage() {
        return msg;
    }

    public boolean isSuccess() {
        return code == SUCCESS;
    }
}
