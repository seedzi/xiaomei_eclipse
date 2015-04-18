package com.xiaomei.leveltwo;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.api.HttpUrlManager;
import com.xiaomei.bean.Goods;
import com.xiaomei.leveltwo.control.LeveltwoControl;
import com.xiaomei.module.user.center.OrderDetailsActivity;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;

public class GoodsListActivity extends AbstractActivity<LeveltwoControl>{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,GoodsListActivity.class);
		context.startActivity(intent);
	}

	private TitleBar mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private MyAdapter mAdapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_list_layout);
		initView();
		initData();
	};
	
	private void initView() {
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle("内素除皱");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
		mAdapter = new MyAdapter(this);
		mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
	}
	
	private void initData(){
		mControl.getGoodsDataAsyn();
	}
	
	// ============================== CallBack ==========================================
	public void getGoodsDataAsynCallBack(){
		mAdapter.setData(mControl.getModel().getGoodsList());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getGoodsDataAsynExceptionCallBack(){
		Toast.makeText(this, "加载数据错误", 0).show();
	}
	
	private class MyAdapter extends BaseAdapter implements View.OnClickListener{
		
		private LayoutInflater mLayoutInflater;
		
		private List<Goods> mData;
		
		public MyAdapter(Context context){
			mLayoutInflater = LayoutInflater.from(context);
		}
		
		public void setData(List<Goods> data){
			mData = data;
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
				holder.sizeTv = (TextView) findViewById(R.id.size);
				holder.hospitalTv = (TextView) findViewById(R.id.hospital);
				convertView.setOnClickListener(this);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			Goods goods = mData.get(position);
			ImageLoader.getInstance().displayImage(goods.getFileUrl(),holder.iconIv );
			holder.titleTv .setText(goods.getTitle());
			holder.goodId = goods.getId();
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
