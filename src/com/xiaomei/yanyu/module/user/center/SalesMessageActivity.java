package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.GoodsAdapter;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.GoodsListActivity;
import com.xiaomei.yanyu.module.user.center.control.SalesControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

public class SalesMessageActivity extends AbstractActivity<SalesControl> implements OnScrollListener,OnRefreshListener{
    
    public static void startActivity(Activity ac){
        Intent intent = new Intent(ac,SalesMessageActivity.class);
        ac.startActivity(intent);
         ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
    }

    private TitleBar mTitleBar;
    
    private PullToRefreshListView mPullToRefreshListView;
    
    private GoodsAdapter mAdapter;
    
    private ListView mListView;
    
    private boolean mIsRefresh;
    
    
    private View mLoadingView; 
    
    private ViewGroup mRefreshLayout;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list_layout);
        initView();
        initData();
    };
    
    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.setTitle("促销消息");
        mTitleBar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
//        mTitleBar.findViewById(R.id.comment).setVisibility(View.GONE);
//        mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
//        mTitleBar.findViewById(R.id.fav).setVisibility(View.GONE);
        
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mPullToRefreshListView.setOnRefreshListener(this);
        mAdapter = new GoodsAdapter(this);
        mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
        mPullToRefreshListView.setOnScrollListener(this);
        mListView = mPullToRefreshListView.getRefreshableView();
        mListView.setEmptyView(findViewById(R.id.empty));
        mListView.setOnItemClickListener(new GoodsAdapter.GoodsItemClickListener());
        mLoadingView = findViewById(R.id.loading_layout);
        mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
        
    }
    
    private void initData(){
        showProgress();
        mIsRefresh = true;
        mControl.getGoodsDataAsyn();
    }
    
    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mControl.getGoodsDataAsyn();
    }
    
    private void getMoreData(){
        if(mIsRefresh)
            return;
        if(!mRefreshLayout.isShown())
            mRefreshLayout.setVisibility(View.VISIBLE);
        mPullToRefreshListView.addFooterView(mRefreshLayout);
        mControl.getGoodsDataMoreAsyn();
        mIsRefresh = true;
    }
    
    // ============================== OnScroll ==========================================
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        if(!mIsRefresh && position == mAdapter.getCount()){
            getMoreData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
    }
    
    // ============================== ProgressDialog ==========================================
    private void showProgress(){
        mLoadingView.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
        if(!animationDrawable.isRunning())
            animationDrawable.start();
        mPullToRefreshListView.setVisibility(View.GONE);
    }
    
    private void dissProgress(){
        mLoadingView.setVisibility(View.GONE);
        mPullToRefreshListView.setVisibility(View.VISIBLE);
    }
    
    // ============================== CallBack ==========================================
    public void getGoodsDataAsynCallBack(){
        mAdapter.clear();
        mAdapter.addAll(mControl.getModel().getGoodsList());
        mAdapter.notifyDataSetChanged();
        mIsRefresh = false;
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
        Toast.makeText(this, "加载成功", 0).show();
    }
    
    public void getGoodsDataAsynExceptionCallBack(){
        Toast.makeText(this, "加载数据错误", 0).show();
        mIsRefresh = false;
        dissProgress();
        mPullToRefreshListView.onRefreshComplete();
    }
    
    public void getGoodsDataMoreAsynCallBack(){
        dissProgress();
        mIsRefresh = false;
        mAdapter.addAll(mControl.getModel().getGoodsList());
        mAdapter.notifyDataSetChanged();
        mPullToRefreshListView.removeFooterView(mRefreshLayout);
        Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
    }
    
    public void getGoodsDataMoreAsynExceptionCallBack(){
        dissProgress();
        mIsRefresh = false;
        mPullToRefreshListView.removeFooterView(mRefreshLayout);
    }
}
