package com.xiaomei.leveltwo.model;

import java.util.List;

import com.xiaomei.bean.BeautifulRingDetail;
import com.xiaomei.bean.Goods;

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
}
