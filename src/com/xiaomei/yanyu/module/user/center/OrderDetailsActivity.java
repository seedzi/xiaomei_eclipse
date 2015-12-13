package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.activity.PayOrderActivity;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 订单详情页
 * @author huzhi
 */
public class OrderDetailsActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{
	
	public static boolean STATE_CHANGED = false;
	
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
	public static void startActivity(Activity ac,String goodsId,String username,String mobile,String passport){
		Intent intent = new Intent(ac,OrderDetailsActivity.class);
		 intent.putExtra("goods_id", goodsId);
	      intent.putExtra("username", username);
	      intent.putExtra("mobile", mobile);
	      intent.putExtra("passport", passport);
		 ac.startActivity(intent);
		 ac.overridePendingTransition(R.anim.activity_slid_out_no_change, R.anim.activity_slid_in_from_right);
	}
	
	private TitleBar mTitlBar;
	
	private TextView mobileTv; //电话号码
	private TextView merchantNameTv;  //机构名字
	private TextView merchantLocationTv; //机构地址
	private TextView goodsTitleTv; //商品名称
	private TextView goodsPriceTv; //商品价格
	private TextView goodsTypeTv; //商品类型
	private ImageView goodsIconIv; //产品icon
	
	private EditText orderNameEd; //客户姓名
	private EditText orderMobile; //客户电话
	private EditText orderPassport; //客户护照

    private Button mActionButton;
//	private Order order;
//	private Order.DataDetail.GoodsInfo goodsInfo ;
	
	private View rootView;
	private View mLoadingView; 
	
	private String goodsId; //产品id
	private String passport;//护照
	private String username; // 姓名
	private String mobile;//手机号
	
	// =============================================================================================
	/**未付款*/
	private final int ORDER_NO_PAY = 1;
	/**已付款*/
	private final int ORDER_FINISH_PAY = 2;
	/**退款申请中*/
	private final int ORDER_CANCEL = 3;
	/**交易完成*/
	private final int ORDER_FINISH = 4;
	/**评论完成*/
	private final int ORDER_COMMENT_FINISH = 5;
	/**退款已完成*/
	private final int ORDER_CANEL_FINISH = 6;
	
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
		
		mobileTv = (TextView) findViewById(R.id.merchant_mobile);
		merchantNameTv = (TextView) findViewById(R.id.merchant_name);
		merchantLocationTv = (TextView) findViewById(R.id.merchant_location);
		goodsTitleTv = (TextView) findViewById(R.id.goods_title);
		goodsPriceTv = (TextView) findViewById(R.id.goods_price);
		goodsTypeTv = (TextView) findViewById(R.id.goods_type);
		goodsIconIv = (ImageView) findViewById(R.id.goods_icon);
		
		orderNameEd = (EditText) findViewById(R.id.item4).findViewById(R.id.value);
		orderMobile = (EditText) findViewById(R.id.item5).findViewById(R.id.value);
		orderPassport = (EditText) findViewById(R.id.item6).findViewById(R.id.value);

        mActionButton = (Button)findViewById(R.id.action_button);
        mActionButton.setText("立即付款");
        mActionButton.setOnClickListener(this);
	}
	
	private int[] res = new int[]{R.id.item1,R.id.item2,R.id.item3,R.id.item4,R.id.item5,R.id.item6};
	
	private void initData(){
		Order order = mControl.getModel().getOrder();
        Button actionButton = (Button)findViewById(R.id.action);
	    if(order!=null){   //我的订单页进入
	    	android.util.Log.d("111", "我的订单页进入");
	        goodsId = order.getDataList().getGoodsId();
	        attachData2UI(order);
	        setStatus(order);
	    }else{ //产品页进入
	    	android.util.Log.d("111", "产品页进入");
	        goodsId = getIntent().getStringExtra("goods_id");
	        username = getIntent().getStringExtra("username");
	        mobile = getIntent().getStringExtra("mobile");
	        passport = getIntent().getStringExtra("passport");
	        mControl.addUserOrderAsyn(UserUtil.getUser(), goodsId, username,mobile,passport);
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
        goodsPriceTv.setText(getResources().getString(R.string.ren_ming_bi) + " " + orderDataList.getGoodsPay());
        ImageLoader.getInstance().displayImage(goodsInfo.getGoodsImg(), goodsIconIv);
        Order.DataDetail.HospInfo hospInfo = orderDataDetail.getHospInfo();
        merchantNameTv.setText(hospInfo.getHospName());
        merchantLocationTv.setText(hospInfo.getAddr());
        mobileTv.setText(hospInfo.getTel());
        
        List<Order.DataDetail.OrderInfo> orderInfos = orderDataDetail.getOrderInfos();
        int i = 0;
        for(Order.DataDetail.OrderInfo info:orderInfos){
        	if(i<3){
        		initItem((ViewGroup)findViewById(res[i]), info,false,i);
        	}else{
        		initItem((ViewGroup)findViewById(res[i]), info,true,i);
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
			tv.setText("申请退款");
			tv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showCancelOrderDialog();
					// 临时测试
//					CommentsActivity.startActivity(OrderDetailsActivity.this,mControl.getModel().getOrder());
				}
			});
			setEditEnable(false);
			break;
		case ORDER_CANCEL:
			hidePay();
			tv.setText("退款审核中");
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
			findViewById(R.id.order_status).setVisibility(View.GONE);
			break;
		case ORDER_CANEL_FINISH:
			hidePay();
			tv.setText("退款完成");
			setEditEnable(false);
			break;
		default:
			break;
		}
	}
	
	private void hidePay(){
		findViewById(R.id.order_status).setVisibility(View.VISIBLE);
	}
	
	private void showPay(){
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
		tv.setText("退款审核中");
		setEditEnable(false);
		Toast.makeText(this, "退款审核中", 0).show();
		STATE_CHANGED = true;
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
	
	private void initItem(ViewGroup viewItem , Order.DataDetail.OrderInfo info,boolean enable,int i){
		android.util.Log.d("111", "info = "+info);
		TextView title = (TextView) viewItem.findViewById(R.id.title);
		EditText value = (EditText) viewItem.findViewById(R.id.value);
		title.setText(info.getTitle());
		value.setEnabled(enable);
		
		if(i==3){ //客户姓名
			if(TextUtils.isEmpty(info.getValue())){
				value.setHint("请输入姓名");
			}else{
				value.setText(info.getValue());
			}
		}else if(i==4){ //客户电话
			if(TextUtils.isEmpty(info.getValue())||"0".equals(info.getValue())){
				value.setHint("请输入电话");
			}else{
				value.setText(info.getValue());
			}
		}else if(i==5){ //护照号
			if(TextUtils.isEmpty(info.getValue())||"0".equals(info.getValue())){
				value.setHint("请输入护照号");
			}else{
				value.setText(info.getValue());
			}
		}else{
			value.setText(info.getValue());
		}
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
                PayOrderActivity.startActivity(this, mControl.getModel().getOrder());
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
	
	private void showCancelOrderDialog(){
		AlertDialog.Builder builder = new Builder(this);
        builder.setMessage("您确认申请退款吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mControl.cancelUserOrderUrl(mControl.getModel().getOrder().getDataList().getId()); 
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

	}
}
