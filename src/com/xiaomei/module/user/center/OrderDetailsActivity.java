package com.xiaomei.module.user.center;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.Payment.ZhifubaoPayManager;
import com.xiaomei.bean.Order;
import com.xiaomei.bean.User;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.TitleBar;

public class OrderDetailsActivity extends BaseActiviy<UserCenterControl> implements View.OnClickListener{
	@Deprecated
	public static void startActivity(Context context){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		context.startActivity(intent);
	}
	@Deprecated
	public static void startActivity(Context context,Order order){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		intent.putExtra("order", order);
		context.startActivity(intent);
	}
	/**
	 * @param context
	 * @param goodsId  商品id
	 */
	public static void startActivity(Context context,String goodsId){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		 intent.putExtra("goods_id", goodsId);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlBar;
	
	private TextView mobileTv; //电话号码
	private TextView mechanismNameTv;  //机构名字
	private TextView mechanismLocationTv; //机构地址
	private TextView goodsTitleTv; //商品名称
	private TextView goodsPriceTv; //商品价格
	private TextView goodsTypeTv; //商品类型
	private ImageView goodsIconIv; //产品icon
	private View payWeixin;
	private View payZhifubao;
	private Order order;
	private Order.DataDetail.GoodsInfo goodsInfo ;
	
	private View rootView;
	private View mLoadingView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_details_layout);
		setUpView();
		initData();
	}
	
	private void setUpView(){
		mTitlBar = (TitleBar) findViewById(R.id.titlebar);
		mTitlBar.setTitle(getResources().getString(R.string.order_details));
		mTitlBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		rootView = findViewById(R.id.root_layout);
		mLoadingView = findViewById(R.id.loading_layout);
		
		mobileTv = (TextView) findViewById(R.id.mechanism_mobile);
		mechanismNameTv = (TextView) findViewById(R.id.mechanism_name);
		mechanismLocationTv = (TextView) findViewById(R.id.mechanism_location);
		goodsTitleTv = (TextView) findViewById(R.id.goods_title);
		goodsPriceTv = (TextView) findViewById(R.id.goods_price);
		goodsTypeTv = (TextView) findViewById(R.id.goods_type);
		goodsIconIv = (ImageView) findViewById(R.id.goods_icon);
		payWeixin = findViewById(R.id.pay_weixin);
		payWeixin.setOnClickListener(this);
		payZhifubao = findViewById(R.id.pay_zhifubao);
		payZhifubao.setOnClickListener(this);
	}
	private int[] res = new int[]{R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5,R.id.item6};
	
	private void initData(){
		String goodsId = getIntent().getStringExtra("goods_id");
		mControl.addUserOrderAsyn(UserUtil.getUser(), goodsId, "1");
		showProgress();
		/*
		order = (Order) getIntent().getSerializableExtra("order");
		android.util.Log.d("111", "order = " + order);
		Order.DataDetail orderDataDetail = order.getDataDetail();
		if(orderDataDetail ==null)
			return ;
		goodsInfo = orderDataDetail.getGoodsInfo();
		goodsTitleTv.setText(goodsInfo.getGoodsName());
		goodsPriceTv.setText(goodsInfo.getOrderAmount());
		ImageLoader.getInstance().displayImage(goodsInfo.getGoodsImg(), goodsIconIv);
		Order.DataDetail.HospInfo hospInfo = orderDataDetail.getHospInfo();
		mechanismNameTv.setText(hospInfo.getHospName());
		mechanismLocationTv.setText(hospInfo.getAddr());
		mobileTv.setText(hospInfo.getTel());
		
		List<Order.DataDetail.OrderInfo> orderInfos = orderDataDetail.getOrderInfos();
		int i = 0;
		for(Order.DataDetail.OrderInfo info:orderInfos){
			initItem((ViewGroup)findViewById(res[i]), info);
			i++;
		}
		*/
//		// feiqi
//		User user = UserUtil.getUser();
//		String goodsId = mGoodsId;
//		String passport = "123"; //护照 TODO
//		mControl.addUserOrderAsyn(user, goodsId, passport);
	}

	
	// ====================================  CallBack =========================================================
	public void addUserOrderAsynCallBack(){
		Order order = mControl.getModel().getmOrder();
		android.util.Log.d("111", "order = " + order);
		Order.DataDetail orderDataDetail = order.getDataDetail();
		if(orderDataDetail ==null)
			return ;
		goodsInfo = orderDataDetail.getGoodsInfo();
		goodsTitleTv.setText(goodsInfo.getGoodsName());
		goodsPriceTv.setText(goodsInfo.getOrderAmount());
		ImageLoader.getInstance().displayImage(goodsInfo.getGoodsImg(), goodsIconIv);
		Order.DataDetail.HospInfo hospInfo = orderDataDetail.getHospInfo();
		mechanismNameTv.setText(hospInfo.getHospName());
		mechanismLocationTv.setText(hospInfo.getAddr());
		mobileTv.setText(hospInfo.getTel());
		
		List<Order.DataDetail.OrderInfo> orderInfos = orderDataDetail.getOrderInfos();
		int i = 0;
		for(Order.DataDetail.OrderInfo info:orderInfos){
			initItem((ViewGroup)findViewById(res[i]), info);
			i++;
		}
	}
	private void initItem(ViewGroup viewItem , Order.DataDetail.OrderInfo info){
		android.util.Log.d("111", "info = "+info);
		TextView title = (TextView) viewItem.findViewById(R.id.title);
		TextView value = (TextView) viewItem.findViewById(R.id.value);
		title.setText(info.getTitle());
		value.setText(info.getValue());
		Toast.makeText(this, "订单生成成功", 0).show();
		dissProgress();
	}
	
	public void addUserOrderAsynExceptionCallBack(){
		Toast.makeText(this, "订单生成失败", 0).show();
		rootView.setVisibility(View.GONE);
	}
	
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
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.pay_weixin:
			
			break;
		case R.id.pay_zhifubao:
//			ZhifubaoPayManager.getInstance().pay(goodsInfo.getGoodsName(),goodsInfo.getGoodsName(),goodsInfo.getOrderAmount());
			ZhifubaoPayManager.getInstance().pay();
		default:
			break;
		}
	}
}
