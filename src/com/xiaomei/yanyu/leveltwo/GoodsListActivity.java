package com.xiaomei.yanyu.leveltwo;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

public class GoodsListActivity extends AbstractActivity<LeveltwoControl> implements OnScrollListener,OnRefreshListener{
	
	public static void startActivity(Context context,String catId,String title){
		Intent intent = new Intent(context,GoodsListActivity.class);
		intent.putExtra("cat_id", catId);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}

	private TitleBar mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private MyAdapter mAdapter;
	
	private ListView mListView;
	
	private boolean mIsRefresh;
	
	private String catId;
	
	private View mLoadingView; 
	
	private ViewGroup mRefreshLayout;
	
	private String title;
	
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
		
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
		mPullToRefreshListView.setOnRefreshListener(this);
		mAdapter = new MyAdapter(this);
		mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
		mPullToRefreshListView.setOnScrollListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
		mLoadingView = findViewById(R.id.loading_layout);
		mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
	}
	
	private void initData(){
		android.util.Log.d("111", "catId = " + catId);
		showProgress();
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId);
	}
	
	@Override
	public void onRefresh() {
		mIsRefresh = true;
		mControl.getGoodsDataAsyn(catId);
	}
	
	private void getMoreData(){
		if(mIsRefresh)
			return;
		if(!mRefreshLayout.isShown())
			mRefreshLayout.setVisibility(View.VISIBLE);
		mPullToRefreshListView.addFooterView(mRefreshLayout);
		mControl.getGoodsDataMoreAsyn(catId);
		mIsRefresh = true;
	}
	
	// ============================== OnScroll ==========================================
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int position = mListView.getLastVisiblePosition();
		if(!mIsRefresh && position == mAdapter.getCount()){
			getMoreData();
		}
		android.util.Log.d("111", "position = " + position + ", mIsRefresh = " + mIsRefresh + ", mAdapter.getCount() = " + mAdapter.getCount());
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
		mAdapter.setData(mControl.getModel().getGoodsList());
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
		mAdapter.getData().addAll(mControl.getModel().getGoodsList());
		mAdapter.notifyDataSetChanged();
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
		Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getGoodsDataMoreAsynExceptionCallBack(){
		dissProgress();
		mIsRefresh = false;
		mPullToRefreshListView.removeFooterView(mRefreshLayout);
	}
	
	// ============================== Adapter ==========================================
	private class MyAdapter extends BaseAdapter implements View.OnClickListener{
		
		private LayoutInflater mLayoutInflater;
		
		private List<Goods> mData;
		
		public MyAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
		}
		
		public void setData(List<Goods> data){
			mData = data;
		}
		
		public List<Goods> getData(){
			return mData;
		}
		
		@Override
		public int getCount() {
			return mData==null ?0 : mData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_goods_layout, null);
				holder = new Holder();
				holder.iconIv = (ImageView) convertView.findViewById(R.id.icon);
				holder.titleTv = (TextView) convertView.findViewById(R.id.title);
				holder.sizeTv = (TextView) convertView.findViewById(R.id.size);
				holder.hospitalTv = (TextView) convertView.findViewById(R.id.hospital_name);
				holder.localTv = (TextView) convertView.findViewById(R.id.location);
				holder.priceTv = (TextView) convertView.findViewById(R.id.price);
				holder.localTv = (TextView) convertView.findViewById(R.id.location);
				convertView.setOnClickListener(this);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			Goods goods = mData.get(position);
			ImageLoader.getInstance().displayImage(goods.getFileUrl(),holder.iconIv );
			holder.titleTv .setText(goods.getTitle());
			holder.goodId = goods.getId();
			holder.sizeTv.setText("销量" + goods.getSales());
			holder.hospitalTv.setText(goods.getHospName());
			holder.priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
			holder.localTv.setText(goods.getCityName());
			
//			holder.sizeTv.setText(goods.getPriceMarket());
//			holder.hospitalTv.setText(goods.ge);
			return convertView;
		}
		
		private class Holder{
			ImageView iconIv;
			TextView titleTv; 
			TextView sizeTv;
			TextView hospitalTv;
			String goodId;
			TextView localTv;
			TextView priceTv;
		}

		@Override
		public void onClick(View v) {
			Holder holder = (Holder) v.getTag();
//			OrderDetailsActivity.startActivity(GoodsListActivity.this,holder.goodId);
//			GoodsDetailActivity.startActivity(GoodsListActivity.this,holder.goodId);
			GoodsDetailActivity.startActivity(GoodsListActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId);
			
//			WebViewActivity.startActivity(GoodsListActivity.this ,"http://drxiaomei.duapp.com/goods.php?goods_id=1015");
		}
		
	}


}
