package com.xiaomei.yanyu.leveltwo;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.GoodsOption;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.TopFilter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class GoodsListActivity extends AbstractActivity<LeveltwoControl> implements OnScrollListener,OnRefreshListener{
	
	private static final int SUB_CAT = 0;

    private static final int ORIGIN_PLACE = 1;

    private static final int PRICE_ORDER = 2;

    public static void startActivity(Activity ac,String catId,String title){
		Intent intent = new Intent(ac,GoodsListActivity.class);
		intent.putExtra("cat_id", catId);
		intent.putExtra("title", title);
		ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
	}

	private TitleBar mTitleBar;
    private TopFilter mTopFilter;
	private PullToRefreshListView mPullToRefreshListView;
	
	private GoodsAdapter mAdapter;
	
	private ListView mListView;
	
	private boolean mIsRefresh;
	
	private String catId;
    private String mSubCat = "";
    private String mOriginPlace = "";
    private String mPriceOrder = "";
	
	private View mLoadingView; 
	
	private ViewGroup mRefreshLayout;
	
	private String title;

    private List<GoodsOption> mGoodsOptions;

    private FilterAdapter mSubCatAdapter;
    private FilterAdapter mOriginPlaceAdapter;
    private FilterAdapter mPriceOrderAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_list_layout);
		catId = getIntent().getStringExtra("cat_id");
		title = getIntent().getStringExtra("title");
		initView();
		initData();
	};
	
	private void initView() {
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(title);
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.comment).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.fav).setVisibility(View.GONE);
		
		mTopFilter = (TopFilter) findViewById(R.id.filter);
		mSubCatAdapter = new FilterAdapter(this);
        mOriginPlaceAdapter = new FilterAdapter(this);
        mPriceOrderAdapter = new FilterAdapter(this);
        mTopFilter.addAll(new ListAdapter[]{mSubCatAdapter, mOriginPlaceAdapter, mPriceOrderAdapter});

		mTopFilter.getFilter(SUB_CAT).setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		        Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
		        if (!item.second.equals(mSubCat)) {
		            mSubCat = item.second;
		            onRefresh(mPullToRefreshListView);
		        }
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		    
		});
		mTopFilter.getFilter(ORIGIN_PLACE).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
                if (!item.second.equals(mOriginPlace)) {
                    mOriginPlace = item.second;
                    onRefresh(mPullToRefreshListView);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            
        });
		mTopFilter.getFilter(PRICE_ORDER).setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
                if (!item.second.equals(mPriceOrder)) {
                    mPriceOrder = item.second;
                    onRefresh(mPullToRefreshListView);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            
        });

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//		for(DropMenu filter : mFilters) {
//		    filter.setDropDownWidth(displaymetrics.widthPixels);
//		}
		
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
		mPullToRefreshListView.setOnRefreshListener(this);
		mAdapter = new GoodsAdapter(this);
		mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
		mPullToRefreshListView.setOnScrollListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
		mListView.setEmptyView(findViewById(R.id.empty));
		mLoadingView = findViewById(R.id.loading_layout);
		mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
		mListView.setOnItemClickListener(new GoodsAdapter.GoodsItemClickListener());
	}
	
	private void initData(){
		showProgress();
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId, mSubCat, mOriginPlace, mPriceOrder);
		mControl.getGoodsOptionAsyn(catId);
	}
	
	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId, mSubCat, mOriginPlace, mPriceOrder);
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.getRefreshableView().addFooterView(mRefreshLayout);
		mControl.getGoodsDataMoreAsyn(catId, mSubCat, mOriginPlace, mPriceOrder);
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
		mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
	}
	
	public void getGoodsDataMoreAsynExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
		mPullToRefreshListView.getRefreshableView().removeFooterView(mRefreshLayout);
	}
	
	public void getGoodsOptionAsynCallBack() {
	    mGoodsOptions = mControl.getModel().getGoodsOptions();
	    mSubCatAdapter.addAll(findByOptionType(mGoodsOptions, "sub_cat").getItems());
	    mOriginPlaceAdapter.addAll(findByOptionType(mGoodsOptions, "origin_place").getItems());
	    mPriceOrderAdapter.addAll(findByOptionType(mGoodsOptions, "price_order").getItems());
	    mTopFilter.setVisibility(View.VISIBLE);
	}
	
	private GoodsOption findByOptionType(List<GoodsOption> options, String type) {
	    if (type == null || type.isEmpty()) {
	        return null;
	    }
	    for(GoodsOption option : options) {
	        if (type.equals(option.getType())) {
	            return option;
	        }
	    }
	    return null;
	}
	
	public void getGoodsOptionAsynExceptionCallBack() {
	    // TODO Try again or show some warnings
	}
	
	private class FilterAdapter extends ArrayAdapter<Pair<String, String>> {

        public FilterAdapter(Context context) {
            super(context, 0);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }
        
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View root = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.top_filter_drop_item, parent, false);
            ((TextView) root.findViewById(android.R.id.text1)).setText(getItem(position).first);
            return root;
        }
    }
}
