package com.xiaomei.levelone.model;

import java.util.List;

import com.xiaomei.bean.Hospital;

public class MechanismModel {

	private List<Hospital> mData;
	
	public void setData(List<Hospital> data){
		mData = data;
	}
	
	public List<Hospital> getData(){
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
