package com.xiaomei.yanyu.leveltwo.model;

import java.util.List;

import com.xiaomei.yanyu.bean.BeautifulRingDetail;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.GoodsOption;

public class LevelTwoModel {
	
	
	private List<Goods> mGoodsList;
	
	private List<GoodsOption> mGoodsOptions;

	private BeautifulRingDetail mBeautifulRingDetailData;
	
	private boolean mIsFav;
	
	public void setGoodsListList(List<Goods> list){
		mGoodsList = list;
	}
	
	public List<Goods> getGoodsList(){
		return mGoodsList;
	}
	
   public List<GoodsOption> getGoodsOptions() {
        return mGoodsOptions;
    }

    public void setGoodsOptions(List<GoodsOption> options) {
        this.mGoodsOptions = options;
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

	public boolean ismIsFav() {
		return mIsFav;
	}

	public void setmIsFav(boolean mIsFav) {
		this.mIsFav = mIsFav;
	}
	
}
