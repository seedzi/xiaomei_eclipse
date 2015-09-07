/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.activity;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Area;
import com.xiaomei.yanyu.bean.Area.Filter;
import com.xiaomei.yanyu.bean.Area.FilterItem;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.DropMenu;
import com.xiaomei.yanyu.widget.TitleActionBar;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshListView;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnLastItemVisibleListener;
import com.xiaomei.yanyu.widget.pullrefreshview.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by sunbreak on 9/2/15.
 */
public class AreaListActivity extends Activity implements OnRefreshListener, OnLastItemVisibleListener, LoaderManager.LoaderCallbacks<Object> {

    private static final int AREA_FILTER_LOADER = 1;

    private View mTopFilter;
    private DropMenu mFilterCountry;
    private DropMenu mFilterSpecial;
    private PullToRefreshListView mPullView;
    private ListView mListView;

    private RequestQueue mQueue;
    private AreaAdapter mAreaAdapter;
    private FilterAdapter mCountryAdapter;
    private FilterAdapter mGoodsTypeAdapter;
    private String mAreaCountry = "";
    private String mAreaSpecial = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_area_list);

        mQueue = XiaoMeiApplication.getInstance().getQueue();
        mCountryAdapter = new FilterAdapter(this);
        mGoodsTypeAdapter = new FilterAdapter(this);
        mAreaAdapter = new AreaAdapter(this);

        mTopFilter = findViewById(R.id.filter);
        mFilterCountry = (DropMenu) findViewById(R.id.country);
        mFilterSpecial = (DropMenu) findViewById(R.id.goods_type);

        mPullView = (PullToRefreshListView) findViewById(R.id.list);
        mPullView.setOnRefreshListener(this);
        mPullView.setOnLastItemVisibleListener(this);
        mListView = mPullView.getRefreshableView();
        View emptyView = findViewById(R.id.empty);
        mPullView.setEmptyView(emptyView);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        mFilterCountry.setAdapter(mCountryAdapter);
        mFilterCountry.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) parent.getItemAtPosition(position);
                mAreaCountry = item.getKey();
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mFilterSpecial.setAdapter(mGoodsTypeAdapter);
        mFilterSpecial.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FilterItem item = (FilterItem) parent.getItemAtPosition(position);
                mAreaSpecial = item.getKey();
                onRefresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mListView.setAdapter(mAreaAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = parent.getContext();
                context.startActivity(new Intent(context, AreaDetailActivity.class).putExtra(IntentUtil.EXTRA_AREA_ID, id));
            }
        });

        getLoaderManager().initLoader(AREA_FILTER_LOADER, null, this);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mQueue.add(new StringRequest(getAreaListUrl(), mRefreshListener, mRefreshErroListener));
    }

    @Override
    public void onLastItemVisible() {
        // TODO Load more
    }

    private String getAreaListUrl() {
        return Uri.parse(HttpUrlManager.AREA_LIST).buildUpon()
                .appendQueryParameter(HttpUtil.QUERY_COUNTRY, mAreaCountry)
                .appendQueryParameter(HttpUtil.QUERY_SPECIAL, mAreaSpecial)
                .build().toString();
    }

    private Listener<String> mRefreshListener = new Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson gson = new Gson();
            BizResult res = gson.fromJson(response, BizResult.class);
            if (res.isSuccess()) {
                mAreaAdapter.clear();
                mAreaAdapter.addAll(gson.fromJson(res.getMessage(), Area[].class));
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

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case AREA_FILTER_LOADER:
                return new AreaFilterLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case AREA_FILTER_LOADER:
                if (data != null) {
                    Filter[] areaFilters = (Filter[]) data;
                    mCountryAdapter.clear();
                    mCountryAdapter.addAll(areaFilters[0].getItems());
                    mGoodsTypeAdapter.clear();
                    mGoodsTypeAdapter.addAll(areaFilters[1].getItems());
                }
                mTopFilter.setVisibility(data != null ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    private static class AreaFilterLoader extends AsyncTaskLoader<Object> {

        public AreaFilterLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_FILTER_LIST);
            try {
                BizResult result = httpApi.doHttpRequestResult(httpGet);
                JsonObject jsonObject = result.getMessage().getAsJsonObject();
                Gson gson = new Gson();
                Filter countryFilter = gson.fromJson(jsonObject.get(Filter.COUNTRY), Filter.class);
                Filter goodstypeFilter = gson.fromJson(jsonObject.get(Filter.SPECIAL), Filter.class);
                return new Filter[]{countryFilter, goodstypeFilter};
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

    private static class AreaAdapter extends ArrayAdapter<Area> {

        private DisplayImageOptions options;

        public AreaAdapter(Context context) {
            super(context, 0);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.home_area_default)
                    .showImageForEmptyUri(R.drawable.home_area_default)
                    .showImageOnFail(R.drawable.home_area_default)
                    .build();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = getContext();
            View itemView = convertView != null ? convertView :
                    LayoutInflater.from(context).inflate(R.layout.area_list_item, parent, false);

            Area area = getItem(position);
            ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
            ImageLoader.getInstance().displayImage(area.getImageLarge(), image, options);
            UiUtil.findTextViewById(itemView, R.id.name).setText(area.getName());
            UiUtil.findTextViewById(itemView, R.id.goods_count).setText(context.getString(R.string.area_goods_count, String.valueOf(area.getCount())));
            UiUtil.findTextViewById(itemView, R.id.description).setText(area.getDescription());
            UiUtil.findTextViewById(itemView, R.id.special).setText(context.getString(R.string.area_special) + area.getSpecial());
            return itemView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }

    private class FilterAdapter extends ArrayAdapter<Area.FilterItem> {

        public FilterAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.top_filter_drop_item, parent, false);
            UiUtil.findTextViewById(itemView, android.R.id.text1).setText(getItem(position).getLabel());
            return itemView;
        }
    }
}
