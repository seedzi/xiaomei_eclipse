package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.bean.LoginResult;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.User;

public class UserLoginBuilder extends AbstractJSONBuilder<User> {
	/*
	{
	    "msg": {
	        "token": "8e34f2168a9928e46f7fe5f5c5562e6d1b6ff39fbcf7de8c86be4c90840a9dad",
	        "userinfo": {
	            "user_type_desc": "升级青铜用户还需1000",
	            "modifydate": "1430531569",
	            "sex": "",
	            "user_type": 1,
	            "userid": "19062525",
	            "createdate": "1430531569",
	            "idcard": "",
	            "avatar": "http://q.qlogo.cn/qqapp/1104506536/CEE0B09E3B6BD8D29AF057FAAEBDE9A1/100",
	            "cost": "0",
	            "discount": 1,
	            "username": "三文鱼",
	            "order": "[]",
	            "qq_uid": "CEE0B09E3B6BD8D29AF057FAAEBDE9A1",
	            "address": "",
	            "points": "0",
	            "mobile": ""
	        }
	    },
	    "code": 0
	}
		*/
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
			if(jsonObject.has("user_type_desc"))
				userInfo.setUserTypeDesc(js.getString("user_type_desc"));
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
	         if(js.has("avatar"))
	                userInfo.setAvatar(js.getString("avatar"));
			user.setUserInfo(userInfo);
		}
		return user;
	}
//	{"msg":{"token":"75ced4559baf29cc7ba5c80add96c39afa3bc39357685881bc1d12242e315ad1",
//	"userinfo":{"modifydate":"1428493921","sex":"0","username":"未定义","user_type":"1","age":"0","createdate":"1428493921","userid":"19062270","mobile":"13716417246"}},"code":0}


}
