package com.xiaomei.module.user.control;

import android.os.Message;

import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserControl extends BaseControl{

	public UserControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}
	
	@AsynMethod
	public void getUserOrdersAsyn(){
		//TODO
	}

	@AsynMethod
	public Message loginAsyn(String username ,String password){
		return null;
	}
	
	@AsynMethod
	public Message register(String username,String password){
		return null;
	}
	
}
