package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.bean.UserInfo;

public class UserInfoBuilder extends AbstractJSONBuilder<UserInfo> {

	@Override
	protected UserInfo builder(JSONObject jsonObject) throws JSONException {
		Log.d("user", jsonObject.toString());
		return null;
	}

}
