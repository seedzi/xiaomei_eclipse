package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.bean.Goods;

public class SingleGoodsBuilder extends AbstractJSONBuilder<Goods> {

    @Override
    protected Goods builder(JSONObject jobj) throws JSONException {
        Gson gson = new Gson();
        android.util.Log.d("aaa", "jobj = " + jobj.toString());
        BizResult bizResult = gson.fromJson(jobj.toString(), BizResult.class);
        if (bizResult.isSuccess()) {
            return gson.fromJson(bizResult.getMessage(), Goods.class);
        }
        return null;
    }

}
