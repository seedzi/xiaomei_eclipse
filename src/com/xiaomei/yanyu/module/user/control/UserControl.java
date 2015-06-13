package com.xiaomei.yanyu.module.user.control;


import android.util.Log;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.module.user.model.UserModel;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserControl extends BaseControl{
	
	private static boolean DEBUG = true;
	
	private UserModel mUserModel;

	public UserControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mUserModel = new UserModel();
	}
	
	@AsynMethod
	public void findPasswordAsyn(String username,String password,String verificationCode){
		User user = null;
		try {
			user = XiaoMeiApplication.getInstance().getApi().findPassword(username, password, verificationCode);
			mUserModel.setUser(user);
			if(DEBUG)
				Log.d("111", "loginAsyn()  user = " + user);
		} catch (Exception e) {
			sendMessage("findPasswordAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		if(UserUtil.isUserValid(user)){
			UserUtil.userloginSuccess(user);
			sendMessage("findPasswordAsynCallBack");
		}else{
			sendMessage("findPasswordAsynExceptionCallBack");
		}
	}


	@AsynMethod
	public void loginAsyn(String username ,String password){
		User user = null;
		try {
			user = XiaoMeiApplication.getInstance().getApi().userLogin(username, password);
			mUserModel.setUser(user);
			if(DEBUG)
				Log.d("111", "loginAsyn()  user = " + user);
		} catch (Exception e) {
			sendMessage("loginAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		if(UserUtil.isUserValid(user)){
			UserUtil.userloginSuccess(user);
			sendMessage("loginAsynCallBack");
			if(DEBUG)
				Log.d("111", "loginAsyn()  登录成功" );
		}else{
			sendMessage("loginAsynExceptionCallBack");
			if(DEBUG)
				Log.d("111", "loginAsyn()  登录失败" );
		}
	}
	
	@AsynMethod
	public void registeAsyn(String username,String password,String verificationCode){
		try {
			NetResult result = XiaoMeiApplication.getInstance().getApi().userRegister(username, password, verificationCode);
			if("0".equals(result.getCode()))
				sendMessage("registeAsynCallBack");
			else{
				mUserModel.setRegisterFailureMsg(result.getMsg());
				sendMessage("registeAsynExceptionCallBack");
			}
			if(DEBUG)
				Log.d("111", "registeAsyn()  msg = " + result.getMsg());
		} catch (Exception e) {
			sendMessage("registeAsynExceptionCallBack");
			e.printStackTrace();
			return;
		} 
		
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
	
	public User getUser(){
		return mUserModel.getUser();
	}
	
	
	@AsynMethod
	public void feedback(String content,String contact){
		try {
			XiaoMeiApplication.getInstance().getApi().	feedback(content,contact,UserUtil.getUser().getToken());
			sendMessage("feedbackAsynCallBack");
		} catch (Exception e) {
				sendMessage("feedbackExceptionCallBack");
				e.printStackTrace();
				return;
		} 
	}
	
	public UserModel getModel(){
		return mUserModel;
	}
	
}
