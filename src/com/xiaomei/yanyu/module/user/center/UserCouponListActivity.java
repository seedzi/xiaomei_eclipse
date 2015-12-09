package com.xiaomei.yanyu.module.user.center;

import org.apache.http.message.BasicNameValuePair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.adapter.CouponAdapter;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.util.Security;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UserCouponListActivity extends Activity
        implements OnClickListener, OnItemClickListener {

    public static void startActivity(Activity ac) {
        Intent intent = new Intent(ac, UserCouponListActivity.class);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right,
                R.anim.activity_slid_out_no_change);
    }

    private CouponAdapter mAdapter;

    private TitleActionBar mTitleBar;

    private PullToRefreshListView mPullView;

    private View mCheckLayout;

    /*vollery请求队列*/
    private RequestQueue mQueue;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_coupon_list);

        mTitleBar = new TitleActionBar(getActionBar());
        mTitleBar.setTitle(R.string.user_coupon);
        mTitleBar.setTextAction(R.string.check_coupon);
        mTitleBar.setOnActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleBar.setActionVisibility(View.GONE);
                mCheckLayout.setVisibility(View.VISIBLE);
            }
        });

        mPullView = (PullToRefreshListView)findViewById(R.id.list);
        mListView = mPullView.getRefreshableView();
        mAdapter = new CouponAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        View emptyView = findViewById(R.id.empty);
        mListView.setEmptyView(emptyView);

        mCheckLayout = findViewById(R.id.check_coupon_layout);
        View checkButton = mCheckLayout.findViewById(R.id.check_coupon);
        checkButton.setOnClickListener(this);

        mAdapter.add(new Coupon());
        mAdapter.add(new Coupon());
        mAdapter.add(new Coupon());
        mQueue = XiaoMeiApplication.getInstance().getQueue();
        
        initData();
    }

    private Listener<String> mRefreshListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                android.util.Log.d("aaa", "response = " + response.toString());
                Gson gson = new Gson();
                BizResult res = gson.fromJson(response, BizResult.class);
                if (res.isSuccess()) {
                    mAdapter.clear();
                    mAdapter.addAll(gson.fromJson(res.getMessage(), Coupon[].class));
                } 
            } catch (Exception e) {
                // TODO: handle exception
            }
            mPullView.onRefreshComplete();
        }
    };

    private ErrorListener mRefreshErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError arg0) {
            mPullView.onRefreshComplete();
        }
    };
    
    private void initData(){
        mQueue.add(new StringRequest(Request.Method.GET,getListUrl(), mRefreshListener, mRefreshErroListener));
    }
    private String getListUrl() {
        String time = String.valueOf(System.currentTimeMillis()/1000);
        BasicNameValuePair[] values = {
                new BasicNameValuePair(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken()),
                new BasicNameValuePair(HttpUtil.QUERY_UPTIME,time )} ; 
        return Uri.parse(HttpUrlManager.userPreferentialVolumeUrl()).buildUpon()
                .appendQueryParameter(HttpUtil.QUERY_TOKEN, UserUtil.getUser().getToken())
                .appendQueryParameter(HttpUtil.QUERY_UPTIME, time)
                .appendQueryParameter(HttpUtil.QUERY_FIG, Security.get32MD5Str(values))
                .build().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_coupon:
                mTitleBar.setActionVisibility(View.VISIBLE);
                mCheckLayout.setVisibility(View.GONE);
                // TODO 兑换优惠券
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
