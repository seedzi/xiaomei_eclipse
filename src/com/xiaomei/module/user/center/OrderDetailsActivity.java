package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.bean.Order;
import com.xiaomei.bean.User;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.TitleBar;

public class OrderDetailsActivity extends BaseActiviy<UserCenterControl> implements View.OnClickListener{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		context.startActivity(intent);
	}
	
	
	public static void startActivity(Context context,String goodsId){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		 intent.putExtra("goods_id", goodsId);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlBar;
	/**产品id*/
	private String mGoodsId;
	
	private TextView mobileTv; //电话号码
	private TextView mechanismNameTv;  //机构名字3
	private TextView mechanismLocationTv; //机构地址
	private TextView goodsTitleTv; //商品名称
	private TextView goodsPriceTv; //商品价格
	private TextView goodsTypeTv; //商品类型
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_order_details_layout);
		mGoodsId = getIntent().getStringExtra("goods_id");
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
		
		mobileTv = (TextView) findViewById(R.id.mechanism_mobile);
		mechanismNameTv = (TextView) findViewById(R.id.mechanism_name);
		mechanismLocationTv = (TextView) findViewById(R.id.mechanism_location);
		goodsTitleTv = (TextView) findViewById(R.id.goods_title);
		goodsPriceTv = (TextView) findViewById(R.id.goods_price);
		goodsTypeTv = (TextView) findViewById(R.id.goods_type);
		
		View lineView = null;
		TextView titleTv  = null;
		TextView valueTv = null;
		
		lineView = findViewById(R.id.item1);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("订单号");
		lineView = findViewById(R.id.item2);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("下单日期");
		lineView = findViewById(R.id.item3);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("服务日期");
		lineView = findViewById(R.id.item4);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("客户姓名");
		lineView = findViewById(R.id.item5);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("客户电话");
		lineView = findViewById(R.id.item6);
		titleTv = (TextView) lineView.findViewById(R.id.title);
		titleTv.setText("护照号");
		
	}
	
	private void initData(){
		User user = UserUtil.getUser();
		String goodsId = mGoodsId;
		String passport = "123"; //护照 TODO
		mControl.addUserOrderAsyn(user, goodsId, passport);
	}
	
	public void addUserOrderAsynCallBack(){
		Order order = mControl.getModel().getmOrder();
		mobileTv.setText(order.getUserInfo().getMobile());
		goodsTitleTv.setText(order.getGoodsName());
		goodsPriceTv.setText(order.getGoodsPrice());
	}
	
	public void addUserOrderAsynExceptionCallBack(){
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case 1:
			
			break;

		default:
			break;
		}
	}
}
