package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.bean.UserFav;

public class UserFavBuilder extends AbstractJSONBuilder<UserFav> {

	@Override
	protected UserFav builder(JSONObject jsonObject) throws JSONException {
		android.util.Log.d("json", jsonObject.toString());
		return null;
	}

}
