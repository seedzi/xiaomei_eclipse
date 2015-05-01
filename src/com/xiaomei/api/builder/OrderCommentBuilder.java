package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.XiaoMeiApplication;

public class OrderCommentBuilder extends AbstractJSONBuilder {

	@Override
	protected Object builder(JSONObject jsonObject) throws JSONException {
		android.util.Log.d("json", "json = " + jsonObject.toString());
		
		return null;
	}

}
