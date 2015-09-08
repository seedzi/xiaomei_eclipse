package com.xiaomei.yanyu.activity;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.android.dx.io.instructions.OneRegisterDecodedInstruction;
import com.google.gson.Gson;
import com.umeng.socialize.net.l;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpUtil;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class TopicListActivity extends Activity implements OnRefreshListener, OnLastItemVisibleListener, LoaderCallbacks<Object> {

    public static void startActivity(Activity ac, String title, String url) {
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
        ac.startActivity(new Intent(ac, TopicListActivity.class)
                .putExtra(IntentUtil.EXTRA_TITLE, title)
                .putExtra(IntentUtil.EXTRA_URL, url));
    }

    private static final int TOPIC_LOADER = 0;

    private String mUrl;

    private PullToRefreshListView mPullView;
    private TopicAdapter mListAdapter;

    private RecommendGoodsLoader mTopicLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra(IntentUtil.EXTRA_URL);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(intent.getStringExtra(IntentUtil.EXTRA_TITLE));

        mPullView = (PullToRefreshListView) findViewById(R.id.list);
        mPullView.setOnRefreshListener(this);
        View emptyView = findViewById(R.id.empty);
        mPullView.setEmptyView(emptyView);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopicLoader.forceLoad();
            }
        });

        ListView listView = mPullView.getRefreshableView();
        mListAdapter = new TopicAdapter(this);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new TopicAdapter.TopicItemClickListener());

        mTopicLoader = (RecommendGoodsLoader) getLoaderManager().initLoader(TOPIC_LOADER, null, this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        mTopicLoader.forceLoad();
    }

    @Override
    public void onLastItemVisible() {
        // TODO Load more
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new RecommendGoodsLoader(this, mUrl);
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        if (data != null) {
            mListAdapter.clear();
            mListAdapter.addAll((Topic[]) data);
        }
        mPullView.onRefreshComplete();
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
        
    }

    public static class RecommendGoodsLoader extends AsyncTaskLoader<Object> {

        private String url;

        public RecommendGoodsLoader(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.GOODS_TOPIC_LIST,
                    new BasicNameValuePair(HttpUtil.QUERY_CURPAGE, "1"),
                    new BasicNameValuePair(HttpUtil.QUERY_PERPAGE, "10"));
            try {
                BizResult result = httpApi.doHttpRequestResult(httpGet);
                if (result.isSuccess()) {
                    Gson gson = new Gson();
                    BizResult msg = gson.fromJson(result.getMessage(), BizResult.class);
                    return gson.fromJson(msg.getMessage(), Topic[].class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XiaoMeiOtherException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
