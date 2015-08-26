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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.R.id;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.module.user.control.UserControl;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.util.YanYuUtils;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.yuekuapp.BaseActivity;

public class UserOrderListActivity extends AbstractActivity<UserCenterControl> {
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,UserOrderListActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar  mTitleBar;
	
	private PullToRefreshListView mPullToRefreshListView;
	
	private OrderAdapter mAdapter;
	
	private View mEmptyView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_layout);
		setUpView();
		initData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
        if(OrderDetailsActivity.STATE_CHANGED){
        	initData();
        	OrderDetailsActivity.STATE_CHANGED = false;
        }
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
		
		mEmptyView= findViewById(R.id.empty_view);
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
		if(!YanYuUtils.isConnect(this)){
			((TextView)mEmptyView.findViewById(R.id.txt)).setText("网络不给力哦！");
			((TextView)mEmptyView.findViewById(R.id.sub_txt)).setText("");
		}else{
			((TextView)mEmptyView.findViewById(R.id.txt)).setText("暂无订单记录");
			((TextView)mEmptyView.findViewById(R.id.sub_txt)).setText("去看看分类商铺吧");
		}
	}
	
	// =============================================== CallBack  =================================================
	public void getUserOrdersAsynCallBack(){
	    mAdapter.clear();
		mAdapter.addAll(mControl.getModel().getOrderList());
		mAdapter.notifyDataSetChanged();
		Toast.makeText(UserOrderListActivity.this, "加载成功", 0).show();
		dissProgress();
	}
	
	public void getUserOrdersAsynExceptionCallBack(){
		dissProgress();
		showEmpty();
	}
	
	
	private class OrderAdapter extends ArrayAdapter<Order> {
		
		public OrderAdapter(Context context) {
		    super(context, 0);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View itemView = convertView != null ? convertView
	                : LayoutInflater.from(getContext()).inflate(R.layout.item_user_order_layout, parent,
	                        false);
		    
			final Order order = getItem(position);
			Order.DataList dataList = order.getDataList();
			if(dataList!=null){
				UiUtil.findTextViewById(itemView, R.id.order_id).setText(dataList.getId());
				UiUtil.findTextViewById(itemView, R.id.user_name).setText(dataList.getUsername());
				UiUtil.findTextViewById(itemView, R.id.create_time).setText(DateUtils.formateDate(Long.valueOf(dataList.getCreatedate())*1000));
				
				ImageView icon = UiUtil.findImageViewById(itemView, R.id.goods_icon);
				icon.setImageResource(R.drawable.order_list_default);
				ImageLoader.getInstance().displayImage(dataList.getGoodsImg(), icon);
				
				UiUtil.findTextViewById(itemView, R.id.goods_name).setText(dataList.getGoodsName());
				UiUtil.findTextViewById(itemView, R.id.order_amount).setText(getResources().getString(R.string.ren_ming_bi) + " " + dataList.getGoodsPay());
				UiUtil.findTextViewById(itemView, R.id.city).setText(dataList.getCity());
				UiUtil.findTextViewById(itemView, R.id.hosp_name).setText(dataList.getHospName());
				
				int status = Integer.valueOf(dataList.getStatus());
				UiUtil.findTextViewById(itemView, R.id.status).setText(dataList.getStatusText());
				String payString = status == 1 ? "立即付款" : status == 4 ? "去评论" : "订单详情";
				int background = status == 1 || status == 4 ? R.drawable.payment_selector : R.drawable.payment_over;
				TextView payButton = UiUtil.findTextViewById(itemView, R.id.pay_button);
                payButton.setText(payString);
                payButton.setBackgroundResource(background);
                
                payButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order.getDataList().getStatus().equals("4")) {
                            CommentsActivity.startActivity4Result(UserOrderListActivity.this, order);
                        } else {
                            OrderDetailsActivity.startActivity(UserOrderListActivity.this, order);
                        }
                    }
                });
			}
			
			return itemView;
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
