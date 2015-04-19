package com.xiaomei.module.user.center.control;

import java.util.List;

import org.apache.cordova.api.LOG;

import android.text.TextUtils;

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.bean.NetResult;
import com.xiaomei.bean.Order;
import com.xiaomei.bean.User;
import com.xiaomei.bean.User.UserInfo;
import com.xiaomei.module.user.model.UserModel;
import com.xiaomei.util.FileUtils;
import com.xiaomei.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserCenterControl extends BaseControl {
	
	private UserModel mModel;

	public UserCenterControl(MessageProxy mMethodCallBack) {
		super(mMethodCallBack);
		mModel = new UserModel();
	}

	@AsynMethod
	public void getUserInfo(User user){
		try {
			XiaoMeiApplication.getInstance().getApi().getUserInfo(user.getUserInfo().getUserid(),user.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@AsynMethod
	public void addUserOrderAsyn(User user,String goodsId, String passport){
		UserInfo userInfo = user.getUserInfo();
		String userid = userInfo.getUserid();
		String username = userInfo.getUsername();
		String mobile = userInfo.getMobile();
		String token = user.getToken();
		try {
			mModel.setmOrder(XiaoMeiApplication.getInstance().getApi().addUserOrder(userid, goodsId, username, mobile, passport, token));
			sendMessage("addUserOrderAsynCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("addUserOrderAsynExceptionCallBack");
		} 
	}
	

	@AsynMethod
	public void loginOutAsyn(){
		try {
			NetResult netResult = XiaoMeiApplication.getInstance().getApi().loginOut(UserUtil.getUser().getToken());
			if(netResult.getCode().trim().equals("0")){
				UserUtil.clearUser();
				sendMessage("loginOutAsynCallBack");
			}else{
				sendMessage("loginOutAsynExceptionCallBack");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("loginOutAsynExceptionCallBack");
		} 				
	}
	
	@AsynMethod
	public void getUserOrdersAsyn(){
		try {
			List<Order> orderList = XiaoMeiApplication.getInstance().getApi().getUserOrderList(UserUtil.getUser().getUserInfo().getUserid(), UserUtil.getUser().getToken());
			LOG.d("111", "size = " + orderList.size());
			mModel.setOrderList(orderList);
			sendMessage("getUserOrdersAsynCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getUserOrdersAsynExceptionCallBack");
		}
	}
	
	public UserModel getModel(){
		return mModel;
	}
	
	@AsynMethod
	public void uploadIcon(String filePath){
	    String uploadFileUrl = null;
		try {
		    uploadFileUrl = XiaoMeiApplication.getInstance().getApi().uploadFile(UserUtil.getUser().getToken(),filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mModel.setUploadFileUrl(uploadFileUrl);
		if(!TextUtils.isEmpty(uploadFileUrl))
			sendMessage("uploadIconCallBack");
		else
			sendMessage("uploadIconExceptionCallBack");
	}
	
	@AsynMethod
	public void uploadUserInfo(String username,String mobile,String local,String shenfenzheng,String iconUrl){
	    try {
            XiaoMeiApplication.getInstance().getApi().updateUserInfo(username, mobile, local, shenfenzheng, iconUrl, UserUtil.getUser().getToken());
            //更新本地的用户信息
            User user = UserUtil.getUser();
            User.UserInfo userInfo = user.getUserInfo();
            userInfo.setUsername(username);
            userInfo.setAvatar(iconUrl);
            User.save(user);
            sendMessage("uploadUserInfoCallBack");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("uploadUserInfoExceptionCallBack");
        } 
	}
	
}
