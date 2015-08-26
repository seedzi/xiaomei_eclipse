package com.xiaomei.yanyu.levelone.model;

import java.util.List;

import com.xiaomei.yanyu.bean.RecommendShares;
import com.xiaomei.yanyu.bean.UserShare;

public class SharesModel {
    
    // ================================ Jingxuan============================================
	
	private List<RecommendShares> mBeautifulData;
	
	private int mBeautifulPage;
	
	public List<RecommendShares> getBeautifulData(){
		return mBeautifulData;
	}
	
	public void setBeautifulData(List<RecommendShares> data){
	    mBeautifulData = data;
	}
	
	public int getBeautifulPage(){
		return mBeautifulPage;
	}
	
	public void setBeautifulPage(int page){
	    mBeautifulPage  = page;
	}

	public void increaeBeautifulPage(){
	    mBeautifulPage ++;
	}
	
	public void reduceBeautifulPage(){
	    mBeautifulPage --;
	}
	
	// ================================ guangchang============================================
	
	private List<UserShare> mUserShareData;
	
    private int mUserSharePage;
	
	public List<UserShare> getUserShareData(){
	    return mUserShareData;
	}
	
	public void setUserShareData(List<UserShare> data){
	    mUserShareData = data;
	}
	
   public int getUserSharePage(){
        return mUserSharePage;
    }
	
   public void setUserSharePage(int page){
       mUserSharePage  = page;
    }
	
    public void increaeUserSharePage() {
        mUserSharePage++;
    }

    public void reduceUserSharePage() {
        mUserSharePage--;
    }
	    
}
