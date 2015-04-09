package com.xiaomei.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.api.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.bean.BeautifulRingDetail;

public class BeautifulDetailBuilder extends AbstractJSONBuilder<BeautifulRingDetail> {

	@Override
	protected BeautifulRingDetail builder(JSONObject jsonObject)
			throws JSONException {
		LOG.d("json", jsonObject.toString());
		BeautifulRingDetail beautifulRingDetail = new BeautifulRingDetail();
		if(jsonObject.has("id"))
			beautifulRingDetail.setId(jsonObject.getString("id"));
		if(jsonObject.has("goods_id"))
			beautifulRingDetail.setGoodsId(jsonObject.getString("goods_id"));
		if(jsonObject.has("hosp_id"))
			beautifulRingDetail.setHospId(jsonObject.getString("hosp_id"));
		if(jsonObject.has("userid"))
			beautifulRingDetail.setUserid(jsonObject.getString("userid"));
		if(jsonObject.has("username"))
			beautifulRingDetail.setUsername(jsonObject.getString("username"));
		if(jsonObject.has("avatar"))
			beautifulRingDetail.setAvatar(jsonObject.getString("avatar"));
		if(jsonObject.has("share_title"))
			beautifulRingDetail.setShareTitle(jsonObject.getString("share_title"));
		if(jsonObject.has("share_des"))
			beautifulRingDetail.setShareDes(jsonObject.getString("share_des"));
		if(jsonObject.has("sort_order"))
			beautifulRingDetail.setSortOrder(jsonObject.getString("sort_order"));
		if(jsonObject.has("is_open"))
			beautifulRingDetail.setIsOpen(jsonObject.getString("is_open"));
		if(jsonObject.has("share_mark"))
			beautifulRingDetail.setShareMake(jsonObject.getString("share_mark"));
		if(jsonObject.has("share_file"))
			beautifulRingDetail.setShareFile(jsonObject.getString("share_file"));
		if(jsonObject.has("num_favors"))
			beautifulRingDetail.setNumFavors(jsonObject.getString("num_favors"));
		if(jsonObject.has("num_comments"))
			beautifulRingDetail.setNumComments(jsonObject.getString("num_comments"));
		if(jsonObject.has("createdate"))
			beautifulRingDetail.setCreatedate(jsonObject.getString("createdate"));
		if(jsonObject.has("images")){
			List<BeautifulRingDetail.Item> list = new ArrayList<BeautifulRingDetail.Item>();
			JSONArray jsonArray = jsonObject.getJSONArray("images");
			for(int i=0;i<jsonArray.length();i++){
				BeautifulRingDetail.Item item = new BeautifulRingDetail.Item();
				JSONObject job = jsonArray.getJSONObject(i);
				if(job.has("id"))
					item.setId(job.getString("id"));
				if(job.has("type"))
					item.setType(job.getString("type"));
				if(job.has("typeid"))
					item.setTypeid(job.getString("typeid"));
				if(job.has("url"))
					item.setUrl(job.getString("url"));
				if(job.has("title"))
					item.setTilte(job.getString("title"));
				list.add(item);
			}
			beautifulRingDetail.setItems(list);
		}
		return beautifulRingDetail;
	}

	/*
	{
	    "id": "5",
	    "goods_id": "0",
	    "hosp_id": "0",
	    "userid": "0",
	    "username": null,
	    "avatar": null,
	    "share_title": "是对方是否1231",
	    "share_des": "阿萨德发顺风",
	    "sort_order": "0",
	    "is_open": "1",
	    "share_mark": "暗示",
	    "share_file": "http://bcs.duapp.com/drxiaomei/images/share5/20150323161155_26824.jpeg",
	    "num_favors": "0",
	    "num_comments": "0",
	    "createdate": "1427522221",
	    "images": [
	        {
	            "id": "5",
	            "type": "share",
	            "typeid": "5",
	            "url": "http://bcs.duapp.com/drxiaomei/images/share5/20150323161155_26824.jpeg",
	            "title": "asdfsfsd"
	        },
	        {
	            "id": "6",
	            "type": "share",
	            "typeid": "5",
	            "url": "http://bcs.duapp.com/drxiaomei/images/share5/20150328140000_42996.jpg",
	            "title": "找不到对象"
	        },
	        {
	            "id": "7",
	            "type": "share",
	            "typeid": "5",
	            "url": "http://bcs.duapp.com/drxiaomei/images/share5/20150328140035_29489.jpg",
	            "title": "趴咚"
	        },
	        {
	            "id": "8",
	            "type": "share",
	            "typeid": "5",
	            "url": "http://bcs.duapp.com/drxiaomei/images/share5/20150328140126_18939.jpg",
	            "title": "荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚荷花咚"
	        }
	    ]
	}
	*/
}
