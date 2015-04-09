package com.xiaomei.bean;

import android.text.TextUtils;

import com.xiaomei.SharePreferenceKey;
import com.xiaomei.SharePreferenceWrap;

/*
{"token":"75ced4559baf29cc7ba5c80add96c39af2f15cd1a348227bf0652508225e8e34",
	"userinfo":
	{"modifydate":"1428493921","sex":"0","username":"未定义","user_type":"1","age":"0","createdate":"1428493921","userid":"19062270","mobile":"13716417246"}}
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
		
		private  String modifydate;
		
		private String sex;
		
		private String username;
		
		private String userType;
		
		private String age;
		
		private String createdate;
		
		private String userid;
		
		private String mobile;

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

		@Override
		public String toString() {
			return "UserInfo [modifydate=" + modifydate + ", sex=" + sex
					+ ", username=" + username + ", userType=" + userType
					+ ", age=" + age + ", createdate=" + createdate
					+ ", userid=" + userid + ", mobile=" + mobile + "]";
		}
		
		
	}


	public static void save(User user){
		SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap(SharePreferenceKey.USER);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_NAME, user.getUserInfo().username);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_MODIFYDATE, user.getUserInfo().modifydate);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_SEX, user.getUserInfo().sex);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_TYPE, user.getUserInfo().userType);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_AGE, user.getUserInfo().age);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_CREATEDATE, user.getUserInfo().createdate);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_USERID, user.getUserInfo().userid);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_MOBILE, user.getUserInfo().mobile);
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
