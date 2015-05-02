package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

public class ActionShareCommentBuilder extends AbstractJSONBuilder {

    @Override
    protected Object builder(JSONObject jsonObject) throws JSONException {
        android.util.Log.d("json", jsonObject.toString());
        return null;
    }

}
