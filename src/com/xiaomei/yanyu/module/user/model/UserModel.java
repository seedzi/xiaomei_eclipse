package com.xiaomei.yanyu.module.user.model;

import java.util.List;

import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order2;
import com.xiaomei.yanyu.bean.User;
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
    
	
}
