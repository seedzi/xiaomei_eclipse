package com.xiaomei.yanyu.levelone.model;

import java.util.List;

import com.xiaomei.yanyu.bean.BeautifulRing;

public class BeautifulRingModel {
	
	private List<BeautifulRing> mData;
	
	public List<BeautifulRing> getData(){
		return mData;
	}
	
	public void setData(List<BeautifulRing> data){
		mData = data;
	}
	
	private int mPage;
	
	public int getPage(){
		return mPage;
	}
	
	public void setPage(int page){
		mPage  = page;
	}

	public void increaePage(){
		mPage ++;
	}
	
	public void reducePage(){
		mPage --;
	}
}
