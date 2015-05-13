package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.Goods;

public class GoodsBuilder extends AbstractJSONBuilder<List<Goods>> {

	@Override
	protected List<Goods> builder(JSONObject jsonObject) throws JSONException {
		List<Goods> list = new ArrayList<Goods>();
		if(DebugRelease.isDebug)
		    Log.d("json", jsonObject.toString());
		jsonObject = jsonObject.getJSONObject("msg");
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
			if(jobj.has("hosp_name"))
				goods.setHospName(jobj.getString("hosp_name"));
			if(jobj.has("sales"))
				goods.setSales(jobj.getString("sales"));
			if(jobj.has("city_name"))
				goods.setCityName(jobj.getString("city_name"));
			list.add(goods);
		}
		return list;
	}
	/*
	{
	    "msg": {
	        "data": [
	            {
	                "id": "1013",
	                "des": "甜美可人小苹果-全球顶级半永久填充剂AQUA Filling",
	                "title": "【苹果肌】甜美可人小苹果-全球顶级半永久填充剂AQUA Filling",
	                "city_name": "首尔",
	                "price_front": "34000",
	                "price_xm": "34000",
	                "sales": "0",
	                "price_market": "0",
	                "hosp_id": "7",
	                "is_front": "1",
	                "hosp_name": "德碧亶the Bidan整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1013\/goods_thumb_1013_200x184.jpg",
	                "cat_name": "苹果肌"
	            },
	            {
	                "id": "1055",
	                "des": "清新小卧蚕 - 半永久填充剂Aqua Filling",
	                "title": "【卧蚕】清新小卧蚕 - 半永久填充剂Aqua Filling",
	                "city_name": "首尔",
	                "price_front": "0",
	                "price_xm": "4500",
	                "sales": "963",
	                "price_market": "6000",
	                "hosp_id": "13",
	                "is_front": "1",
	                "hosp_name": "UP2C整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1055\/goods_thumb_1055_200x184.jpg",
	                "cat_name": "卧蚕"
	            },
	            {
	                "id": "1053",
	                "des": "美人鼻 - 半永久填充剂Aqua Filling",
	                "title": "【隆鼻】美人鼻 - 半永久填充剂Aqua Filling",
	                "city_name": "首尔",
	                "price_front": "4500",
	                "price_xm": "4500",
	                "sales": "0",
	                "price_market": "6000",
	                "hosp_id": "13",
	                "is_front": "1",
	                "hosp_name": "UP2C整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1053\/goods_thumb_1053_200x184.jpg",
	                "cat_name": "隆鼻"
	            },
	            {
	                "id": "1052",
	                "des": "饱满额头 - 半永久填充剂Aqua Filling",
	                "title": "【隆额】饱满额头 - 半永久填充剂Aqua Filling",
	                "city_name": "首尔",
	                "price_front": "14000",
	                "price_xm": "14000",
	                "sales": "0",
	                "price_market": "20000",
	                "hosp_id": "13",
	                "is_front": "1",
	                "hosp_name": "UP2C整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1052\/goods_thumb_1052_200x184.jpg",
	                "cat_name": "隆额"
	            },
	            {
	                "id": "1048",
	                "des": "减龄苹果肌–韩国Cutegel玻尿酸",
	                "title": "【苹果肌】减龄苹果肌–韩国Cutegel玻尿酸",
	                "city_name": "首尔",
	                "price_front": "4800",
	                "price_xm": "4800",
	                "sales": "0",
	                "price_market": "8000",
	                "hosp_id": "16",
	                "is_front": "1",
	                "hosp_name": "延世plus整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1048\/goods_thumb_1048_200x184.jpg",
	                "cat_name": "苹果肌"
	            },
	            {
	                "id": "1047",
	                "des": "可爱嘟嘟唇–韩国Cutegel玻尿酸",
	                "title": "【丰唇】可爱嘟嘟唇–韩国Cutegel玻尿酸",
	                "city_name": "首尔",
	                "price_front": "3000",
	                "price_xm": "3000",
	                "sales": "0",
	                "price_market": "5000",
	                "hosp_id": "16",
	                "is_front": "1",
	                "hosp_name": "延世plus整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1047\/goods_thumb_1047_200x184.jpg",
	                "cat_name": "丰唇"
	            },
	            {
	                "id": "1043",
	                "des": "尖翘小下巴-韩国玻尿酸CuteGel",
	                "title": "【隆颏】尖翘小下巴-韩国玻尿酸CuteGel",
	                "city_name": "首尔",
	                "price_front": "1800",
	                "price_xm": "1800",
	                "sales": "0",
	                "price_market": "3000",
	                "hosp_id": "14",
	                "is_front": "1",
	                "hosp_name": "狎鸥亭YK整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1043\/goods_thumb_1043_200x184.jpg",
	                "cat_name": "隆颏"
	            },
	            {
	                "id": "1042",
	                "des": "塑造饱满美额-韩国玻尿酸CuteGel",
	                "title": "【隆额】塑造饱满美额-韩国玻尿酸CuteGel",
	                "city_name": "首尔",
	                "price_front": "9000",
	                "price_xm": "9000",
	                "sales": "0",
	                "price_market": "15000",
	                "hosp_id": "14",
	                "is_front": "1",
	                "hosp_name": "狎鸥亭YK整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1042\/goods_thumb_1042_200x184.jpg",
	                "cat_name": "隆额"
	            },
	            {
	                "id": "1041",
	                "des": "魅惑双唇 -韩国玻尿酸CuteGel－下唇",
	                "title": "【丰唇】魅惑双唇 -韩国玻尿酸CuteGel－下唇",
	                "city_name": "首尔",
	                "price_front": "3600",
	                "price_xm": "3600",
	                "sales": "0",
	                "price_market": "6000",
	                "hosp_id": "14",
	                "is_front": "1",
	                "hosp_name": "狎鸥亭YK整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods1041\/goods_thumb_1041_200x184.jpg",
	                "cat_name": "丰唇"
	            },
	            {
	                "id": "1040",
	                "des": "魅惑双唇 -韩国玻尿酸CuteGel－上唇",
	                "title": "【丰唇】魅惑双唇 -韩国玻尿酸CuteGel－上唇",
	                "city_name": "首尔",
	                "price_front": "3000",
	                "price_xm": "1800",
	                "sales": "0",
	                "price_market": "3000",
	                "hosp_id": "14",
	                "is_front": "1",
	                "hosp_name": "狎鸥亭YK整形外科",
	                "file_url": "http:\/\/bcs.duapp.com\/d
*/
}
