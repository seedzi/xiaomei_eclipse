package com.xiaomei.yanyu.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.api.http.StringPostRequest;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order.DataDetail;
import com.xiaomei.yanyu.bean.Order.DataDetail.OrderInfo;
import com.xiaomei.yanyu.bean.Order.DataList;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.center.OrderDetailsActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.IntentExtra;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

public class ResultActivity extends Activity implements OnDateSetListener {

    private static final String ACTION_SHOW_PAYMENT = "action_show_payment";

    private static final String EXTRA_RESULT = "extra_result";

    public static void showPaymentResult(Activity activity, Order order, boolean successful) {
        activity.startActivity(new Intent(activity, ResultActivity.class)
                .setAction(ACTION_SHOW_PAYMENT)
                .putExtra(IntentExtra.EXTRA_ORDER, order)
                .putExtra(EXTRA_RESULT, successful));
        UiUtil.overridePendingTransition(activity);
    }

    private RequestQueue mQueue;
    private Order mOrder;
    private long mPreserveDate;

    private TitleActionBar mTitleActionBar;
    private View mResultSignage;
    private TextView mResultTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTitleActionBar = new TitleActionBar(getActionBar());
        mResultSignage = findViewById(R.id.result_signage);
        mResultTitle = (TextView)mResultSignage.findViewById(R.id.result_title);

        mQueue = XiaoMeiApplication.getInstance().getQueue();

        String action = getIntent().getAction();
        if (action.equals(ACTION_SHOW_PAYMENT)) {
            showPaymentResult(getIntent().getBooleanExtra(EXTRA_RESULT, false));
        }
        mOrder = (Order)getIntent().getSerializableExtra(IntentExtra.EXTRA_ORDER);
    }

    private void showPaymentResult(boolean isSuccessful) {
        mTitleActionBar.setTitle(R.string.payment_success);
        mResultSignage.setBackgroundResource(R.drawable.payment_success_signage);
        mResultTitle.setText(R.string.payment_success);
        showFragment(new PaymentResultFragment());
    }

    private void showPreserveResult(boolean isSuccessful) {
        mTitleActionBar.setTitle(R.string.preserve_success);
        mResultSignage.setBackgroundResource(R.drawable.preserve_success_signage);
        mResultTitle.setText(R.string.preserve_success);
        showFragment(new PreserveResultFragment());
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_pane, fragment).commit();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mPreserveDate = DateUtils.getTimeInMillis(year, monthOfYear, dayOfMonth);
        mQueue.add(new StringPostRequest(HttpUrlManager.COUPON_ORDER,
                getUpdateParams(mPreserveDate),
                mUpdateListener, mUpdateErrorListener));
    }

    private Map<String, String> getUpdateParams(long preserveDate) {
        HashMap<String, String> params = new HashMap<String, String>();
        DataList dataList = mOrder.getDataList();
        DataDetail dataDetail = mOrder.getDataDetail();
        User user = UserUtil.getUser();
        params.put(HttpUtil.QUERY_USERID, user.getUserInfo().getUserid());
        params.put(HttpUtil.QUERY_GOODS_ID, dataList.getGoodsId());
        params.put(HttpUtil.QUERY_ORDERID, dataList.getId());
        params.put(HttpUtil.QUERY_SUBDATE, DateUtils.formatQueryParameter(preserveDate));
        params.put(HttpUtil.QUERY_MOBILE, dataDetail.findOrderInfo(OrderInfo.TYPE_MOBILE).getValue());
        params.put(HttpUtil.QUERY_PASSPORT, dataDetail.findOrderInfo(OrderInfo.TYPE_PASSPORT).getValue());
        params.put(HttpUtil.QUERY_COUPONID, dataDetail.findOrderInfo(OrderInfo.TYPE_COUPON_ID).getValue());
        params.put(HttpUtil.QUERY_ACTION, HttpUtil.ACTION_UPDATE);
        params.put(HttpUtil.QUERY_TOKEN, user.getToken());
        params.put(HttpUtil.QUERY_UPTIME, DateUtils.formatQueryParameter(System.currentTimeMillis()));
        return HttpUtil.signParams(params);
    }

    private Listener<String> mUpdateListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            if (response != null) {
                BizResult res = new Gson().fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    showPreserveResult(true);
                    return;
                }
            }
            UiUtil.showToast(ResultActivity.this, "预约失败！");
        }
    };

    private ErrorListener mUpdateErrorListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError arg0) {
            UiUtil.showToast(ResultActivity.this, "预约失败！");
        }
    };

    private class PaymentResultFragment extends Fragment implements OnClickListener {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_payment_result, container, false);
            root.findViewById(R.id.preserve).setOnClickListener(this);
            root.findViewById(R.id.consult_online).setOnClickListener(this);
            return root;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.preserve:
                    Calendar calendar = Calendar.getInstance();
                    new DatePickerDialog(ResultActivity.this, ResultActivity.this,
                            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.consult_online:
                    GoodsDetailActivity.startActivityWithTitle(ResultActivity.this,
                            HttpUtil.CONSULT_URL, "咨询");
                    break;
            }
        }
    }

    private class PreserveResultFragment extends Fragment implements OnClickListener {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_preserve_result, container, false);
            TextView preserveDate = (TextView)root.findViewById(R.id.preserve_date);
            preserveDate.setText(
                    getString(R.string.info_preserve_result, DateUtils.formateDate(mPreserveDate)));
            root.findViewById(R.id.view_order).setOnClickListener(this);
            root.findViewById(R.id.view_main_page).setOnClickListener(this);
            root.findViewById(R.id.consult_online).setOnClickListener(this);
            return root;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_order:
                    OrderDetailsActivity.startActivity(ResultActivity.this, mOrder);
                    break;
                case R.id.view_main_page:
                    TabsActivity.startActivity(ResultActivity.this);
                    break;
                case R.id.consult_online:
                    GoodsDetailActivity.startActivityWithTitle(ResultActivity.this,
                            HttpUtil.CONSULT_URL, "咨询");
            }
        }

    }
}
