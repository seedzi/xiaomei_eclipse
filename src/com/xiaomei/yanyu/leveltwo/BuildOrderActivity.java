package com.xiaomei.yanyu.leveltwo;

import java.util.ArrayList;
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
import com.xiaomei.yanyu.Payment.PayUtils;
import com.xiaomei.yanyu.activity.OrderCouponActivity;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.xiaomei.yanyu.widget.ValuePreference;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
public class BuildOrderActivity extends Activity implements View.OnClickListener {
    

    public static void startActivity(Activity ac,String goodsId){
        Intent intent = new Intent(ac,BuildOrderActivity.class);
        intent.putExtra("goods_id", goodsId);
        ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
    }
    
    private ImageView iconIv; 
    private TextView titleTv;
    private TextView hospitalTv;
    private TextView localTv;
    private TextView priceTv;
    private View buildOrder;
    private View itemGoodsLayout;

    private RequestQueue mQueue;

    private String goodsId; //产品id
    private Goods goods; // 产品id

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
        TitleActionBar titleActionBar = new TitleActionBar(getActionBar());
        titleActionBar.setTitle(R.string.activity_build_order);
        
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
        UserInfo userInfo = UserUtil.getUser().getUserInfo();
        mUsername.setValue(userInfo.getUsername());
        mUserMobile.setValue(userInfo.getMobile());
        mUserPassport.setValue(userInfo.getIdcard());
        
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
        mQueue = XiaoMeiApplication.getInstance().getQueue();
        mQueue.add(new StringRequest(getGoodsUrl(), mGetGoodsListenr, mGetGoodsErroListener));
    }

    private String getGoodsUrl() {
        Map<String, String> params = HttpUtil.queryBuilder()
                .put(HttpUtil.QUERY_GOODS_ID, goodsId)
                .put(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken())
                .put(HttpUtil.QUERY_UPTIME, DateUtils.formatQueryParameter(System.currentTimeMillis()))
                .build();
        return HttpUtil.buildUri(HttpUrlManager.GOODS_COUPON_INFO, HttpUtil.signParams(params)).toString();
    }

    private Listener<String> mGetGoodsListenr = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    goods = gson.fromJson(res.getMessage(), Goods.class);
                    updateGoods();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private ErrorListener mGetGoodsErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            
        }
    };

    private void updateGoods() {
        ImageLoader.getInstance().displayImage(goods.getFileUrl(), iconIv);
        titleTv .setText(goods.getTitle());
        hospitalTv.setText(goods.getHospName());
        priceTv.setText(getString(R.string.price, Integer.valueOf(goods.getPriceXm())));
        localTv.setText(goods.getCityName());
        merchantMobile.setText(goods.getHospTel());
        mMoneyView.setText(
                getString(R.string.price, Integer.valueOf(goods.getPriceXm()) - mDiscount));

        if(goods.getAvailCoupons()==null || goods.getAvailCoupons().size()==0){
            mDiscountMoneyTxt.setText("无优惠卷可用，请填写优惠码");
            mDiscountMoneyTxt.setTextColor(Color.parseColor("#ffffff"));
        }else{
            mDiscountMoneyTxt.setText("有" + goods.getAvailCoupons().size() + "张优惠卷可用");
            mDiscountMoneyTxt.setTextColor(Color.parseColor("#d366f3"));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.discount_layout:
         	//优惠卷
                OrderCouponActivity.startActivity4Result(this, "",
                        (ArrayList)goods.getAvailCoupons());
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
