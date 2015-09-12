package com.xiaomei.yanyu.activity;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.android.dx.io.instructions.OneRegisterDecodedInstruction;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.umeng.socialize.net.l;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.api.util.Constant;
import com.xiaomei.yanyu.bean.Area;
import com.xiaomei.yanyu.bean.Topic;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.view.TopicAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class TopicListActivity extends Activity implements OnRefreshListener, OnLastItemVisibleListener {

    public static void startActivity(Activity ac, String title, String url) {
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
        ac.startActivity(new Intent(ac, TopicListActivity.class)
                .putExtra(IntentUtil.EXTRA_TITLE, title)
                .putExtra(IntentUtil.EXTRA_URL, url));
    }

    private static final int TOPIC_LOADER = 0;

    private String mUrl;

    private PullToRefreshListView mPullView;

    private RequestQueue mQueue;
    private TopicAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(IntentUtil.EXTRA_URL);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(intent.getStringExtra(IntentUtil.EXTRA_TITLE));

        mQueue = XiaoMeiApplication.getInstance().getQueue();

        mPullView = (PullToRefreshListView) findViewById(R.id.list);
        mPullView.setOnRefreshListener(this);
        mPullView.setOnLastItemVisibleListener(this);
        View emptyView = findViewById(R.id.empty);
        mPullView.setEmptyView(emptyView);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh(mPullView);
            }
        });

        ListView listView = mPullView.getRefreshableView();
        mListAdapter = new TopicAdapter(this);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new TopicAdapter.TopicItemClickListener());

        onRefresh(mPullView);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mQueue.add(new StringRequest(getTopicListUrl(1), mRefreshListener, mRefreshErroListener));
    }

    @Override
    public void onLastItemVisible() {
        int page = mListAdapter.getCount() / Constant.PERPAGE + 1;
        mQueue.add(new StringRequest(getTopicListUrl(page), mRefreshMoreListener, mRefreshErroListener));
    }

    private String getTopicListUrl(int page) {
        return Uri.parse(mUrl).buildUpon()
                .appendQueryParameter(HttpUtil.QUERY_CURPAGE, String.valueOf(page))
                .appendQueryParameter(HttpUtil.QUERY_PERPAGE, String.valueOf(Constant.PERPAGE))
                .build().toString();
    }

    private Listener<String> mRefreshListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            BizResult res = gson.fromJson(response, BizResult.class);
            if (res.isSuccess()) {
                Topic[] topics = Topic.newGson().fromJson(res.getMessage(), Topic[].class);
                mListAdapter.clear();
                mListAdapter.addAll(topics);
            }
            mPullView.onRefreshComplete();
        }
    };

    private Listener<String> mRefreshMoreListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            BizResult res = gson.fromJson(response, BizResult.class);
            if (res.isSuccess()) {
                Topic[] topics = Topic.newGson().fromJson(res.getMessage(), Topic[].class);
                mListAdapter.addAll(topics);
            }
            Log.d("sunbreak", "mRefreshMoreListener");
            mPullView.onRefreshComplete();
        }
    };

    private ErrorListener mRefreshErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("sunbreak", "mRefreshErroListener");
            mPullView.onRefreshComplete();
        }
    };
}
