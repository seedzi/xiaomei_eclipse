package com.xiaomei.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.bean.Goods;

public class GoodsBuilder extends AbstractJSONBuilder<List<Goods>> {

	@Override
	protected List<Goods> builder(JSONObject jsonObject) throws JSONException {
		List<Goods> list = new ArrayList<Goods>();
		Log.d("json", jsonObject.toString());
		JSONArray jArray = jsonObject.getJSONArray("data");
		for(int i = 0 ;i<jArray.length(); i++){
			Goods goods = new Goods();
			JSONObject jobj = jArray.getJSONObject(i);
			if(jobj.has("id"))
				goods.setId(jobj.getString("id"));
			if(jobj.has("hosp_id"))
				goods.setHospId(jobj.getString("hosp_id"));
			if(jobj.has("title"))
				goods.setTitle(jobj.getString("title"));
			if(jobj.has("des"))
				goods.setDes(jobj.getString("des"));
			if(jobj.has("price_market"))
				goods.setPriceMarket(jobj.getString("price_market"));
			if(jobj.has("price_xm"))
				goods.setPriceXm(jobj.getString("price_xm"));
			if(jobj.has("price_front"))
				goods.setPriceFront(jobj.getString("price_front"));
			if(jobj.has("is_front"))
				goods.setIsFront(jobj.getString("is_front"));
			if(jobj.has("file_url"))
				goods.setFileUrl(jobj.getString("file_url"));
			list.add(goods);
		}
		return list;
	}
	
}
