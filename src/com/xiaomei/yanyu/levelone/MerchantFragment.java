package com.xiaomei.yanyu.levelone;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.adapter.MerchantAdapter;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.levelone.control.MerchantControl;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener2;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MerchantFragment extends BaseFragment<MerchantControl>
	implements OnRefreshListener2 {
	
	private ViewGroup mRootView;
	
	private PullToRefreshListView mPullToRefreshListView;;
	
	private ListView mListView;
	
	private MerchantAdapter mAdapter;
	
	private ViewGroup mRefreshLayout;
	
	private View mLoadingView; 
	
	private View mEmptyView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_merchant_layout, null);
			setUpView();
			setListener();
			initData();
		}else{
			try {
				((ViewGroup)mRootView.getParent()).removeView(mRootView);
			} catch (Exception e) {
			}
		}
		return mRootView;
	}

	private void setUpView(){
		mPullToRefreshListView = (PullToRefreshListView) mRootView.findViewById(R.id.list);

		mListView = mPullToRefreshListView.getRefreshableView();
		mAdapter = new MerchantAdapter(getActivity()); 
		mListView.setAdapter(mAdapter);
//		mListView.setOnItemClickListener(new MerchantAdapter.MerchantItemClickListener());
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		mRefreshLayout = (ViewGroup) inflater.inflate(R.layout.pull_to_refresh_footer, null);
		
		mLoadingView = mRootView.findViewById(R.id.loading_layout);
		
		mEmptyView= mRootView.findViewById(R.id.empty_view);
		mEmptyView.findViewById(R.id.reload_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showProgress();
				initData();
			}
		});
	}
	
	private void setListener(){
		mPullToRefreshListView.setOnRefreshListener(this);
//		mPullToRefreshListView.setOnScrollListener(this);
	}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleActionBar titleBar = ((TabsActivity) getActivity()).getTitleBar();
        titleBar.setTitle(R.string.fragment_merchant);
        titleBar.setActionVisibility(View.GONE);
    }

	private void initData(){
		showProgress();
		mControl.getMerchantListAsyn();
	}
	
	private void getMoreData(){
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getMerchantListMoreAsyn();
	}
	
	// ================================== Progress ==========================================
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
	
	// ================================== Call back ==========================================
	public void getMerchantLismListCallBack(){
		dissProgress();
		mAdapter.clear();
		mAdapter.addAll(mControl.getModel().getData());
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getMerchantListExceptionCallBack(){
		dissProgress();
		showEmpty();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}

	public void getMerchantLismListMoreCallBack(){
		dissProgress();
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		mAdapter.addAll(mControl.getModel().getData());
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), "加载完成", 0).show();
	}
	
	public void getMerchantListMoreExceptionCallBack(){
		dissProgress();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}
	// ================================== Call back ==========================================
	@Override
	public void onPullDownToRefresh() {
		mControl.getMerchantListAsyn();
	}
	
	@Override
    public void onPullUpToRefresh() {
	    getMoreData();
	}
}
