package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.bean.BeautifulRing;

public class BeautifulRingBuilder extends AbstractJSONBuilder<List<BeautifulRing>> {

	@Override
	protected List<BeautifulRing> builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		List<BeautifulRing> list = new ArrayList<BeautifulRing>();
		if(jsonObject.has("msg"))
			jsonObject = jsonObject.getJSONObject("msg");
		JSONArray jsonArray = jsonObject.getJSONArray("shares");
		for( int i=0 ;i< jsonArray.length();i++){
			JSONObject jobject = jsonArray.getJSONObject(i);
			BeautifulRing beautifulRing = new BeautifulRing();
			if(jobject.has("id"))
				beautifulRing.setId(jobject.getString("id"));
			if(jobject.has("goods_id"))
				beautifulRing.setGoodsId(jobject.getString("goods_id"));
			if(jobject.has("hosp_id"))
				beautifulRing.setHospId(jobject.getString("hosp_id"));
			if(jsonObject.has("userid"))
				beautifulRing.setUserid(jobject.getString("userid"));		
			if(jobject.has("share_title"))
				beautifulRing.setShareTitle(jobject.getString("share_title"));	
			if(jobject.has("share_des"))
				beautifulRing.setShareDes(jobject.getString("share_des"));	
			if(jobject.has("sort_order"))
				beautifulRing.setSortOrder(jobject.getString("sort_order"));	
			if(jobject.has("is_open"))
				beautifulRing.setIsOpen(jobject.getString("is_open"));	
			if(jobject.has("share_mark"))
				beautifulRing.setShareMark(jobject.getString("share_mark"));	
			if(jobject.has("share_file"))
				beautifulRing.setShareFile(jobject.getString("share_file"));	
			if(jobject.has("num_favors"))
				beautifulRing.setNumFavors(jobject.getString("num_favors"));	
			if(jobject.has("num_comments"))
				beautifulRing.setNumComments(jobject.getString("num_comments"));	
			if(jobject.has("share_type"))
				beautifulRing.setShareType(jobject.getString("share_type"));	
			if(jobject.has("createdate"))
				beautifulRing.setCreatedate(jobject.getString("createdate"));
			list.add(beautifulRing);
		}
		Log.d("111", "size = " + list.size());
		return list;
	}

	/*
	"id": "5",
    "goods_id": "0",
    "hosp_id": "0",
    "userid": "0",
    "share_title": "是对方是否1231",
    "share_des": "阿萨德发顺风",
    "sort_order": "0",
    "is_open": "1",
    "share_mark": "暗示",
    "share_file": "http://bcs.duapp.com/drxiaomei/images/share5/20150323161155_26824.jpeg",
    "num_favors": "0",
    "num_comments": "0",
    "modifydate": "1427522221",
    "share_type": "1"
    */
}
