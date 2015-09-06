package com.xiaomei.yanyu.api.builder;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.bean.Goods;

public class GoodsBuilder extends AbstractJSONBuilder<List<Goods>> {

	@Override
	protected List<Goods> builder(JSONObject jsonObject) throws JSONException {
	    Gson gson = new Gson();
	    BizResult bizResult = gson.fromJson(jsonObject.toString(), BizResult.class);
	    if (bizResult.isSuccess()) {
            JsonElement data = bizResult.getMessage().getAsJsonObject().get("data");
            Goods[] array = gson.fromJson(data, Goods[].class);
            return Arrays.asList(array);
        }
	    return null;
	}
}
