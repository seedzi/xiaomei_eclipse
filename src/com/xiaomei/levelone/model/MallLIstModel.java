package com.xiaomei.levelone.model;

import java.util.List;

import com.xiaomei.bean.Mall;

public class MallLIstModel {

	private List<Mall> mData;
	
	private Mall mHead;
	
	public void setData(List<Mall> data){
		mData = data;
	}
	
	public List<Mall> getData(){
		return mData;
	}
	
	public Mall getHead(){
		return mHead;
	}
	
	public void setHead(Mall head){
		mHead = head;
	}
}
