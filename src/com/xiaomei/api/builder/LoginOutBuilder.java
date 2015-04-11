package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.socialize.utils.Log;

public class LoginOutBuilder extends AbstractJSONBuilder {

	@Override
	protected Object builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		return null;
	}

}
