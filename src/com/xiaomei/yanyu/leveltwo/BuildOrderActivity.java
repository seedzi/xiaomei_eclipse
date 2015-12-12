package com.xiaomei.yanyu.leveltwo;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.Payment.PayUtils;
import com.xiaomei.yanyu.activity.OrderCouponActivity;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 生成订单页
 * @author huzhi
 */
public class BuildOrderActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{
    

    public static void startActivity(Activity ac,String goodsId){
        Intent intent = new Intent(ac,BuildOrderActivity.class);
        intent.putExtra("goods_id", goodsId);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_out_no_change, R.anim.activity_slid_in_from_right);
    }
    
    private ImageView iconIv; 
    private TextView titleTv;
    private TextView hospitalTv;
    private TextView localTv;
    private TextView priceTv;
    private TextView priceMarketTv ;
    private View buildOrder;
    private View itemGoodsLayout;
    
    private String goodsId; //产品id
    private TitleBar mTitlebar;
    
    private EditText mUsername;
    private EditText mUserMobile;
    private EditText mUserPassport;
    
    private View mDiscountLayout;
    
    private TextView mDiscountMoneyTxt;
    private TextView merchantMobile;
    
    private View commitOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_layout);
        setupView();
        initData();
    }

    private void setupView(){
        mTitlebar = (TitleBar) findViewById(R.id.titlebar);
        mTitlebar.setTitle("生成订单");
        mTitlebar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitlebar.findViewById(R.id.right_root).setVisibility(View.GONE);
        
        itemGoodsLayout = findViewById(R.id.item_goods_layout);
        iconIv = (ImageView)itemGoodsLayout.findViewById(R.id.goods_icon);
        titleTv = (TextView)itemGoodsLayout.findViewById(R.id.goods_name);
        hospitalTv = (TextView)findViewById(R.id.merchant_name);
        localTv = (TextView)findViewById(R.id.merchant_location);
        priceTv = (TextView)itemGoodsLayout.findViewById(R.id.order_amount);
        priceMarketTv = (TextView) findViewById(R.id.origin_price);
        
        TextView title = (TextView) findViewById(R.id.item1).findViewById(R.id.title);     
        title.setText("客户姓名");
        title = (TextView) findViewById(R.id.item2).findViewById(R.id.title);     
        title.setText("客户电话");
        title = (TextView) findViewById(R.id.item3).findViewById(R.id.title);     
        title.setText("护照号");
        mUsername = (EditText) findViewById(R.id.item1).findViewById(R.id.value);     
        mUserMobile = (EditText) findViewById(R.id.item2).findViewById(R.id.value);     
        mUserPassport = (EditText) findViewById(R.id.item3).findViewById(R.id.value);     
        
        goodsId = getIntent().getStringExtra("goods_id");
        
        mDiscountLayout = findViewById(R.id.discount_layout);
        mDiscountLayout.setOnClickListener(this);
        mDiscountMoneyTxt = (TextView) findViewById(R.id.discount_money_txt);
        merchantMobile = (TextView) findViewById(R.id.merchant_mobile);
        
        commitOrder = findViewById(R.id.commit_order);
        commitOrder.setOnClickListener(this);
    }
    
    private void initData(){
        mControl.getGoodsFromNetAsyn(goodsId);
    }

    // =========================================== CallBack =====================================================
    public void getGoodsFromNetAsynCallback(){
        UserInfo userInfo = UserUtil.getUser().getUserInfo();
        mUsername.setText(userInfo.getUsername());
        mUserMobile.setText(userInfo.getMobile());
        mUserPassport.setText(userInfo.getIdcard());
        
        Goods goods = mControl.getModel().getGoods();
        
        ImageLoader.getInstance().displayImage(goods.getFileUrl(),iconIv );
        titleTv .setText(goods.getTitle());
        hospitalTv.setText(goods.getHospName());
        priceTv.setText(getResources().getString(R.string.ren_ming_bi)+" "+ goods.getPriceXm());
        localTv.setText(goods.getCityName());
        priceMarketTv.setText("原价"+goods.getPriceMarket()+"元");
        merchantMobile.setText(goods.getHospTel());
        
        List<Goods.Mark> marks = goods.getMarks();
        int i = 0;
        GradientDrawable shapeDrawable  = null;
        if(marks!=null){
        	/*
            for(Goods.Mark mark:marks){
                switch (i) {
                case 0:
                    mark1.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark1.setBackground(shapeDrawable);
                    mark1.setText(mark.getLabel());
                    break;
                case 1:
                    mark2.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark2.setBackground(shapeDrawable);
                    mark2.setText(mark.getLabel());
                    break;
                case 2:
                    mark3.setVisibility(View.VISIBLE);
                    shapeDrawable = new GradientDrawable();
                    shapeDrawable.setCornerRadius(15);
                    shapeDrawable.setColor(Color.parseColor(mark.getColor()));
                    mark3.setBackground(shapeDrawable);
                    mark3.setText(mark.getLabel());
                    break;
                default:
                    break;
                }
                i++;
            }*/
        }
        
        if(goods.getAvailCoupons()==null || goods.getAvailCoupons().size()==0){
        	mDiscountMoneyTxt.setText("无优惠卷可用，请填写优惠码");
        	mDiscountMoneyTxt.setTextColor(Color.parseColor("#ffffff"));
        }else{
        	mDiscountMoneyTxt.setText("有"+goods.getAvailCoupons().size() + "张优惠卷可用");
        	mDiscountMoneyTxt.setTextColor(Color.parseColor("#d366f3"));
        }
    }

    public void getGoodsFromNetAsynExceptionCallback(){
        
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.discount_layout:
         	//优惠卷
            OrderCouponActivity.startActivity4Result(this, "",(ArrayList)mControl.getModel().getGoods().getAvailCoupons());
            break;
        case R.id.commit_order:
            if(!PayUtils.checkoutInputData(mUsername.getText().toString(),
                    mUserMobile.getText().toString(), 
                    mUserPassport.getText().toString())){
                Toast.makeText(this, "请您完整的输入您的信息", 0).show();
                return;
            }
            OrderDetailsActivity.startActivity(this, goodsId, mUsername
                    .getText().toString(), mUserMobile.getText().toString(),
                    mUserPassport.getText().toString());
            finish();
        	break;
        default:
            break;
        }
    }
    
    // =========================================== 业务 =====================================================
    private String mCouponid = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            mCouponid = data.getStringExtra("couponid");
            String discount = data.getStringExtra("discount");
            mDiscountMoneyTxt.setText(discount);
        }
    }
}
