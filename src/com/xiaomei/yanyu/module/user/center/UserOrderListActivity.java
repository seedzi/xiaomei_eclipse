package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.module.user.control.UserControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseActivity;

public class UserOrderListActivity extends BaseActivity<UserCenterControl> {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserOrderListActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar  mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
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
		
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.list);
		mAdapter = new OrderAdapter(this);
		mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
		mPullToRefreshListView.setPullToRefreshEnabled(false);
		mLoadingView = findViewById(R.id.loading_layout);
	}
	
	private void initData(){
		showProgress();
		mControl.getUserOrdersAsyn();
	}
	// =============================================== ProgressDialog  =================================================
	private View mLoadingView; 
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
	
	
	// =============================================== CallBack  =================================================
	public void getUserOrdersAsynCallBack(){
		mAdapter.setData(mControl.getModel().getOrderList());
		mAdapter.notifyDataSetChanged();
		Toast.makeText(UserOrderListActivity.this, "加载成功", 0).show();
		dissProgress();
	}
	
	public void getUserOrdersAsynExceptionCallBack(){
		dissProgress();
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
				holder.payButton = (TextView) convertView.findViewById(R.id.pay_button);
				holder.payButton.setOnClickListener(this);
				convertView.setTag(holder);
			}
			holder = (Holder) convertView.getTag();
			Order order = data.get(position);
			Order.DataList dataList = order.getDataList();
			if(dataList!=null){
				android.util.Log.d("111", "status = " + dataList.getStatus());
				holder.orderIdTv.setText(dataList.getId());
				holder.userNameTv.setText(dataList.getUsername());
				holder.createTimeTv.setText(dataList.getCreatedate());
				ImageLoader.getInstance().displayImage(dataList.getGoodsImg(), holder.goodsIconIv);
				holder.goodsNameTv.setText(dataList.getGoodsName());
				holder.statusTv.setText(dataList.getStatus());
				holder.orderAmountTv.setText(getResources().getString(R.string.ren_ming_bi) + " " + dataList.getOrderAmount());
				holder.payButton.setTag(Integer.valueOf(position));
				int status = Integer.valueOf(dataList.getStatus());
				switch (status) {
				case 1: //未支付
					holder.payButton.setBackgroundResource(R.drawable.payment_selector);
					holder.payButton.setText("立即付款");
					break;
				case 2: //已付款
					holder.payButton.setBackgroundResource(R.drawable.payment_over);
					holder.payButton.setText("订单详情");
					break;
				case 3: //
					holder.payButton.setBackgroundResource(R.drawable.payment_over);
					holder.payButton.setText("订单已取消");
					break;
				case 4:
					holder.payButton.setBackgroundResource(R.drawable.payment_selector);
					holder.payButton.setText("去评论");
					break;
				case 5:
					holder.payButton.setBackgroundResource(R.drawable.payment_over);
					holder.payButton.setText("完成");
					break;
				default:
					break;
				}
			}
			return convertView;
		}

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Order order = data.get(position);
			if(order == null)
				return;
			switch (Integer.valueOf(order.getDataList().getStatus())) {
			case 1:
				OrderDetailsActivity.startActivity(UserOrderListActivity.this, order);
				break;
			case 2:
				OrderDetailsActivity.startActivity(UserOrderListActivity.this, order);
				break;
			case 4:
				CommentsActivity.startActivity4Result(UserOrderListActivity.this, order);
				break;

			default:
				break;
			}
		}
		
		private class Holder {
			TextView orderIdTv; //订单号
			TextView userNameTv; //用户名
			TextView createTimeTv; //创建时间
			ImageView goodsIconIv; //商品icon
			TextView goodsNameTv; //商品名
			TextView statusTv;//订单状态
			TextView orderAmountTv; //订单价格
			TextView payButton; //按钮
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if(resultCode == RESULT_OK){
				initData();
			}
			break;

		default:
			break;
		}
	}
}
