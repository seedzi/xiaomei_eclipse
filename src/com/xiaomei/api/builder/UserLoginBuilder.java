package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.bean.LoginResult;
import com.xiaomei.bean.NetResult;
import com.xiaomei.bean.User;

public class UserLoginBuilder extends AbstractJSONBuilder<User> {

	@Override
	protected User builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		if(jsonObject.has("msg"))
			jsonObject = jsonObject.getJSONObject("msg");
		else 
			return null;
		User user = new User();
		if(jsonObject.has("token"))
			user.setToken(jsonObject.getString("token"));
		if(jsonObject.has("userinfo")){
			JSONObject js = jsonObject.getJSONObject("userinfo");
			User.UserInfo userInfo = new User.UserInfo();
			if(js.has("modifydate"))
				userInfo.setModifydate(js.getString("modifydate"));
			if(js.has("sex"))
				userInfo.setSex(js.getString("sex"));
			if(js.has("username"))
				userInfo.setUsername(js.getString("username"));
			if(js.has("user_type"))
				userInfo.setUserType(js.getString("user_type"));
			if(js.has("age"))
				userInfo.setAge(js.getString("age"));
			if(js.has("createdate"))
				userInfo.setCreatedate(js.getString("createdate"));
			if(js.has("userid"))
				userInfo.setUserid(js.getString("userid"));
			if(js.has("mobile"))
				userInfo.setMobile(js.getString("mobile"));
			user.setUserInfo(userInfo);
		}
		return user;
	}
//	{"msg":{"token":"75ced4559baf29cc7ba5c80add96c39afa3bc39357685881bc1d12242e315ad1",
//	"userinfo":{"modifydate":"1428493921","sex":"0","username":"未定义","user_type":"1","age":"0","createdate":"1428493921","userid":"19062270","mobile":"13716417246"}},"code":0}


}
