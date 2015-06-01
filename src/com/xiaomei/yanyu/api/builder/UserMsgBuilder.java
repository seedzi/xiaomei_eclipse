package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.UserMessage;

public class UserMsgBuilder extends AbstractJSONBuilder<List<UserMessage>> {
/**
 *             "cat_id": "55",
            "title": "PRP再生因子注射 – 让皮肤重获生机",
            "rate_effect": "5",
            "rate_environment": "5",
            "is_front": "1",
            "des": "PRP再生因子注射 – 让皮肤重获生机",
            "city_name": "首尔",
            "price_market": "2000",
            "goods_type": "0",
            "rate_service": "4",
            "sales": "16",
            "sort_order": "1001",
 */
	@Override
	protected List<UserMessage> builder(JSONObject jsonObject) throws JSONException {
		if(DebugRelease.isDebug)
			android.util.Log.d("json", jsonObject.toString());
		List<UserMessage> data = new ArrayList<UserMessage>();
		if(jsonObject.has("msg")){
			JSONArray jsonArray = jsonObject.getJSONArray("msg");
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject jsO = jsonArray.getJSONObject(i);
				UserMessage userMessage = new UserMessage();
				if(jsO.has("title"))
					userMessage.setTitle(jsO.getString("title"));
				if(jsO.has("content"))
					userMessage.setContent(jsO.getString("content"));
				data.add(userMessage);
			}
		}
		return data;
	}

}
