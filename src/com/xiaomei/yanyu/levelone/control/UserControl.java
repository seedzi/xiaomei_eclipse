package com.xiaomei.yanyu.levelone.control;

import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserControl extends BaseControl {

	
	private User.UserInfo mUserInfo;
	
	public UserControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}

	@AsynMethod
	public void updateUserMessgaeAsyn(){
		// TODO
		sendMessage("");
	}
	
	public UserInfo getUserInfo(){
		return mUserInfo;
	}
}
