package com.xiaomei.yanyu.bean;

import android.text.TextUtils;

import com.xiaomei.yanyu.SharePreferenceKey;
import com.xiaomei.yanyu.SharePreferenceWrap;

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
public class User {
	
	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}



	/**
	 *  用戶中心的信息
	 */
	public static class UserInfo{
		
		private String userTypeDesc;
		
		private  String modifydate;
		
		private String sex;
		
		private String username;
		
		private String userType;
		
		private String age;
		
		private String createdate;
		
		private String userid;
		
		private String mobile;
		
		private String avatar;

		public String getModifydate() {
			return modifydate;
		}

		public void setModifydate(String modifydate) {
			this.modifydate = modifydate;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserType() {
			return userType;
		}

		public void setUserType(String userType) {
			this.userType = userType;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public String getCreatedate() {
			return createdate;
		}

		public void setCreatedate(String createdate) {
			this.createdate = createdate;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserTypeDesc() {
			return userTypeDesc;
		}

		public void setUserTypeDesc(String userTypeDesc) {
			this.userTypeDesc = userTypeDesc;
		}

		@Override
		public String toString() {
			return "UserInfo [userTypeDesc=" + userTypeDesc + ", modifydate="
					+ modifydate + ", sex=" + sex + ", username=" + username
					+ ", userType=" + userType + ", age=" + age
					+ ", createdate=" + createdate + ", userid=" + userid
					+ ", mobile=" + mobile + ", avatar=" + avatar + "]";
		}
	}


	public static void save(User user){
		SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap(SharePreferenceKey.USER);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_TYPE_DESC, user.getUserInfo().userTypeDesc);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_NAME, user.getUserInfo().username);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_MODIFYDATE, user.getUserInfo().modifydate);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_SEX, user.getUserInfo().sex);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_TYPE, user.getUserInfo().userType);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_AGE, user.getUserInfo().age);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_CREATEDATE, user.getUserInfo().createdate);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_USERID, user.getUserInfo().userid);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_MOBILE, user.getUserInfo().mobile);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_AVATAR, user.getUserInfo().avatar);
	}
	
	public static User getFromShareP(){
		SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap(SharePreferenceKey.USER);
		if(!sharePreferenceWrap.getBoolean(SharePreferenceKey.USER_VALID, false)){
			return null;
		}
		User user = new User();
		user.setToken(sharePreferenceWrap.getString(SharePreferenceKey.TOKEN, ""));
		User.UserInfo userInfo = new UserInfo();
		userInfo.setAge(sharePreferenceWrap.getString(SharePreferenceKey.USER_AGE, ""));
		userInfo.setCreatedate(sharePreferenceWrap.getString(SharePreferenceKey.USER_CREATEDATE, ""));
		userInfo.setMobile(sharePreferenceWrap.getString(SharePreferenceKey.USER_MOBILE, ""));
		userInfo.setModifydate(sharePreferenceWrap.getString(SharePreferenceKey.USER_MODIFYDATE, ""));
		userInfo.setSex(sharePreferenceWrap.getString(SharePreferenceKey.USER_SEX, ""));
		userInfo.setUserid(sharePreferenceWrap.getString(SharePreferenceKey.USER_USERID, ""));
		userInfo.setUsername(sharePreferenceWrap.getString(SharePreferenceKey.USER_NAME, ""));
		userInfo.setUserType(sharePreferenceWrap.getString(SharePreferenceKey.USER_TYPE, ""));
		userInfo.setAvatar(sharePreferenceWrap.getString(SharePreferenceKey.USER_AVATAR, ""));
		userInfo.setUserTypeDesc(sharePreferenceWrap.getString(SharePreferenceKey.USER_TYPE_DESC, ""));
		user.setUserInfo(userInfo);
		return user;
	}

	@Override
	public String toString() {
		return "User [token=" + token + ", userInfo=" + userInfo + "]";
	}
	
//	public static class UserInfo{
//		
//		private String username;
//		
//		private String grade;
//		
//		private String iconUrl;
//
//		public String getUsername() {
//			return username;
//		}
//
//		public void setUsername(String username) {
//			this.username = username;
//		}
//
//		public String getGrade() {
//			return grade;
//		}
//
//		public void setGrade(String grade) {
//			this.grade = grade;
//		}
//
//		public String getIconUrl() {
//			return iconUrl;
//		}
//
//		public void setIconUrl(String iconUrl) {
//			this.iconUrl = iconUrl;
//		}
//
//	}
	

	
}
