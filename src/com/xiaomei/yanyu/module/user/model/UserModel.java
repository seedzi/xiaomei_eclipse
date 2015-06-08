package com.xiaomei.yanyu.module.user.model;

import java.util.List;

import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order2;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.UserMessage;
import com.xiaomei.yanyu.bean.WechatBean;

public class UserModel {
	
	private User mUser;
	/**更新用户头像后新生成的url*/
	private String uploadFileUrl;
	
	public void setUser(User user){
		mUser = user;
	}
	
	public User getUser(){
		return mUser;
	}
	
	private Order mOrder;

	public User getmUser() {
		return mUser;
	}

	public void setmUser(User mUser) {
		this.mUser = mUser;
	}

	public Order getOrder() {
		return mOrder;
	}

	public void setOrder(Order mOrder) {
		this.mOrder = mOrder;
	}
	
	private List<Order> mOrderList;
	
	public void setOrderList(List<Order> orderList){
		mOrderList = orderList;
	}
	
	public List<Order> getOrderList(){
		return mOrderList;
	}

    public String getUploadFileUrl() {
        return uploadFileUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        this.uploadFileUrl = uploadFileUrl;
    }

    public List<Order> getmOrderList() {
        return mOrderList;
    }

    public void setmOrderList(List<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }
	
    private WechatBean wechatBean;

	public WechatBean getWechatBean() {
		return wechatBean;
	}

	public void setWechatBean(WechatBean wechatBean) {
		this.wechatBean = wechatBean;
	}
    
	// ===============================   用户消息 =====================================
	private List<UserMessage> mUserMessgae;
	
	public List<UserMessage> getUserMessage(){
		return mUserMessgae;
	}
	
	public void setUserMessage(List<UserMessage> data){
		mUserMessgae = data;
	}
	
	private int currentPage;
	
	public void setCurrentPage(int page){
		currentPage = page;
	}
	
	public void inCreaseCurrentPage(){
		currentPage ++;
	}
	
	public void reduceCurrentPage(){
		currentPage --;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	
	// ===============================   用户收藏 =====================================
	
	private int mPage4Goods;
	private List<Goods> mGoodsList;
	
	public void setGoodsList(List<Goods>  list){
	    mGoodsList = list;
	}
	
	public List<Goods> getGoodsList(){
	    return mGoodsList;
	}
	
   public void inCreaseCurrentPage4Goods(){
       mPage4Goods ++;
    }
    
    public void reduceCurrentPage4Goods(){
        mPage4Goods --;
    }
    
    public void setCurrentPage4Goods(int page){
        mPage4Goods = page;
    }
    
    public int getCurrentPage4Goods(){
        return mPage4Goods;
    }
    
	// ===============================   注册信息  =====================================
    private String mRegisterFailureMsg;
    
    public String getRegisterFailureMsg(){
    	return mRegisterFailureMsg;
    }
    
    public void setRegisterFailureMsg(String msg){
    	mRegisterFailureMsg = msg;
    }
}
