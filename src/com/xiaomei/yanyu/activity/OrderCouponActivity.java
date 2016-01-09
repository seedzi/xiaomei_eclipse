package com.xiaomei.yanyu.activity;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.adapter.ActionCouponAdapter;
import com.xiaomei.yanyu.adapter.CouponAdapter;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.util.Security;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderCouponActivity extends Activity {

    public static final int REQUEST_COUPON = 0;

    public static void startActivity(Activity ac, String couponId) {
        ac.startActivity(new Intent(ac, OrderCouponActivity.class).putExtra("couponId", couponId));
        UiUtil.overridePendingTransition(ac);
    }
    
    public static void startActivity4Result(Activity ac, String couponId,ArrayList<Coupon> data) {
    	Intent intent = new Intent(ac, OrderCouponActivity.class);
    	intent.putExtra("couponId", couponId);
    	intent.putExtra("data", data);
        ac.startActivityForResult(intent, REQUEST_COUPON);
        UiUtil.overridePendingTransition(ac);
    }
    
    /*vollery请求队列*/
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = XiaoMeiApplication.getInstance().getQueue();
        setContentView(R.layout.activity_order_coupon);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_order_coupon);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CouponPagerAdapter(getFragmentManager()));
        PageIndicator indicator = (PageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    private class CouponPagerAdapter extends FragmentPagerAdapter {

        public CouponPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getString(R.string.coupon) : getString(R.string.coupon_code);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new CouponFragment() : new CouponCodeFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    /**
     * @author sunbreak 优惠券页面
     */
    private class CouponFragment extends Fragment implements OnItemClickListener{

        private CouponAdapter mCouponAdapter;
        private ListView mListView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mCouponAdapter = new ActionCouponAdapter(getActivity());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.list, container, false);
            mListView = (ListView)root.findViewById(android.R.id.list);
            mListView.setAdapter(mCouponAdapter);
            mListView.setOnItemClickListener(this);
            return root;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            initData();
            mListView.post(new Runnable() {
                @Override
                public void run() {
                    int firstVisiblePosition = mListView.getFirstVisiblePosition();
                    mListView.setItemChecked(firstVisiblePosition, true);
                }
            });
        }
        private void initData(){
            mCouponAdapter.clear();
            mCouponAdapter.addAll((ArrayList<Coupon>)getIntent().getSerializableExtra("data"));
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            String couponid = mCouponAdapter.getItem(position).id;
            String discount = mCouponAdapter.getItem(position).discount;
            getResult2Back(couponid, discount);
        }
    }

    private void getResult2Back(String couponid,String discount ){
        Intent intent = new Intent();  
        
        intent.putExtra("couponid", couponid);
        intent.putExtra("discount",discount);
        setResult(RESULT_OK, intent);  
        finish();
    }
    
    /**
     * @author sunbreak 优惠码页面
     */
    private class CouponCodeFragment extends Fragment implements TextWatcher, OnClickListener {

        private EditText mInput;
        private Button mConfirm;
        private TextView mInfo;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.order_coupon_code, container, false);
            mInput = (EditText)root.findViewById(R.id.input);
            mConfirm = (Button)root.findViewById(R.id.confirm);
            mInfo = (TextView)root.findViewById(R.id.info);

            mInput.addTextChangedListener(this);
            mConfirm.setEnabled(!TextUtils.isEmpty(mInput.getText()));
            mConfirm.setOnClickListener(this);
            return root;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    // TODO 检查优惠码可用
                	if(TextUtils.isEmpty(mInput.getText().toString())){
                		Toast.makeText(OrderCouponActivity.this, "优惠卷不能为空！", 0).show();
                		return;
                	}
                	mQueue.add(new StringRequest(Request.Method.GET,addCouponUrl(mInput.getText().toString()), mListener, mErroListener));
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mConfirm.setEnabled(!TextUtils.isEmpty(s));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
        private Listener<String> mListener = new Listener<String>() {
            @Override
            public void onResponse(String response) {
            	BizResult res = null;
                try {
                    Gson gson = new Gson();
                    res = gson.fromJson(response, BizResult.class);
                    if (res.isSuccess()) {
                    	Coupon coupon = gson.fromJson(res.getMessage(), Coupon.class);
                        getResult2Back(coupon.couponid, coupon.discount);
                        return;
                    } 
                } catch (Exception e) {
                }
                String msg = res.getMessage().toString();
                if(TextUtils.isEmpty(msg)){
                	msg = "添加优惠码失败";
                }
                msg.replaceAll("\"", "");
            	Toast.makeText(OrderCouponActivity.this, msg, 0).show();
            	mConfirm.setEnabled(false);
            }
        };
        private ErrorListener mErroListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
            	Toast.makeText(OrderCouponActivity.this, "添加优惠码失败", 0).show();
            	mConfirm.setEnabled(false);
            }
        };

    }
    
    
    // ============================================================================================
    // url
    // ============================================================================================
    /**
     * 获取新增优惠卷的入口
     */
    private String addCouponUrl(String code){
        String time = String.valueOf(System.currentTimeMillis()/1000);
        BasicNameValuePair[] values = {
                new BasicNameValuePair(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken()),
                new BasicNameValuePair(HttpUtil.QUERY_UPTIME,time ),
                new BasicNameValuePair(HttpUtil.QUERY_CODE, code)} ; 
        return Uri.parse(HttpUrlManager.addPreferentialVolumeUrl()).buildUpon()
                .appendQueryParameter(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken())
                .appendQueryParameter(HttpUtil.QUERY_UPTIME, time)
                .appendQueryParameter(HttpUtil.QUERY_CODE, code)
                .appendQueryParameter(HttpUtil.QUERY_FIG, Security.get32MD5Str(values))
                .build().toString();
    }
}
