
package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.Merchant;

import android.util.Log;

public class MerchantBuilder extends AbstractJSONBuilder<List<Merchant>> {

    @Override
    protected List<Merchant> builder(JSONObject jsonObject) throws JSONException {
        if (DebugRelease.isDebug)
            Log.d("json", jsonObject.toString());
        List<Merchant> list = new ArrayList<Merchant>();
        JSONArray jsonArray = null;
        if (jsonObject.has("msg"))
            jsonObject = jsonObject.getJSONObject("msg");
        if (jsonObject.has("hospitals"))
            jsonArray = jsonObject.getJSONArray("hospitals");
        else
            return null;

        Gson gson = new Gson();
        Merchant[] merchants = gson.fromJson(jsonArray.toString(), Merchant[].class);
        return new ArrayList<Merchant>(Arrays.asList(merchants));
    }
}
