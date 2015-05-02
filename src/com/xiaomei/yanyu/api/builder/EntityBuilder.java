package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.bean.Section.Entity;

public class EntityBuilder extends AbstractJSONBuilder<Entity> {

	@Override
	protected Entity builder(JSONObject jsonObject) throws JSONException {
		Entity entity = new Entity();
		if(jsonObject.has("image_6"))
			entity.setImg(jsonObject.getString("image_6"));
		if(jsonObject.has("title"))
			entity.setTitle(jsonObject.getString("title"));
		if(jsonObject.has("url"))
			entity.setUrl(jsonObject.getString("url"));
		if(jsonObject.has("num_favorite"))
			entity.setNumfavorite(jsonObject.getString("num_favorite"));
		if(jsonObject.has("username"))
			entity.setUsername(jsonObject.getString("username"));
		if(jsonObject.has("num_comment"))
			entity.setNumcomment(jsonObject.getString("num_comment"));
		if(jsonObject.has("date"))
			entity.setDate(jsonObject.getString("date"));
		if(jsonObject.has("avator"))
			entity.setAvator(jsonObject.getString("avator"));
		return entity;
	}
}
