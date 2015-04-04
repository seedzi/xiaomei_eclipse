package com.xiaomei.module.user.control;


import android.util.Log;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.NetResult;
import com.xiaomei.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserControl extends BaseControl{
	
	private static boolean DEBUG = true;

	public UserControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
	}
	
	@AsynMethod
	public void getUserOrdersAsyn(){
		//TODO
	}
	
	@AsynMethod
	public void findPasswordAsyn(String username,String password,String verificationCode){
		try {
			NetResult result = XiaoMeiApplication.getInstance().getApi().findPassword(username, password, verificationCode);
			if(DEBUG)
				Log.d("111", "findPasswordAsyn()  msg = " + result.getMsg());
		} catch (Exception e) {
			sendMessage("findPasswordAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		sendMessage("findPasswordAsynCallBack");
	}


	@AsynMethod
	public void loginAsyn(String username ,String password){
		try {
			NetResult result = XiaoMeiApplication.getInstance().getApi().userLogin(username, password);
			if(DEBUG)
				Log.d("111", "loginAsyn()  msg = " + result.getMsg());
		} catch (Exception e) {
			sendMessage("loginAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		UserUtil.userloginSuccess(username, password);
		sendMessage("loginAsynCallBack");
	}
	
	@AsynMethod
	public void registeAsyn(String username,String password,String verificationCode){
		try {
			NetResult result = XiaoMeiApplication.getInstance().getApi().userRegister(username, password, verificationCode);
			if(DEBUG)
				Log.d("111", "registeAsyn()  msg = " + result.getMsg());
		} catch (Exception e) {
			sendMessage("registeAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		sendMessage("registeAsynCallBack");
	}
	
	@AsynMethod
	public void getVerificationCodeAsyn(String telno){
		try {
			String respondCode = XiaoMeiApplication.getInstance().getApi().	getVerificationCode(telno);
			if(DEBUG)
				Log.d("111", "getVerificationCodeAsyn()  respondCode= " + respondCode);
		} catch (Exception e) {
				sendMessage("getVerificationCodeAsynExceptionCallBack");
				e.printStackTrace();
				return;
		} 
		sendMessage("getVerificationCodeAsynCallBack");
	}
	
}
