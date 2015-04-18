package com.xiaomei.module.user.center;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
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

public class UserOrderListActivity extends BaseActivity<UserCenterControl> {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserOrderListActivity.class);
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
		Toast.makeText(UserOrderListActivity.this, "加载成功", 0).show();
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
			Holder holder = null;
			if(convertView == null){
				convertView = mLayoutInflater.inflate(R.layout.item_user_order_layout,null);
				holder = new Holder();
				holder.orderIdTv = (TextView) convertView.findViewById(R.id.order_id);
				holder.userNameTv = (TextView) convertView.findViewById(R.id.user_name);
				holder.createTimeTv = (TextView) convertView.findViewById(R.id.create_time);
				holder.goodsIconIv = (ImageView) convertView.findViewById(R.id.goods_icon);
				holder.goodsNameTv = (TextView) convertView.findViewById(R.id.goods_name);
				holder.statusTv = (TextView) convertView.findViewById(R.id.status);
				holder.orderAmountTv = (TextView) convertView.findViewById(R.id.order_amount);
				holder.payButton = convertView.findViewById(R.id.pay_button);
				holder.payButton.setOnClickListener(this);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			Order order = data.get(position);
			Order.DataList dataList = order.getDataList();
			if(dataList!=null){
				holder.orderIdTv.setText(dataList.getId());
				holder.userNameTv.setText(dataList.getUsername());
				holder.createTimeTv.setText(dataList.getCreatedate());
				ImageLoader.getInstance().displayImage(dataList.getGoodsImg(), holder.goodsIconIv);
				holder.goodsNameTv.setText(dataList.getGoodsName());
				holder.statusTv.setText(dataList.getStatus());
				holder.orderAmountTv.setText(dataList.getOrderAmount());
				holder.payButton.setTag(Integer.valueOf(position));
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Order order = data.get(position);
			OrderDetailsActivity.startActivity(UserOrderListActivity.this,order.getDataList().getGoodsId());
		}
		
		private class Holder {
			TextView orderIdTv; //订单号
			TextView userNameTv; //用户名
			TextView createTimeTv; //创建时间
			ImageView goodsIconIv; //商品icon
			TextView goodsNameTv; //商品名
			TextView statusTv;//订单状态
			TextView orderAmountTv; //订单价格
			View payButton; //按钮
		}
	}
}
