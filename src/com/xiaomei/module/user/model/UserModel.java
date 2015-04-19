package com.xiaomei.module.user.model;

import java.util.List;

import com.xiaomei.bean.Order;
import com.xiaomei.bean.Order2;
import com.xiaomei.bean.User;

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
	
	private Order2 mOrder;

	public User getmUser() {
		return mUser;
	}

	public void setmUser(User mUser) {
		this.mUser = mUser;
	}

	public Order2 getmOrder() {
		return mOrder;
	}

	public void setmOrder(Order2 mOrder) {
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
	
	
}
