package com.xiaomei.yanyu.levelone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.ViewGroup;

import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.levelone.home.adapter.BaseView;
import com.xiaomei.yanyu.levelone.home.adapter.ConsultationView;
import com.xiaomei.yanyu.levelone.home.adapter.GoodsTopicView;
import com.xiaomei.yanyu.levelone.home.adapter.HomeShareView;
import com.xiaomei.yanyu.levelone.home.adapter.HotGoodsView;
import com.xiaomei.yanyu.levelone.home.adapter.MerchantTopicVView;
import com.xiaomei.yanyu.levelone.home.adapter.RecommendAreaView;
import com.xiaomei.yanyu.levelone.home.adapter.TopicView;



public class HomeListManager {

    private List<BaseView> mViews = new ArrayList<BaseView>();
    
    public void setupView(ViewGroup rootView,Activity ac){
        mViews.add(new TopicView());
        mViews.add(new RecommendAreaView());
        mViews.add(new HotGoodsView());
        mViews.add(new ConsultationView());
        mViews.add(new MerchantTopicVView());
        mViews.add(new GoodsTopicView());
        mViews.add(new HomeShareView());
        
        for(BaseView baseView :mViews){
            baseView.init(ac);
            rootView.addView(baseView.setupView());
        }
    }
    
    public void setData(List<HomeItem> list){
        int i = 0;
        for(BaseView baseView :mViews){
            baseView.setData(list.get(i));
            i ++;
        }
        notifyRefresh();
    }
    
    private void notifyRefresh(){
        for(BaseView baseView :mViews){
            baseView.notifyDataSetChanged();
        }
    }
}
