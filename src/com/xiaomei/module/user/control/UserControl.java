package com.xiaomei.module.user.control;

import android.os.Message;
import android.util.Log;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.XiaoMeiApi;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
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
	public void loginAsyn(String username ,String password){
	}
	
	@AsynMethod
	public void register(String username,String password,String verificationCode){
		try {
			XiaoMeiApplication.getInstance().getApi().userRegister(username, password, verificationCode);
		} catch (Exception e) {
			sendMessage("registerExceptionCallBack");
			e.printStackTrace();
		} 
		sendMessage("registerCallBack");
	}
	
	@AsynMethod
	public void getVerificationCodeAsyn(String telno){
		try {
			String respondCode = XiaoMeiApplication.getInstance().getApi().	getVerificationCode(telno);
		} catch (Exception e) {
				sendMessage("getVerificationCodeAsynExceptionCallBack");
				e.printStackTrace();
		} 
		sendMessage("getVerificationCodeAsynCallBack");
	}
	
}
