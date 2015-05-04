package com.xiaomei.yanyu.leveltwo.model;

import java.util.List;

import com.xiaomei.yanyu.bean.BeautifulRingDetail;
import com.xiaomei.yanyu.bean.Goods;

public class LevelTwoModel {
	
	
	private List<Goods> mGoodsList;

	private BeautifulRingDetail mBeautifulRingDetailData;
	
	public void setGoodsListList(List<Goods> list){
		mGoodsList = list;
	}
	
	public List<Goods> getGoodsList(){
		return mGoodsList;
	}
	
	public void setBeautifulRingDetail(BeautifulRingDetail data){
		mBeautifulRingDetailData = data;
	}
	
	public BeautifulRingDetail getBeautifulRingDetail(){
		return mBeautifulRingDetailData;
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