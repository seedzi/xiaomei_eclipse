package com.xiaomei.yanyu.levelone.model;

import java.util.List;

import com.xiaomei.yanyu.bean.Merchant;

public class MechanismModel {

	private List<Merchant> mData;
	
	public void setData(List<Merchant> data){
		mData = data;
	}
	
	public List<Merchant> getData(){
		return mData;
	}
	
	private int mPage;
	
	public int getPage(){
		return mPage;
	}

	public void setPage(int page){
		mPage = page;
	}
	
	public void increaePage(){
		mPage ++ ;
	}
	
	public void reducePage(){
		mPage -- ;
	}
}
