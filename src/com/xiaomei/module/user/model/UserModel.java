package com.xiaomei.module.user.model;

import java.util.List;

import com.xiaomei.bean.Order;
import com.xiaomei.bean.User;

public class UserModel {
	
	private User mUser;

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

	public Order getmOrder() {
		return mOrder;
	}

	public void setmOrder(Order mOrder) {
		this.mOrder = mOrder;
	}
	
	private List<Order> mOrderList;
	
	public void setOrderList(List<Order> orderList){
		mOrderList = orderList;
	}
	
	public List<Order> getOrderList(){
		return mOrderList;
	}
	
}
