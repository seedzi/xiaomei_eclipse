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
import com.xiaomei.yanyu.widget.ValuePreference;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private View buildOrder;
    private View itemGoodsLayout;
    
    private String goodsId; //产品id
    private Goods goods; // 产品id

    private TitleBar mTitlebar;
    
    private ValuePreference mUsername;
    private ValuePreference mUserMobile;
    private ValuePreference mUserPassport;
    
    private View mDiscountLayout;
    
    private TextView mDiscountMoneyTxt;
    private TextView merchantMobile;
    
    private TextView mMoneyView;
    private TextView mDiscountView;
    private Button commitOrder;

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
        
        mUsername = (ValuePreference)findViewById(R.id.item1);
        mUsername.setTitle("客户姓名");
        mUserMobile = (ValuePreference)findViewById(R.id.item2);
        mUserMobile.setTitle("客户电话");
        mUserPassport = (ValuePreference)findViewById(R.id.item3);
        mUserPassport.setTitle("护照号");
        
        goodsId = getIntent().getStringExtra("goods_id");
        
        mDiscountLayout = findViewById(R.id.discount_layout);
        mDiscountLayout.setOnClickListener(this);
        mDiscountMoneyTxt = (TextView) findViewById(R.id.discount_money_txt);
        merchantMobile = (TextView) findViewById(R.id.merchant_mobile);

        View paymentLayout = findViewById(R.id.payment_info_bottom_layout);
        mMoneyView = (TextView)paymentLayout.findViewById(R.id.money);
        mDiscountView = (TextView)paymentLayout.findViewById(R.id.discount);
        commitOrder = (Button)paymentLayout.findViewById(R.id.action_button);
        commitOrder.setText(R.string.pay);
        commitOrder.setOnClickListener(this);
    }
    
    private void initData(){
        mControl.getGoodsFromNetAsyn(goodsId);
    }

    // =========================================== CallBack =====================================================
    public void getGoodsFromNetAsynCallback(){
        UserInfo userInfo = UserUtil.getUser().getUserInfo();
        mUsername.setValue(userInfo.getUsername());
        mUserMobile.setValue(userInfo.getMobile());
        mUserPassport.setValue(userInfo.getIdcard());
        
        goods = mControl.getModel().getGoods();
        
        ImageLoader.getInstance().displayImage(goods.getFileUrl(),iconIv );
        titleTv .setText(goods.getTitle());
        hospitalTv.setText(goods.getHospName());
        priceTv.setText(getString(R.string.price, Integer.valueOf(goods.getPriceXm())));
        localTv.setText(goods.getCityName());
        merchantMobile.setText(goods.getHospTel());
        mMoneyView.setText(
                getString(R.string.price, Integer.valueOf(goods.getPriceXm()) - mDiscount));
        
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
            case R.id.action_button:
                String name = mUsername.getValue().toString();
                String mobile = mUserMobile.getValue().toString();
                String passport = mUserPassport.getValue().toString();
                if (!PayUtils.checkoutInputData(name, mobile, passport)) {
                    Toast.makeText(this, "请您完整的输入您的信息", 0).show();
                    return;
                }
                OrderDetailsActivity.startActivity(this, goodsId, name, mobile, passport,
                        mCouponid);
                finish();
                break;
        default:
            break;
        }
    }
    
    // =========================================== 业务 =====================================================
    private String mCouponid = "";

    private int mDiscount;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderCouponActivity.REQUEST_COUPON && resultCode == RESULT_OK) {
            mCouponid = data.getStringExtra("couponid");
            mDiscount = Integer.valueOf(data.getStringExtra("discount"));
            mDiscountMoneyTxt.setText(String.valueOf(mDiscount));
            mDiscountView
                    .setText(mDiscount > 0 ? getString(R.string.discount_money, mDiscount) : null);
            if (goods != null) {
                mMoneyView.setText(
                        getString(R.string.price, Integer.valueOf(goods.getPriceXm()) - mDiscount));
            }
        }
    }
}
