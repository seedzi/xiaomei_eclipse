package com.xiaomei.yanyu.module.user.center;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.activity.OrderCouponActivity;
import com.xiaomei.yanyu.activity.PayOrderActivity;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.api.http.StringPostRequest;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order.DataDetail.OrderInfo;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.xiaomei.yanyu.widget.ValuePreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 订单详情页
 * @author huzhi
 */
public class OrderDetailsActivity extends Activity implements View.OnClickListener {
	
	public static boolean STATE_CHANGED = false;
	
	
	/**
	 * 我的订单页面进入
	 * @param context
	 * @param order
	 */
	public static void startActivity(Context context,Order order){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		intent.putExtra("order", order);
		context.startActivity(intent);
	}

    private static final Map<Integer, String> sStatusAction = new HashMap<Integer, String>() {
        {
            put(Order.STATUS_NO_PAY, "取消订单");
            put(Order.STATUS_FINISH_PAY, "申请退款");
            put(Order.STATUS_FINISH, "去评论");
        }
    };

    private TitleActionBar mTitleActionBar;

	private TextView mobileTv; //电话号码
	private TextView merchantNameTv;  //机构名字
	private TextView merchantLocationTv; //机构地址
	private TextView goodsTitleTv; //商品名称
	private TextView goodsPriceTv; //商品价格
	private TextView goodsTypeTv; //商品类型
	private ImageView goodsIconIv; //产品icon
	
    private ValuePreference orderPreserve; // 预约时间
    private ValuePreference orderNameEd; // 客户姓名
    private ValuePreference orderMobile; // 客户电话
    private ValuePreference orderPassport; // 客户护照
    private ValuePreference orderCoupon; // 优惠折扣

    private TextView mMoneyView;

    private TextView mDiscountView;
    private Button mActionButton;

	private View rootView;
	private View mLoadingView; 

    private RequestQueue mQueue;

    private Order mOrder;
    private Goods mGoods;
	private String passport;//护照
	private String username; // 姓名
	private String mobile;//手机号
	private String mCouponId; // 优惠券ID

    private int mPayMoney; // 支付金额
    private int mDiscountMoney; // 优惠金额

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mOrder = (Order)getIntent().getSerializableExtra("order");
		setContentView(R.layout.activity_user_order_details_layout);
		setUpView();
		initData();
	}
	
	private void setUpView(){
        mTitleActionBar = new TitleActionBar(getActionBar());
        mTitleActionBar.setTitle(R.string.order_details);
        mTitleActionBar.setActionTextColor(getResources().getColor(R.color.color_gray));
        mTitleActionBar
                .setActionTextSize(getResources().getDimensionPixelOffset(R.dimen.action_text));
		
		rootView = findViewById(R.id.root_layout);
		mLoadingView = findViewById(R.id.loading_layout);
		
		mobileTv = (TextView) findViewById(R.id.merchant_mobile);
		merchantNameTv = (TextView) findViewById(R.id.merchant_name);
		merchantLocationTv = (TextView) findViewById(R.id.merchant_location);
		goodsTitleTv = (TextView) findViewById(R.id.goods_title);
		goodsPriceTv = (TextView) findViewById(R.id.goods_price);
		goodsTypeTv = (TextView) findViewById(R.id.goods_type);
		goodsIconIv = (ImageView) findViewById(R.id.goods_icon);
		
        ViewGroup infoListLayout = (ViewGroup)findViewById(R.id.order_info_list_layout);
        LayoutInflater inflater = getLayoutInflater();
		for (OrderInfo.Type type : OrderInfo.sTypes) {
            ValuePreference preference = (ValuePreference)inflater
                    .inflate(R.layout.value_preference, infoListLayout, false);
            infoListLayout.addView(preference);
            preference.setId(type.id);
            preference.setTitle(type.title);
            if (type.hintRes != 0) {
                preference.setHint(type.hintRes);
            }
            preference.setEditable(type.editable);
            preference.setOnClickListener(this);
            switch (type) {
                case PRESERVE_DATE:
                    preference.setOnClickListener(this);
                    orderPreserve = preference;
                    break;
                case USER_NAME:
                    orderNameEd = preference;
                    break;
                case MOBILE:
                    orderMobile = preference;
                    break;
                case PASSPORT:
                    orderPassport = preference;
                    break;
                case COUPON_ID:
                    preference.setOnClickListener(this);
                    orderCoupon = preference;
                    break;
            }
        }

        mMoneyView = (TextView)findViewById(R.id.money);
        mDiscountView = (TextView)findViewById(R.id.discount);
        mActionButton = (Button)findViewById(R.id.action_button);
        mActionButton.setText(R.string.pay);
        mActionButton.setOnClickListener(this);
	}
	
	private int[] res = new int[]{R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5,R.id.item6};
	
	private void initData(){
        mQueue = XiaoMeiApplication.getInstance().getQueue();
        String url = XiaoMeiApplication.getInstance().getApi()
                .getGoodsDetailUrl(mOrder.getDataList().getGoodsId());
        mQueue.add(new StringRequest(url, mGetGoodsListenr, mErroListener));
        attachData2UI(mOrder);
        setStatus(mOrder.getDataList().getStatus());
	}

	/**
	 * 将order的数据设置到ui上
	 */
	private void attachData2UI(Order order){
        android.util.Log.d("111", "order = " + order);
        Order.DataDetail orderDataDetail = order.getDataDetail();
        Order.DataList orderDataList =  order.getDataList();
        if(orderDataDetail ==null)
            return ;
        Order.DataDetail.GoodsInfo goodsInfo = orderDataDetail.getGoodsInfo();
        goodsTitleTv.setText(goodsInfo.getGoodsName());
        goodsPriceTv.setText(getResources().getString(R.string.ren_ming_bi) + " " + orderDataList.getGoodsPay());
        ImageLoader.getInstance().displayImage(goodsInfo.getGoodsImg(), goodsIconIv);
        Order.DataDetail.HospInfo hospInfo = orderDataDetail.getHospInfo();
        merchantNameTv.setText(hospInfo.getHospName());
        merchantLocationTv.setText(hospInfo.getAddr());
        mobileTv.setText(hospInfo.getTel());
        mDiscountView.setText(
                mDiscountMoney > 0 ? getString(R.string.discount_money, mDiscountMoney) : null);
        mPayMoney = Integer.valueOf(orderDataList.getGoodsPay()) - mDiscountMoney;
        mMoneyView.setText(getString(R.string.price, mPayMoney));
        
        List<Order.DataDetail.OrderInfo> orderInfos = orderDataDetail.getOrderInfos();
        for (OrderInfo info : orderInfos) {
            ValuePreference preference = (ValuePreference)findViewById(info.getType().id);
            preference.setValue(info.getDisplayValue());
        }
	}
	/**
	 * 根据order的状态设置数据
	 */
    private void setStatus(final int status) {
        setEditEnable(status == Order.STATUS_NO_PAY);
        mTitleActionBar.setOnActionClickListener(null);
        String action = sStatusAction.get(status);
        mTitleActionBar
                .setTextAction(action != null ? action : mOrder.getDataList().getStatusText());
        mTitleActionBar.setActionVisibility(
                status == Order.STATUS_COMMENT_FINISH ? View.GONE : View.VISIBLE);
        switch (status) {
            case Order.STATUS_NO_PAY:
                mTitleActionBar.setOnActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteOrderDialog();
                    }
                });
                break;
            case Order.STATUS_FINISH_PAY:
                mTitleActionBar.setOnActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCancelOrderDialog();
                    }
                });
                break;
            case Order.STATUS_FINISH:
                mTitleActionBar.setOnActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentsActivity.startActivity4Result(OrderDetailsActivity.this, mOrder);
                    }
                });
                break;
        }
	}
	
	private void setEditEnable(boolean enable){
        orderNameEd.setEditable(enable);
        orderMobile.setEditable(enable);
        orderPassport.setEditable(enable);
        findViewById(R.id.payment_info_bottom_layout)
                .setVisibility(enable ? View.VISIBLE : View.GONE);
	}
	
    private Listener<String> mGetGoodsListenr = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    mGoods = gson.fromJson(res.getMessage(), Goods.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private ErrorListener mErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            UiUtil.postToast(OrderDetailsActivity.this, R.string.warning_network_unavailable);
        }
    };
	
	// ====================================  Progress =========================================================
	private void showProgress(){
		mLoadingView.setVisibility(View.VISIBLE);
		AnimationDrawable animationDrawable =  (AnimationDrawable) ((ImageView)mLoadingView.findViewById(R.id.iv)).getDrawable();
		if(!animationDrawable.isRunning())
			animationDrawable.start();
		rootView.setVisibility(View.GONE);
	}
	
	private void dissProgress(){
		mLoadingView.setVisibility(View.GONE);
		rootView.setVisibility(View.VISIBLE);
	}
	
	// ====================================  Pay =========================================================
	/**
	 * 1.检查用户输入
	 * 2.向服务器更新订单
	 * 3.支付
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
            case R.id.action_button:
                PayOrderActivity.startActivity(this, mOrder, mPayMoney);
                break;
            case R.id.preference_preserve_date:
                // TODO 预约
                break;
            case R.id.preference_coupon_id:
                // 优惠券
                OrderCouponActivity.startActivity4Result(this, "",(ArrayList)mGoods.getAvailCoupons());
                break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if(resultCode == RESULT_OK){
                    setStatus(Order.STATUS_COMMENT_FINISH);
			}
			break;
		    case OrderCouponActivity.REQUEST_COUPON:
		        if (resultCode == RESULT_OK) {
                    mCouponId = data.getStringExtra("couponid");
                    mDiscountMoney = Integer.valueOf(data.getStringExtra("discount"));
                    orderCoupon.setValue(String.valueOf(mDiscountMoney));
		        }
		        break;
		default:
			break;
		}
	}
	
    private void showDeleteOrderDialog() {
        new AlertDialog.Builder(this).setMessage(R.string.warning_message_delete_order)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOrder();
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.not_delete_order, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void deleteOrder() {
        String url = HttpUrlManager.DELETE_ORDER;
        Map<String, String> params = HttpUtil.queryBuilder()
                .put(HttpUtil.QUERY_ORDERID, mOrder.getDataList().getId())
                .put(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken())
                .put(HttpUtil.QUERY_UPTIME, DateUtils.formatQueryParameter(System.currentTimeMillis()))
                .build();
        mQueue.add(new StringPostRequest(url, HttpUtil.signParams(params), mDeleteOrderListener,
                mErroListener));
    }

    private Listener<String> mDeleteOrderListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    finish();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UiUtil.postToast(OrderDetailsActivity.this, R.string.error_delete_order);
        }
    };

    private void showCancelOrderDialog() {
        new AlertDialog.Builder(this).setMessage(R.string.warning_message_cancel_order)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelOrder();
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.not_cancel_order, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void cancelOrder() {
        String url = HttpUrlManager.CANCLE_ORDER;
        Map<String, String> params = HttpUtil.queryBuilder()
                .put(HttpUtil.QUERY_ORDERID, mOrder.getDataList().getId())
                .put(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken())
                .put(HttpUtil.QUERY_UPTIME, DateUtils.formatQueryParameter(System.currentTimeMillis()))
                .build();
        mQueue.add(new StringPostRequest(url, HttpUtil.signParams(params), mCancelOrderListener,
                mErroListener));
    }

    private Listener<String> mCancelOrderListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    finish();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UiUtil.postToast(OrderDetailsActivity.this, R.string.error_cancel_order);
        }
    };
}
