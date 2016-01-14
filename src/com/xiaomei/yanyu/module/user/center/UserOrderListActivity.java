package com.xiaomei.yanyu.module.user.center;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.activity.PayOrderActivity;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserOrderListActivity extends Activity
        implements OnRefreshListener<ListView>, OnItemClickListener {
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,UserOrderListActivity.class);
		ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
	}
	
    private TitleActionBar mTitleActionBar;
	
	private PullToRefreshListView mPullView;
	
    private ListView mListView;

	private OrderAdapter mAdapter;
	
	private View mEmptyView;

    private View mLoadingView;
	
    private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_layout);
		setUpView();

        mQueue = XiaoMeiApplication.getInstance().getQueue();

        mPullView.setRefreshing(false);
        showLoading();
	}
	
    @Override
	public void onResume() {
		super.onResume();
        if(OrderDetailsActivity.STATE_CHANGED){
            mPullView.setRefreshing();
        	OrderDetailsActivity.STATE_CHANGED = false;
        }
	}
	
	private void setUpView(){
        mTitleActionBar = new TitleActionBar(getActionBar());
        mTitleActionBar.setTitle(R.string.user_order);
		
		mPullView = (PullToRefreshListView) findViewById(R.id.list);
        mPullView.setOnRefreshListener(this);
        mListView = mPullView.getRefreshableView();
        mListView.setOnItemClickListener(this);
		mAdapter = new OrderAdapter(this);
        mListView.setAdapter(mAdapter);
        mPullView.setEmptyView(findViewById(R.id.empty_layout));
		
		mEmptyView= findViewById(R.id.empty_view);
        mLoadingView = findViewById(R.id.loading_view);
	}
	
    // 仅在首次加载时显示
    private void showLoading() {
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mEmptyView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Order order = mAdapter.getItem(position - mListView.getHeaderViewsCount());
        OrderDetailsActivity.startActivity(UserOrderListActivity.this, order);
    }

    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        mQueue.add(
                new StringRequest(formatObjectListUrl(), mRefreshListener, mRefreshErroListener));
    }

    private String formatObjectListUrl() {
        User user = UserUtil.getUser();
        Map<String, String> params = HttpUtil.queryBuilder()
                .put(HttpUtil.QUERY_USERID, user.getUserInfo().getUserid())
                .put(HttpUtil.QUERY_TOKEN, user.getToken())
                .put(HttpUtil.QUERY_UPTIME, DateUtils.formatQueryParameter(System.currentTimeMillis()))
                .build();
        return HttpUtil.buildUri(HttpUrlManager.MY_COUPON_ORDER, HttpUtil.signParams(params))
                .toString();
    }

    private Listener<String> mRefreshListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    Order[] orders = gson.fromJson(res.getMessage(), Order[].class);
                    if (orders != null) {
                        mAdapter.clear();
                        mAdapter.addAll(orders);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            mPullView.onRefreshComplete();
            hideLoading();
        }
    };

    private ErrorListener mRefreshErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError arg0) {
            mPullView.onRefreshComplete();
            hideLoading();
        }
    };

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
				
                int status = dataList.getStatus();
				UiUtil.findTextViewById(itemView, R.id.status).setText(dataList.getStatusText());
                String payString = status == Order.STATUS_NO_PAY ? "立即付款"
                        : status == Order.STATUS_FINISH ? "去评论" : "订单详情";
				int background = status == 1 || status == 4 ? R.drawable.payment_selector : R.drawable.payment_over;
				TextView payButton = UiUtil.findTextViewById(itemView, R.id.pay_button);
                payButton.setText(payString);
                payButton.setBackgroundResource(background);
                
                payButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (order.getDataList().getStatus() == Order.STATUS_FINISH) {
                            CommentsActivity.startActivity4Result(UserOrderListActivity.this, order);
                        } else {
                            // TODO 计算优惠券的金额
                            PayOrderActivity.startActivity(UserOrderListActivity.this, order,
                                    Integer.valueOf(order.getDataList().getGoodsPay()));
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
                    mPullView.setRefreshing();
			}
			break;

		default:
			break;
		}
	}
}
