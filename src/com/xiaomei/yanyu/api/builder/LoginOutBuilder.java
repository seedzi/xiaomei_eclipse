package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.socialize.utils.Log;
import com.xiaomei.yanyu.DebugRelease;

public class LoginOutBuilder extends AbstractJSONBuilder {

	@Override
	protected Object builder(JSONObject jsonObject) throws JSONException {
	    if(DebugRelease.isDebug)
	        Log.d("json", jsonObject.toString());
		return null;
	}

}
