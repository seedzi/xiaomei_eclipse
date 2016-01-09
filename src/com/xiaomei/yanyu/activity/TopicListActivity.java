package com.xiaomei.yanyu.activity;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.api.util.Constant;
import com.xiaomei.yanyu.bean.Topic;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.view.TopicAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class TopicListActivity extends Activity implements OnRefreshListener, OnLastItemVisibleListener {

    public static void startActivity(Activity ac, String title, String url) {
        ac.startActivity(new Intent(ac, TopicListActivity.class)
                .putExtra(IntentUtil.EXTRA_TITLE, title)
                .putExtra(IntentUtil.EXTRA_URL, url));
        UiUtil.overridePendingTransition(ac);
    }

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
    public void onLastItemVisible() { // TODO 暂时加载更多
        /*
        int page = mListAdapter.getCount() / Constant.PERPAGE + 1;
        mQueue.add(new StringRequest(getTopicListUrl(page), mRefreshMoreListener, mRefreshErroListener));
        */
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
            mPullView.onRefreshComplete();
        }
    };

    private ErrorListener mRefreshErroListener = new ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            mPullView.onRefreshComplete();
        }
    };
}
