package com.xiaomei.yanyu.levelone;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.adapter.MerchantAdapter;
import com.xiaomei.yanyu.bean.Area.Filter;
import com.xiaomei.yanyu.bean.Area.FilterItem;
import com.xiaomei.yanyu.bean.AreaFilterLoader;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.levelone.control.MerchantControl;
import com.xiaomei.yanyu.view.FilterAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.xiaomei.yanyu.widget.TopFilter;
import com.yuekuapp.BaseFragment;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MerchantFragment extends BaseFragment<MerchantControl>
	implements OnRefreshListener, OnLastItemVisibleListener, LoaderManager.LoaderCallbacks<Object> {
	
	private static final int MERCHANT_FILTER_LOADER = 0;

    private ViewGroup mRootView;
	
    private TopFilter mTopFilter;
    private PullToRefreshListView mPullToRefreshListView;;
	
	private ListView mListView;
	
    private View mLoadingView; 
	
	private View mEmptyView;

	private MerchantAdapter mAdapter;
    private ViewGroup mRefreshLayout;
    private FilterAdapter mCountryAdapter;
    private FilterAdapter mSpecialAdapter;

    private String mFilterCountry = "";
    private String mFilterSpecial = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(mRootView == null){
			mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_merchant_layout, null);
			setUpView();
			setListener();
			getLoaderManager().initLoader(MERCHANT_FILTER_LOADER, null, this);
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
	    mCountryAdapter = new FilterAdapter(getActivity());
        mSpecialAdapter = new FilterAdapter(getActivity());
        mTopFilter = (TopFilter) mRootView.findViewById(R.id.filter);
        mTopFilter.addAll(new ListAdapter[]{mCountryAdapter, mSpecialAdapter});
        mTopFilter.getFilter(0).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) parent.getItemAtPosition(position);
                mFilterCountry = item.getKey();
                onRefresh(mPullToRefreshListView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mTopFilter.getFilter(1).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) parent.getItemAtPosition(position);
                mFilterSpecial = item.getKey();
                onRefresh(mPullToRefreshListView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
		mPullToRefreshListView.setOnLastItemVisibleListener(this);
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
		mControl.getMerchantListAsyn(mFilterCountry, mFilterSpecial);
	}
	
	private void getMoreData(){
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.getRefreshableView().addFooterView(mRefreshLayout);
		mControl.getMerchantListMoreAsyn(mFilterCountry, mFilterSpecial);
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
	}
	
	public void getMerchantListExceptionCallBack(){
		dissProgress();
		showEmpty();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}

	public void getMerchantLismListMoreCallBack(){
		dissProgress();
		mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
		mAdapter.addAll(mControl.getModel().getData());
		mPullToRefreshListView.onRefreshComplete();
	}
	
	public void getMerchantListMoreExceptionCallBack(){
		dissProgress();
		mPullToRefreshListView.onRefreshComplete();
		Toast.makeText(getActivity(), "加载数据异常", 0).show();
	}
	// ================================== Call back ==========================================
	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		mControl.getMerchantListAsyn(mFilterCountry, mFilterSpecial);
	}

    @Override
    public void onLastItemVisible() {
        getMoreData();
    }
    
    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MERCHANT_FILTER_LOADER:
                return new AreaFilterLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case MERCHANT_FILTER_LOADER:
                if (data != null) {
                    Filter[] areaFilters = (Filter[]) data;
                    mCountryAdapter.clear();
                    mCountryAdapter.addAll(areaFilters[0].getItems());
                    mSpecialAdapter.clear();
                    mSpecialAdapter.addAll(areaFilters[1].getItems());
                }
                mTopFilter.setVisibility(data != null ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
