package com.xiaomei.yanyu.module.user.center.control;

import java.util.List;

import org.apache.cordova.api.LOG;

import android.app.Activity;
import android.text.TextUtils;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.Payment.WeiXinPayManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.bean.WechatBean;
import com.xiaomei.yanyu.module.user.model.UserModel;
import com.xiaomei.yanyu.util.UserUtil;
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
			Order order = XiaoMeiApplication.getInstance().getApi().addUserOrder(userid, goodsId, username, mobile, passport, token,"add");
			android.util.Log.d("111", "order = " + order);
			mModel.setOrder(order);
			if(order == null )
				sendMessage("addUserOrderAsynExceptionCallBack");
			else
				sendMessage("addUserOrderAsynCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("addUserOrderAsynExceptionCallBack");
		} 
	}
	
    @AsynMethod
    public void updateUserOrder2ServerAsyn(String orderId,String username, String goodsId, String passport,String mobile) {
    	android.util.Log.d("111", "orderId = " + orderId);
        String token = UserUtil.getUser().getToken();
        String userid = UserUtil.getUser().getUserInfo().getUserid();
        try {
           Order order = XiaoMeiApplication
                    .getInstance()
                    .getApi()
                    .updateUserOrder(orderId,userid, goodsId, username, mobile, passport,
                            token,"update");
           if(order!=null){
        	   mModel.setOrder(order);
        	   sendMessage("updateUserOrder2ServerAsynCallBack");
           } else{
        	   sendMessage("updateUserOrder2ServerAsynExceptionCallBack");
           }
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("updateUserOrder2ServerAsynExceptionCallBack");
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
	
	@AsynMethod
	public void cancelUserOrderUrl(String orderid){
	    try {
	    	NetResult netResult = XiaoMeiApplication.getInstance().getApi().cancelUserOrderUrl(orderid);
	    	if(netResult!=null && "0".equals(netResult.getCode())){ //取消订单成功
	    		sendMessage("cancelUserOrderUrlCallBack"); 
	    	}else{
	    		sendMessage("cancelUserOrderUrlExceptionCallBack");
	    	}
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("cancelUserOrderUrlExceptionCallBack");
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
	
	public void payWechat(Activity ac){
		android.util.Log.d("111", "payWechat");
		WeiXinPayManager.getInstance().pay(mModel.getWechatBean(), ac);
	}
	
	@AsynMethod
	public void getPayWechatInfo(String orderid,String token){
	    try {
	    	WechatBean wechatBean = XiaoMeiApplication.getInstance().getApi().payWechat(orderid, token);
            sendMessage("getPayWechatInfoCallBack");
            mModel.setWechatBean(wechatBean);
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("getPayWechatInfoExceptionCallBack");
        } 
	}
	
}
