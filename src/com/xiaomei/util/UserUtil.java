package com.xiaomei.util;

import com.xiaomei.SharePreferenceKey;
import com.xiaomei.SharePreferenceWrap;

public class UserUtil {
	
	/**
	 * 用户是否登录
	 * @return
	 */
	public static boolean isUserValid(){
		SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap();
		return sharePreferenceWrap.getBoolean(SharePreferenceKey.USER_VALID, false);
	}
	
	
	/**
	 * 用户是否登录
	 * @return
	 */
	public static void userloginSuccess(String userid , String password){
		SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap();
		sharePreferenceWrap.putBoolean(SharePreferenceKey.USER_VALID, true);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_ID, userid);
		sharePreferenceWrap.putString(SharePreferenceKey.USER_PASSWORD, password);
	}

}
