package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;

public class UserOrderActivity extends BaseActiviy {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserOrderActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar  mTitleBar;
	
	private PullToRefreshListView mList;
	
	private OrderAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_order));
		mList = (PullToRefreshListView) findViewById(R.id.list);
		mAdapter = new OrderAdapter(this);
		mList.getRefreshableView().setAdapter(mAdapter);
	}
	
	private class OrderAdapter extends BaseAdapter implements View.OnClickListener{

		private LayoutInflater mLayoutInflater;
		
		public OrderAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return 10;
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
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_user_order_layout,null);
				convertView.setOnClickListener(this);
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			OrderDetailsActivity.startActivity(UserOrderActivity.this);
		}
		
	}
}
