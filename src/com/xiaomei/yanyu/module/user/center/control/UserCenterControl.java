package com.xiaomei.yanyu.module.user.center.control;

import java.util.List;

import org.apache.cordova.api.LOG;

import android.app.Activity;
import android.text.TextUtils;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.Payment.WeiXinPayManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.UserMessage;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.bean.WechatBean;
import com.xiaomei.yanyu.module.user.model.UserModel;
import com.xiaomei.yanyu.util.UserUtil;
import com.yuekuapp.BaseControl;
import com.yuekuapp.annotations.AsynMethod;
import com.yuekuapp.proxy.MessageProxy;

public class UserCenterControl extends BaseControl {
	
	private final String PER_PAGE = "10";
	
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
			if(DebugRelease.isDebug)
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
        if(DebugRelease.isDebug)
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
            if(DebugRelease.isDebug)
                android.util.Log.d("111", "e = " + e.getMessage());
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
			if(DebugRelease.isDebug)
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
	    android.util.Log.d("user", "username = " + username + ",mobile = " +mobile + ", local = " + local + ",shenfenzheng = " + shenfenzheng );
	    try {
            XiaoMeiApplication.getInstance().getApi().updateUserInfo(username, mobile, local, shenfenzheng, iconUrl, UserUtil.getUser().getToken());
            //更新本地的用户信息
            User user = UserUtil.getUser();
            User.UserInfo userInfo = user.getUserInfo();
            if(!TextUtils.isEmpty(mobile)) //电话
                userInfo.setMobile(mobile);
            if(!TextUtils.isEmpty(username))//姓名
            	userInfo.setUsername(username);
            if(!TextUtils.isEmpty(iconUrl))//头像
            	userInfo.setAvatar(iconUrl);
            if(!TextUtils.isEmpty(shenfenzheng)) //身份
            	userInfo.setIdcard(shenfenzheng);
            if(!TextUtils.isEmpty(local))//地址
            	userInfo.setAddress(local);
            if(DebugRelease.isDebug)
                android.util.Log.d("user", "user = " + user);
            User.save(user);
            sendMessage("uploadUserInfoCallBack");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage("uploadUserInfoExceptionCallBack");
        } 
	}
	
	public void payWechat(Activity ac){
	    if(DebugRelease.isDebug)
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
	
	@AsynMethod
	public void getUserMsg() {
		try {
			mModel.setCurrentPage(1);
			List<UserMessage> data = XiaoMeiApplication.getInstance().getApi().showUserMsg(String.valueOf(mModel.getCurrentPage()), PER_PAGE);
			mModel.setUserMessage(data);
			android.util.Log.d("111", "data = " + data.size());
			sendMessage("getUserMsgCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			android.util.Log.d("111", " e = " +  e);
			sendMessage("getUserMsgExceptionCallBack");
		}
	}
	
	@AsynMethod
	public void getUserMsgMore() {
		try {
			mModel.inCreaseCurrentPage();
			List<UserMessage> data = XiaoMeiApplication.getInstance().getApi().showUserMsg(String.valueOf(mModel.getCurrentPage()), PER_PAGE);
			if(data==null || data.size()==0){
				mModel.reduceCurrentPage();
			}
			mModel.setUserMessage(data);
			sendMessage("getUserMsgMoreCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			mModel.reduceCurrentPage();
			sendMessage("getUserMsgMoreExceptionCallBack");
		}
	}
	
	@AsynMethod
	public void getUserFav(){
		try {
			mModel.setCurrentPage4Goods(1);
		    List<Goods> data = XiaoMeiApplication.getInstance().getApi().showUserFav(String.valueOf(mModel.getCurrentPage4Goods()), PER_PAGE);
		    mModel.setGoodsList(data);
			sendMessage("getUserFavCallBack");
		} catch (Exception e) {
			e.printStackTrace();
			sendMessage("getUserFavExceptionCallBack");
		}
	}
	
	   @AsynMethod
	    public void getUserFavMore() {
	        try {
	            mModel.inCreaseCurrentPage4Goods();
	            List<Goods> data = XiaoMeiApplication.getInstance().getApi().showUserFav(String.valueOf(mModel.getCurrentPage4Goods()), PER_PAGE);
	            if(data==null || data.size()==0){
	                mModel.reduceCurrentPage4Goods();
	            }
	            mModel.setGoodsList(data);
	            sendMessage("getUserFavMoreCallBack");
	        } catch (Exception e) {
	            e.printStackTrace();
	            mModel.reduceCurrentPage4Goods();
	            sendMessage("getUserFavMoreExceptionCallBack");
	        }
	    }
	   @AsynMethod
	  public void deleteUserFav(String goodsIds){
		  try {
			XiaoMeiApplication.getInstance().getApi().actionFav("del", goodsIds, UserUtil.getUser().getToken());
			sendMessage("deleteUserFavCallBack");
		} catch (Exception e) {
			sendMessage("deleteUserFavExceptionCallBack");
			e.printStackTrace();
		}
	  }
       @AsynMethod	   
	   public void getGoodsFromNetAsyn(String goodsId){
           try {
                mModel.setGoods(XiaoMeiApplication.getInstance().getApi().getGoodsFromNet(goodsId));
                android.util.Log.d("111", "goods = " + mModel.getGoods());
                sendMessage("getGoodsFromNetAsynCallback");
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage("getGoodsFromNetAsynExceptionCallback");
            } 
       }
}
