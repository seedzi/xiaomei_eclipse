package com.xiaomei.levelone;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.R;
import com.xiaomei.levelone.adapter.HomeAdapter;
import com.xiaomei.levelone.control.HomeControl;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment<HomeControl> {
	
	private ViewGroup mRootView;
	private PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private HomeAdapter mAdapter;
	private View mEmptyView;
	private View mLoadingView; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_home_layout, null);
			initView();
			initData();
			showProgress();
		}else{
			((ViewGroup)mRootView.getParent()).removeView(mRootView);
		}
		return mRootView;
	}

	private void initView(){
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);
		mListView = mPullToRefreshListView.getRefreshableView();
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
	}
	
	private void initData(){
		mAdapter = new HomeAdapter(null, getActivity(), ImageLoader.getInstance());
		mListView.setAdapter(mAdapter);
		mControl.getHomeListEntityAsyn();
	}
	
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		mPullToRefreshListView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		mPullToRefreshListView.setVisibility(View.VISIBLE);
	}
	
	public void getHomeListEntityAsynCallBack(){
		mAdapter.setData(mControl.getSectionList());
		mAdapter.notifyDataSetChanged();
		dissProgress();
		Toast.makeText(getActivity(), "getHomeListEntityAsynCallBack", 0).show();
	}
	
	public void getHomeListEntityAsynCallBackNull(){
		Toast.makeText(getActivity(), "getHomeListEntityAsynCallBackNull", 0).show();
	}
	
	public void getHomeListEntityAsynCallBackException(){
		Toast.makeText(getActivity(), "getHomeListEntityAsynCallBackException", 0).show();
	}
}
