package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.Mall;

public class MallListBuilder extends AbstractJSONBuilder<List<Mall>> {

	@Override
	protected List<Mall> builder(JSONObject jsonObject) throws JSONException {
	    if(DebugRelease.isDebug)
	        android.util.Log.d("json", jsonObject.toString());
		List<Mall> list = new ArrayList<Mall>();
		JSONArray jsonArray = null;
		if(jsonObject.has("msg"))
			jsonObject = jsonObject.getJSONObject("msg");
		if(jsonObject.has("data"))
			jsonArray = jsonObject.getJSONArray("data");
		if(jsonArray == null)
			return null;
		for(int i = 0;i<jsonArray.length();i++){
			Mall mall = new Mall();
			JSONObject jsObj = jsonArray.getJSONObject(i);
			if(jsObj.has("id"))
				mall.setId(jsObj.getString("id"));
			if(jsObj.has("title"))
				mall.setTitle(jsObj.getString("title"));
			if(jsObj.has("link"))
				mall.setLink(jsObj.getString("link"));
			if(jsObj.has("createdate"))
				mall.setCreatedate(jsObj.getString("createdate"));
			if(jsObj.has("is_open"))
				mall.setIsOpen(jsObj.getString("is_open"));
			if(jsObj.has("image_6"))
				mall.setImage(jsObj.getString("image_6"));
			if(jsObj.has("is_delete"))
				mall.setIsDelete(jsObj.getString("is_delete"));
			if(jsObj.has("parentid"))
				mall.setParentId(jsObj.getString("parentid"));
			if(jsObj.has("sort_order"))
				mall.setSortOrder(jsObj.getString("sort_order"));
			if(jsObj.has("type"))
				mall.setType(jsObj.getString("type"));
			if(jsObj.has("pos"))
				mall.setPos(jsObj.getString("pos"));
			if(jsObj.has("cat_mark"))
				mall.setCatMark(jsObj.getString("cat_mark"));
			if(jsObj.has("keywords"))
				mall.setKeywords(jsObj.getString("keywords"));
			if(jsObj.has("file"))
				mall.setFile(jsObj.getString("file"));
			if(jsObj.has("cat_des"))
				mall.setCatDes(jsObj.getString("cat_des"));
			if(jsObj.has("cat_type"))
				mall.setCatType(jsObj.getString("cat_type"));
			if(jsObj.has("cat_name_en"))
				mall.setCatNameEn(jsObj.getString("cat_name_en"));
			if(jsObj.has("cat_name"))
				mall.setCatName(jsObj.getString("cat_name"));
			list.add(mall);
		}
		
		return list;
	}
	/*
    "cat_mark": "肉毒素除皱",
    "id": "4",
    "keywords": "肉毒素除皱",
    "file": "http://bcs.duapp.com/drxiaomei/images/goodscat4/20150418115813_66207.png",
    "cat_des": "肉毒素除皱分类",
    "cat_type": "0",
    "sort_order": "0",
    "cat_name_en": "",
    "cat_name": "肉毒素除皱",
    "parent_id": "0"
    */
	/*
    "id": "1",
    "title": "1234",
    "link": "1015",
    "createdate": 1429352715,
    "is_open": "1",
    "image": "http://bcs.duapp.com/drxiaomei/images/ads1/20150418124845_87872.png",
    "is_delete": 0,
    "parentid": 0,
    "sort_order": "1",
    "type": "1",
    "pos": "shop_index"
    */
}
