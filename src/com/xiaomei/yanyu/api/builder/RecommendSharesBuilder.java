
package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.RecommendShares;

import android.util.Log;

public class RecommendSharesBuilder extends AbstractJSONBuilder<List<RecommendShares>> {

    @Override
    protected List<RecommendShares> builder(JSONObject jsonObject) throws JSONException {
        if (DebugRelease.isDebug)
            Log.d("json", jsonObject.toString());
        if (jsonObject.has("msg"))
            jsonObject = jsonObject.getJSONObject("msg");
        JSONArray jsonArray = jsonObject.getJSONArray("shares");
        Gson gson = new Gson();
        RecommendShares[] shares = gson.fromJson(jsonArray.toString(), RecommendShares[].class);
        return new ArrayList<RecommendShares>(Arrays.asList(shares));
    }
}
