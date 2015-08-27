/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.RecommendSharesDetail;

/**
 * Created by sunbreak on 8/26/15.
 */
public class RecommendSharesDetailBuilder extends AbstractJSONBuilder<RecommendSharesDetail> {

    @Override
    public RecommendSharesDetail builder(JSONObject jsonObject) throws JSONException {
        if(DebugRelease.isDebug)
            android.util.Log.d("json", jsonObject.toString());
        jsonObject = jsonObject.getJSONObject("msg");
        return new Gson().fromJson(jsonObject.toString(), RecommendSharesDetail.class);
    }

}
