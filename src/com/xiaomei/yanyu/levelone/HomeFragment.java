package com.xiaomei.yanyu.levelone;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.levelone.adapter.HomeAdapter;
import com.xiaomei.yanyu.levelone.adapter.HomeAdapter2;
import com.xiaomei.yanyu.levelone.control.HomeControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment<HomeControl> implements
		OnRefreshListener, OnScrollListener {
	
	private ViewGroup mRootView;
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private HomeAdapter mAdapter;
	private View mEmptyView;
	private View mLoadingView; 
	private ViewGroup mRefreshLayout;
	
	private AbstractActivity mAc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAc = (AbstractActivity) getActivity();
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_home_layout, null);
			initView();
			setListener();
			initData();
			showProgress();
		}else{
			try {
				((ViewGroup)mRootView.getParent()).removeView(mRootView);
			} catch (Exception e) {
			}
		}
		return mRootView;
	}

	private void initView(){
		TitleBar mTitleBar = (TitleBar) mRootView.findViewById(R.id.title_bar);
		mTitleBar.setTitle("首页");
		
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshListView.getRefreshableView();
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
		mAdapter = new HomeAdapter(getActivity());
		mListView.setAdapter(mAdapter);
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
		mPullToRefreshListView.setOnScrollListener(this);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initData();
			}
		});
	}
	
	private void initData(){
		mIsRefresh = true;
		mControl.getHomeListEntityAsyn();
	}
	
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		mPullToRefreshListView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void showEmpty(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onRefresh() {
		mIsRefresh = true;
		mControl.getHomeListEntityAsyn();
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getMoreListDataFromNetAysn();
		mIsRefresh = true;
	}
	
	// ===========================  CallBackl ====================================
	
	public void getHomeListEntityAsynCallBack(){
		mIsRefresh = false;
		dissProgress();
		if(mPullToRefreshListView.isRefreshing())
			mPullToRefreshListView.onRefreshComplete();
		mAdapter.clear();
		mAdapter.addAll(mControl.getSectionList());
		mAdapter.notifyDataSetChanged();
		
		Toast.makeText(getActivity(), "加载完成", 0).show();
	}
	
	public void getHomeListEntityAsynCallBackNull(){
		mIsRefresh = false;
		dissProgress();
		showEmpty();
		Toast.makeText(getActivity(), "网络异常l", 0).show();
	}
	
	public void getHomeListEntityAsynCallBackException(){
		mIsRefresh = false;
		dissProgress();
		showEmpty();
		Toast.makeText(getActivity(), "网络异常", 0).show();
	}

	public void getHomeListEntityMoreAsynCallBack(){
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		mAdapter.addAll(mControl.getSectionList());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getHomeListEntityMoreAsynCallBackException(){
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
//		mAc.showToast("没有更多数据啦");
	}
	
	// ===========================  Scroll ====================================
	private boolean mIsRefresh = false;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getCount() ){
			getMoreData();
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}
}
