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
	public Message loginAsyn(String username ,String password){
		return null;
	}
	
	@AsynMethod
	public Message register(String username,String password,String verificationCode){
		try {
			XiaoMeiApplication.getInstance().getApi().userRegister(username, password, verificationCode);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@AsynMethod
	public void getVerificationCodeAsyn(String telno){
		try {
			String respondCode = XiaoMeiApplication.getInstance().getApi().	getVerificationCode(telno);
			Log.d("111", "respondCode = " + respondCode);
		} catch (Exception e) {
				sendMessage("getVerificationCodeAsynExceptionCallBack");
		} 
		sendMessage("getVerificationCodeAsynCallBack");
	}
	
}
