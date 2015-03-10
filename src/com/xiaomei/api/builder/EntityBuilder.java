package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.bean.Section.Entity;

public class EntityBuilder extends AbstractJSONBuilder<Entity> {

	@Override
	protected Entity builder(JSONObject jsonObject) throws JSONException {
		Entity entity = new Entity();
		if(jsonObject.has("addr"))
			entity.setAddr(jsonObject.getString("addr"));
		if(jsonObject.has("img"))
			entity.setImg(jsonObject.getString("img"));
		if(jsonObject.has("price_market"))
			entity.setPrice_market(jsonObject.getString("price_market"));
		if(jsonObject.has("price_xm"))
			entity.setPrice_xm(jsonObject.getString("price_xm"));
		if(jsonObject.has("saled"))
			entity.setSaled(jsonObject.getString("saled"));
		if(jsonObject.has("stock"))
			entity.setStock(jsonObject.getString("stock"));
		if(jsonObject.has("title"))
			entity.setTitle(jsonObject.getString("title"));
		if(jsonObject.has("url"))
			entity.setUrl(jsonObject.getString("url"));
		return entity;
	}

}
