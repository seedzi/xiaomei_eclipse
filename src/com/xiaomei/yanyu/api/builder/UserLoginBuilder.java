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
{
    "msg": {
        "token": "5a989c54b7f03ea0bbefd17b497debf6f6b80c65952fec79bde99a200e282ea8",
        "userinfo": {
            "user_type_desc": "升级钻石会员还需18600",
            "modifydate": "1429330858",
            "sex": "0",
            "user_type": 3,
            "userid": "19062300",
            "createdate": "1429330858",
            "avatar": "http://bcs.duapp.com/drxiaomei//images/avatar/20150419/20150419090018_81662.jpg",
            "cost": "11400",
            "discount": 0.97,
            "username": "张三",
            "order": "[19062394,19062405,19062408,19062409,19062410,19062411,19062412,19062414]",
            "age": "0",
            "points": "11400",
            "mobile": "15010768102"
        }
    },
    "code": 0
}
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
			if(js.has("user_type_desc"))
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
   	         if(js.has("cost"))
	                userInfo.setCost(js.getString("cost"));
   	         if(js.has("address"))
                    userInfo.setAddress(js.getString("address"));
             if(js.has("idcard"))
                    userInfo.setIdcard(js.getString("idcard"));
			user.setUserInfo(userInfo);
			android.util.Log.d("user", "user = " + user);
		}
		return user;
	}
//	{"msg":{"token":"75ced4559baf29cc7ba5c80add96c39afa3bc39357685881bc1d12242e315ad1",
//	"userinfo":{"modifydate":"1428493921","sex":"0","username":"未定义","user_type":"1","age":"0","createdate":"1428493921","userid":"19062270","mobile":"13716417246"}},"code":0}


}
