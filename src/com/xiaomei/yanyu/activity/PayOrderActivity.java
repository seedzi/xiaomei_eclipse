package com.xiaomei.yanyu.activity;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.Payment.WeiXinPayManager;
import com.xiaomei.yanyu.Payment.ZhifubaoPayManager;
import com.xiaomei.yanyu.Payment.ZhifubaoPayManager.CallBack;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.WechatBean;
import com.xiaomei.yanyu.util.IntentExtra;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PayOrderActivity extends Activity implements OnClickListener {

    private static final int WECHAT_PAYMENT = 0;
    private static final int ALIPAY_PAYMENT = 1;

    private static final String EXTRA_MONEY = "extra_money";

    public static void startActivity(Activity activity, Order order, int payMoney) {
        activity.startActivity(new Intent(activity, PayOrderActivity.class)
                .putExtra(IntentExtra.EXTRA_ORDER, order)
                .putExtra(EXTRA_MONEY, payMoney));
        UiUtil.overridePendingTransition(activity);
    }

    private View mWechatPayment;
    private View mAlipayPayment;

    private int mPaymentSelection = WECHAT_PAYMENT;

    private Order mOrder;
    private int mPayMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrder = (Order)getIntent().getSerializableExtra(IntentExtra.EXTRA_ORDER);
        mPayMoney = getIntent().getIntExtra(EXTRA_MONEY, 0);

        setContentView(R.layout.activity_pay_order);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle("支付订单");

        TextView moneyView = (TextView)findViewById(R.id.money);
        moneyView.setText(getString(R.string.price, mPayMoney));

        mWechatPayment = findViewById(R.id.wechat_payment);
        initPaymentLayout(mWechatPayment, R.drawable.weixing, R.string.pay_weixin,
                R.string.pay_weixin_safety);
        mWechatPayment.setOnClickListener(this);

        mAlipayPayment = findViewById(R.id.alipay_payment);
        initPaymentLayout(mAlipayPayment, R.drawable.zhifubao, R.string.pay_zhifubao,
                R.string.pay_zhifubao_safety);
        mAlipayPayment.setOnClickListener(this);

        updateSelection();

        findViewById(R.id.confirm).setOnClickListener(this);
    }

    private void initPaymentLayout(View paymentLayout, int iconRes, int titleRes, int summaryRes) {
        UiUtil.findImageViewById(paymentLayout, android.R.id.icon).setImageResource(iconRes);
        UiUtil.findTextViewById(paymentLayout, android.R.id.title).setText(titleRes);
        UiUtil.findTextViewById(paymentLayout, android.R.id.summary).setText(summaryRes);
    }

    private void updateSelection() {
        mWechatPayment.setActivated(mPaymentSelection == WECHAT_PAYMENT);
        mAlipayPayment.setActivated(mPaymentSelection == ALIPAY_PAYMENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_payment:
                mPaymentSelection = WECHAT_PAYMENT;
                updateSelection();
                break;
            case R.id.alipay_payment:
                mPaymentSelection = ALIPAY_PAYMENT;
                updateSelection();
                break;
            case R.id.confirm:
                pay();
                break;
        }
    }

    private void pay() {
        switch (mPaymentSelection) {
            case WECHAT_PAYMENT:
                new WechatPayTask().execute();
                break;
            case ALIPAY_PAYMENT:
                ZhifubaoPayManager.getInstance().setCurrentActivity(this);
                ZhifubaoPayManager.getInstance().setCallBack(new CallBack() {
                    @Override
                    public void successCallBack() {
                        ResultActivity.showPaymentResult(PayOrderActivity.this, mOrder, true);
                        dismissDialog();
                    }

                    @Override
                    public void failureCallBack() {
                        dismissDialog();
                    }
                });
                ZhifubaoPayManager.getInstance().pay(mOrder.getDataList().getGoodsName(),
                        mOrder.getDataList().getGoodsName(), String.valueOf(mPayMoney),
                        mOrder.getDataList().getId());
                showProgressDialog("支付中……");
                break;
        }
    }

    // =========================================== ProgressDialog ==========================================

    private ProgressDialog mProgressDialog;

    private void showProgressDialog(String message){
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    
    private void dismissDialog(){
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    private class WechatPayTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected void onPreExecute() {
            showProgressDialog("支付中……");
        }

        @Override
        protected Object doInBackground(Void... params) {
            try {
                return XiaoMeiApplication.getInstance().getApi().payWechat(
                        mOrder.getDataList().getId(), UserUtil.getUser().getToken());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            WechatBean bean = (WechatBean)result;
            if (bean != null) {
                WeiXinPayManager.getInstance().pay(bean, PayOrderActivity.this);
            }
            dismissDialog();
        }
    }
}
