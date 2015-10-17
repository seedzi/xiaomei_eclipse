package com.xiaomei.yanyu.levelone.home.adapter;

import java.util.zip.Inflater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.ScreenUtils;

public abstract class BaseView implements View.OnClickListener{
    

    protected LayoutInflater mInflater;
    
    protected Activity mAc;
    
    protected int mScreenWidth;
    
    protected HomeItem mData;
    
    public void init(Activity ac){
        mAc = ac;
        mInflater = LayoutInflater.from(mAc);
        mScreenWidth = ScreenUtils.getScreenWidth(mAc);
    }
    
    public void setData(HomeItem data){
        mData = data;
    }
    
    public abstract View setupView();

    protected abstract void refreshUi();
    
    public void notifyDataSetChanged(){
        refreshUi();
    }
    

    @Override
    public void onClick(View v) {
        String url = (String) v.getTag();
        GoodsDetailActivity.startActivity(mAc, url,null,null);//TODO
    }

}
