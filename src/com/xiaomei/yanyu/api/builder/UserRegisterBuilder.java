package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.bean.NetResult;

public class UserRegisterBuilder extends AbstractJSONBuilder<NetResult> {

	@Override
	protected NetResult builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		NetResult netResult = new NetResult();
		if(jsonObject.has("code"))
			netResult.setCode(jsonObject.getString("code"));
		if(jsonObject.has("msg"))
			netResult.setMsg(jsonObject.getString("msg"));
		return netResult;
	}

}
