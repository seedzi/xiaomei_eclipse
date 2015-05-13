package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;

public class ActionShareCommentBuilder extends AbstractJSONBuilder {

    @Override
    protected Object builder(JSONObject jsonObject) throws JSONException {
        if(DebugRelease.isDebug)
            android.util.Log.d("json", jsonObject.toString());
        return null;
    }

}
