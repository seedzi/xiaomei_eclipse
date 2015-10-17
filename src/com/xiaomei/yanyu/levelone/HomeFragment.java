package com.xiaomei.yanyu.levelone;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.levelone.adapter.HomeListManager;
import com.xiaomei.yanyu.levelone.control.HomeControl;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment<HomeControl> implements
		OnRefreshListener {
	
	private ViewGroup mRootView;
//	private PullToRefreshListView mPullToRefreshListView;
//	private ListView mListView;
//	private HomeAdapter2 mAdapter;
	private View mEmptyView;
	private View mLoadingView; 
	private ViewGroup mRefreshLayout;
	
	private PullToRefreshScrollView mScrollView;
	private ViewGroup mContainerView;
	private HomeListManager mHomeListManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
//		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
//		mListView = mPullToRefreshListView.getRefreshableView();
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
//		mAdapter = new HomeAdapter2(getActivity());
//		mListView.setAdapter(mAdapter);
		
		mContainerView = (ViewGroup) mRootView.findViewById(R.id.root);
		mScrollView =  (PullToRefreshScrollView) mRootView.findViewById(R.id.pull_view);
		mScrollView.setOnRefreshListener(this);
		
		mHomeListManager = new HomeListManager();
		mHomeListManager.setupView(mContainerView, getActivity());
	}
	
	private void setListener(){
//		mPullToRefreshListView.setOnRefreshListener(this);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initData();
			}
		});
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleActionBar titleBar = ((TabsActivity) getActivity()).getTitleBar();
        titleBar.setTitle(R.string.fragment_home);
        titleBar.setActionVisibility(View.GONE);
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
//		mPullToRefreshListView.setVisibility(View.GONE);
		mScrollView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
//		mPullToRefreshListView.setVisibility(View.VISIBLE);
		mScrollView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.GONE);
	}
	
	private void showEmpty(){
		mLoadingView.setVisibility(View.GONE);
//		mPullToRefreshListView.setVisibility(View.GONE);
		mScrollView.setVisibility(View.GONE);
		mEmptyView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		mIsRefresh = true;
		mControl.getHomeListEntityAsyn();
	}
	
	// ===========================  CallBackl ====================================
	
	public void getHomeListEntityAsynCallBack(){
		mIsRefresh = false;
		dissProgress();
//		if(mPullToRefreshListView.isRefreshing())
//			mPullToRefreshListView.onRefreshComplete();
//		mAdapter.setData(mControl.getModel().getList());
//		mAdapter.notifyDataSetChanged();
		mHomeListManager.setData(mControl.getModel().getList());
		mScrollView.onRefreshComplete();
	}
	
	public void getHomeListEntityAsynCallBackNull(){
		mIsRefresh = false;
		dissProgress();
		showEmpty();
		mScrollView.onRefreshComplete();
		Toast.makeText(getActivity(), "网络异常l", 0).show();
	}
	
	public void getHomeListEntityAsynCallBackException(){
		mIsRefresh = false;
		dissProgress();
		showEmpty();
		mScrollView.onRefreshComplete();
		Toast.makeText(getActivity(), "网络异常", 0).show();
	}
	
	private boolean mIsRefresh = false;
}
