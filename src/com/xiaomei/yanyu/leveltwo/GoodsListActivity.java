package com.xiaomei.yanyu.leveltwo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.GoodsOption;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.widget.DropMenu;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

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
		 ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}

	private TitleBar mTitleBar;
	
	private DropMenu[] mFilters = new DropMenu[3];
	
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
		
		findViewById(R.id.filter).setVisibility(View.VISIBLE);
		mFilters[SUB_CAT] = (DropMenu) findViewById(R.id.sub_cat);
		mFilters[ORIGIN_PLACE] = (DropMenu) findViewById(R.id.origin_place);
		mFilters[PRICE_ORDER] = (DropMenu) findViewById(R.id.price_order);
		
		mFilters[SUB_CAT].setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		        Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
		        if (!item.second.equals(mSubCat)) {
		            mSubCat = item.second;
		            onRefresh();
		        }
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		    
		});
		mFilters[ORIGIN_PLACE].setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
                if (!item.second.equals(mOriginPlace)) {
                    mOriginPlace = item.second;
                    onRefresh();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
            
        });
		mFilters[PRICE_ORDER].setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Pair<String, String> item = (Pair<String, String>) parent.getAdapter().getItem(position);
                if (!item.second.equals(mPriceOrder)) {
                    mPriceOrder = item.second;
                    onRefresh();
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
		mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Goods goods = (Goods) parent.getItemAtPosition(position);
                String goodsId = goods.getId();
                GoodsDetailActivity.startActivity((Activity) view.getContext(), HttpUrlManager.GOODS_DETAIL_URL+"?goods_id=" + goodsId, goodsId);
            }
        });
	}
	
	private void initData(){
		showProgress();
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId, mSubCat, mOriginPlace, mPriceOrder);
		mControl.getGoodsOptionAsyn(catId);
	    
	    String[] default_selection = new String[]{"全部"};
        for(int i = 0; i < mFilters.length; i++) {
            mFilters[i].setAdapter(new FilterAdapter(this));
        }
        
	}
	
	@Override
	public void onRefresh() {
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId, mSubCat, mOriginPlace, mPriceOrder);
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
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
	
	public void getGoodsOptionAsynCallBack() {
	    mGoodsOptions = mControl.getModel().getGoodsOptions();
	    
	    ArrayAdapter<Pair<String, String>> adapter;
	    adapter = (ArrayAdapter<Pair<String, String>>) mFilters[SUB_CAT].getAdapter();
	    adapter.addAll(findByOptionType(mGoodsOptions, "sub_cat").getItems());
	    
	    adapter = (ArrayAdapter<Pair<String, String>>) mFilters[ORIGIN_PLACE].getAdapter();
	    adapter.addAll(findByOptionType(mGoodsOptions, "origin_place").getItems());
	    
	    adapter = (ArrayAdapter<Pair<String, String>>) mFilters[PRICE_ORDER].getAdapter();
	    adapter.addAll(findByOptionType(mGoodsOptions, "price_order").getItems());
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
