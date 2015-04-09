package com.xiaomei.module.user.center.control;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.User;
import com.xiaomei.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserCenterControl extends BaseControl {

	public UserCenterControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}

	@AsynMethod
	public void getUserInfo(User user){
		try {
			XiaoMeiApplication.getInstance().getApi().getUserInfo(user.getUserInfo().getUserid(),user.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
