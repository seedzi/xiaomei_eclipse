package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

public class CollectionActivity extends AbstractActivity<UserCenterControl> implements OnScrollListener,OnRefreshListener{
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,CollectionActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}

	private TitleBar mTitleBar;
	
	private ListView mListView;
	
	private MyAdapter mAdapter;
	
	private boolean mIsRefresh;
	
	private View mLoadingView; 
	
	private ViewGroup mRefreshLayout;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_layout);
		setUpView();
		initData();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_collection));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mLoadingView = findViewById(R.id.loading_layout);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
        mPullToRefreshListView.setOnRefreshListener(this);
		mRefreshLayout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_footer, null);
        mListView = mPullToRefreshListView.getRefreshableView();
        mAdapter = new MyAdapter(CollectionActivity.this);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(this);
	}
	
	private void initData(){
        showProgress();
        mIsRefresh = true;
        mControl.getUserFav();
	}
	
	private void getMoreData(){
	       if(mIsRefresh)
	            return;
	        if(!mRefreshLayout.isShown())
	            mRefreshLayout.setVisibility(View.VISIBLE);
	        mPullToRefreshListView.addFooterView(mRefreshLayout);
	        mControl.getUserFavMore();
	        mIsRefresh = true;
	}
	
  // ============================== Progressbar ==========================================
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
	
	// ============================== Callback ==========================================
	public void getUserFavCallBack(){
	       mAdapter.setData(mControl.getModel().getGoodsList());
	        mAdapter.notifyDataSetChanged();
	        mIsRefresh = false;
	        dissProgress();
	        mPullToRefreshListView.onRefreshComplete();
	        Toast.makeText(this, "加载成功", 0).show();
	}
	
	public void getUserFavExceptionCallBack(){
	       Toast.makeText(this, "加载数据错误", 0).show();
	        mIsRefresh = false;
	        dissProgress();
	        mPullToRefreshListView.onRefreshComplete();
	}
	
	public void getUserFavMoreCallBack(){
	       dissProgress();
	        mIsRefresh = false;
	        mAdapter.getData().addAll(mControl.getModel().getGoodsList());
	        mAdapter.notifyDataSetChanged();
	        mPullToRefreshListView.removeFooterView(mRefreshLayout);
	        Toast.makeText(this, getResources().getString(R.string.get_data_sucess), 0).show();
	}
	
	public void getUserFavMoreExceptionCallBack(){
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
	                holder.iconIv.setImageResource(R.drawable.goods_list_default);
	                ImageLoader.getInstance().displayImage(goods.getFileUrl(),holder.iconIv );
	                android.util.Log.d("111", "holder.iconIv height = " + holder.iconIv.getHeight() + ",  width = " + holder.iconIv.getWidth());
	                holder.titleTv .setText(goods.getTitle());
	                holder.goodId = goods.getId();
	                holder.sizeTv.setText("销量" + goods.getSales());
	                holder.hospitalTv.setText(goods.getHospName());
	                holder.priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
	                holder.localTv.setText(goods.getCityName());
	                
//	              holder.sizeTv.setText(goods.getPriceMarket());
//	              holder.hospitalTv.setText(goods.ge);
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
//	              OrderDetailsActivity.startActivity(GoodsListActivity.this,holder.goodId);
//	              GoodsDetailActivity.startActivity(GoodsListActivity.this,holder.goodId);
//	              GoodsDetailActivity.startActivity(GoodsListActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId);
	                GoodsDetailActivity.startActivity(CollectionActivity.this,HttpUrlManager.GOODS_DETAIL_URL+"?goods_id="+holder.goodId,holder.goodId);
//	              WebViewActivity.startActivity(GoodsListActivity.this ,"http://drxiaomei.duapp.com/goods.php?goods_id=1015");
	            }
	            
	        }

    @Override
    public void onRefresh() {
        mIsRefresh = true;
        mControl.getUserFav();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int position = mListView.getLastVisiblePosition();
        if (!mIsRefresh && position == mAdapter.getCount()) {
            getMoreData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub

    }

}
