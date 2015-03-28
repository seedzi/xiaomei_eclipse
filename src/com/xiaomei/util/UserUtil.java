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

}
