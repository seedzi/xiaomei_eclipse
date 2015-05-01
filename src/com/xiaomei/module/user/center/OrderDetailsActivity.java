package com.xiaomei.module.user.center;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.Payment.PayUtils;
import com.xiaomei.Payment.ZhifubaoPayManager;
import com.xiaomei.Payment.ZhifubaoPayManager.CallBack;
import com.xiaomei.bean.Order;
import com.xiaomei.bean.Order2;
import com.xiaomei.bean.User;
import com.xiaomei.comment.CommentsActivity;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.TitleBar;

public class OrderDetailsActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{
	@Deprecated
	public static void startActivity(Context context){
		Intent intent = new Intent(context,OrderDetailsActivity.class);
		context.startActivity(intent);
	}
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
	/**
	 * 产品页面进入
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
	
	private EditText orderNameEd; //客户姓名
	private EditText orderMobile; //客户电话
	private EditText orderPassport; //客户护照
//	private Order order;
//	private Order.DataDetail.GoodsInfo goodsInfo ;
	
	private View rootView;
	private View mLoadingView; 
	
	private String goodsId; //产品id
	
	private ProgressDialog mProgressDialog;
	
	// =============================================================================================
	/**未付款*/
	private final int ORDER_NO_PAY = 1;
	/**已付款*/
	private final int ORDER_FINISH_PAY = 2;
	/**已取消*/
	private final int ORDER_CANCEL = 3;
	/**交易完成*/
	private final int ORDER_FINISH = 4;
	/**评论完成*/
	private final int ORDER_COMMENT_FINISH = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Order order = (Order) getIntent().getSerializableExtra("order");
		mControl.getModel().setOrder(order);
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
		
		orderNameEd = (EditText) findViewById(R.id.item4).findViewById(R.id.value);
		orderMobile = (EditText) findViewById(R.id.item5).findViewById(R.id.value);
		orderPassport = (EditText) findViewById(R.id.item6).findViewById(R.id.value);
	}
	
	private int[] res = new int[]{R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5,R.id.item6};
	
	private void initData(){
		Order order = mControl.getModel().getOrder();
	    if(order!=null){   //我的订单页进入
	        goodsId = order.getDataList().getGoodsId();
	        attachData2UI(order);
	        setStatus(order);
	    }else{ //产品页进入
	        goodsId = getIntent().getStringExtra("goods_id");
	        mControl.addUserOrderAsyn(UserUtil.getUser(), goodsId, "123");
	        showProgress();
	    }
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
        goodsPriceTv.setText(orderDataList.getOrderAmount());
        ImageLoader.getInstance().displayImage(goodsInfo.getGoodsImg(), goodsIconIv);
        Order.DataDetail.HospInfo hospInfo = orderDataDetail.getHospInfo();
        mechanismNameTv.setText(hospInfo.getHospName());
        mechanismLocationTv.setText(hospInfo.getAddr());
        mobileTv.setText(hospInfo.getTel());
        
        List<Order.DataDetail.OrderInfo> orderInfos = orderDataDetail.getOrderInfos();
        int i = 0;
        for(Order.DataDetail.OrderInfo info:orderInfos){
        	if(i<3){
        		initItem((ViewGroup)findViewById(res[i]), info,false);
        	}else{
        		initItem((ViewGroup)findViewById(res[i]), info,true);
        	}
            i++;
        }
	}
	/**
	 * 根据order的状态设置数据
	 */
	private void setStatus(Order order){
		TextView tv = (TextView) findViewById(R.id.order_status);
		int status = Integer.valueOf(order.getDataList().getStatus());
		switch (status) {
		case ORDER_NO_PAY:
			showPay();
			setEditEnable(true);
			break;
		case ORDER_FINISH_PAY:
			hidePay();
			tv.setText("取消订单");
			tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mControl.cancelUserOrderUrl(mControl.getModel().getOrder().getDataList().getId()); 
					// 临时测试
//					CommentsActivity.startActivity(OrderDetailsActivity.this,mControl.getModel().getOrder());
				}
			});
			setEditEnable(false);
			break;
		case ORDER_CANCEL:
			hidePay();
			tv.setText("订单已取消");
			setEditEnable(false);
			break;
		case ORDER_FINISH:
			hidePay();
			tv.setText("去评论");
			setEditEnable(false);
			tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CommentsActivity.startActivity4Result(OrderDetailsActivity.this,mControl.getModel().getOrder());
				}
			});
			break;
		case ORDER_COMMENT_FINISH:
			hidePay();
			tv.setText("交易完成");
			setEditEnable(false);
			break;
		default:
			break;
		}
	}
	
	private void hidePay(){
		findViewById(R.id.pay_title_layout).setVisibility(View.GONE);
		findViewById(R.id.pay_divild).setVisibility(View.GONE);
		findViewById(R.id.pay_layout).setVisibility(View.GONE);
		findViewById(R.id.order_status).setVisibility(View.VISIBLE);
	}
	
	private void showPay(){
		findViewById(R.id.pay_title_layout).setVisibility(View.VISIBLE);
		findViewById(R.id.pay_divild).setVisibility(View.VISIBLE);
		findViewById(R.id.pay_layout).setVisibility(View.VISIBLE);
		findViewById(R.id.order_status).setVisibility(View.GONE);
	}
	
	private void setEditEnable(boolean enable){
		orderNameEd.setEnabled(enable);
		orderMobile.setEnabled(enable);
		orderPassport.setEnabled(enable);
	}
	
	// ====================================  CallBack =========================================================
	public void cancelUserOrderUrlCallBack(){ 
		TextView tv = (TextView) findViewById(R.id.order_status);
		tv.setText("订单已取消");
		setEditEnable(false);
		Toast.makeText(this, "订单已取消", 0).show();
	}
	
	public void cancelUserOrderUrlExceptionCallBack(){
		Toast.makeText(this, "网络异常", 0).show();
	}
	
	public void addUserOrderAsynCallBack(){
		dissProgress();
		Order order = mControl.getModel().getOrder();
		attachData2UI(order);
		setStatus(order);
	}
	private void initItem(ViewGroup viewItem , Order.DataDetail.OrderInfo info,boolean enable){
		android.util.Log.d("111", "info = "+info);
		TextView title = (TextView) viewItem.findViewById(R.id.title);
		EditText value = (EditText) viewItem.findViewById(R.id.value);
		title.setText(info.getTitle());
		value.setText(info.getValue());
		value.setEnabled(enable);
	}
	
	public void addUserOrderAsynExceptionCallBack(){
		Toast.makeText(this, "订单生成失败", 0).show();
		rootView.setVisibility(View.GONE);
	}
	
	public void updateUserOrder2ServerAsynCallBack(){
		dismissDialog();
		Order order = mControl.getModel().getOrder();
		ZhifubaoPayManager.getInstance().setCurrentActivity(this);
		ZhifubaoPayManager.getInstance().setCallBack(new CallBack() {
			@Override
			public void successCallBack() {
				setEditEnable(false);
				hidePay();
				TextView tv = (TextView) findViewById(R.id.order_status);
				tv.setText("取消订单");
			}
			@Override
			public void failureCallBack() {
			}
		});
		ZhifubaoPayManager.getInstance().pay(order.getDataList().getGoodsName(),order.getDataList().getGoodsName(),"0.01"/*order.getDataList().getOrderAmount()*/,order.getDataList().getId());
	}
	
	public void updateUserOrder2ServerAsynExceptionCallBack(){
		dismissDialog();
		Toast.makeText(this, "订单支付失败", 0).show();
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
	
	// =========================================== ProgressDialog ==========================================
	
	private void showProgressDialog(String message){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	private void dismissDialog(){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
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
		case R.id.pay_weixin:
			
			break;
		case R.id.pay_zhifubao: //支付宝支付
		    if(!PayUtils.checkoutInputData(orderNameEd.getText().toString(),
		            orderMobile.getText().toString(), 
		            orderPassport.getText().toString())){
		        Toast.makeText(this, "请您完整的输入您的信息", 0).show();
		        return;
		    }
		    showProgressDialog("订单提交中...");
		    mControl.updateUserOrder2ServerAsyn(mControl.getModel().getOrder().getDataList().getId(),orderNameEd.getText().toString(),
		            goodsId, 
		            orderPassport.getText().toString(),
		            orderMobile.getText().toString()
		            );

//		    ZhifubaoPayManager.getInstance().pay();
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if(resultCode == RESULT_OK){
				TextView tv = (TextView) findViewById(R.id.order_status);
				tv.setText("已评论");
				tv.setOnClickListener(null);
			}
			break;

		default:
			break;
		}
	}
}
