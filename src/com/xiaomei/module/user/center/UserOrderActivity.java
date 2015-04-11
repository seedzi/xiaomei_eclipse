package com.xiaomei.module.user.center;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.bean.Order;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.module.user.control.UserControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseActivity;

public class UserOrderActivity extends BaseActivity<UserCenterControl> {
	
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
		initData();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_order));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mList = (PullToRefreshListView) findViewById(R.id.list);
		mAdapter = new OrderAdapter(this);
		mList.getRefreshableView().setAdapter(mAdapter);
		
	}
	
	private void initData(){
		mControl.getUserOrdersAsyn();
	}
	
	// =============================================== CallBack  =================================================
	public void getUserOrdersAsynCallBack(){
		mAdapter.setData(mControl.getModel().getOrderList());
		mAdapter.notifyDataSetChanged();
		Toast.makeText(UserOrderActivity.this, "加载成功", 0).show();
	}
	
	public void getUserOrdersAsynExceptionCallBack(){
		
	}
	
	
	private class OrderAdapter extends BaseAdapter implements View.OnClickListener{

		private LayoutInflater mLayoutInflater;
		
		private List<Order> data;
		
		public OrderAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}
		
		public void setData(List<Order> list){
			data = list;
		}
		
		public List<Order> getData(List<Order> list){
			return data;
		}

		@Override
		public int getCount() {
			return data == null ? 0 : data.size();
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
