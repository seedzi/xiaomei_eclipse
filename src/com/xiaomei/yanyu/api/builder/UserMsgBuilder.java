package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.UserMessage;

public class UserMsgBuilder extends AbstractJSONBuilder<List<UserMessage>> {

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
