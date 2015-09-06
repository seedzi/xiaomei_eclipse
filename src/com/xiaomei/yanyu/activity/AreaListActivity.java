/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.activity;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

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
import com.xiaomei.yanyu.bean.AreaFilter;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.DropMenu;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by sunbreak on 9/2/15.
 */
public class AreaListActivity extends Activity implements LoaderManager.LoaderCallbacks<Object> {

    private static final int AREA_LOADER = 0;
    private static final int AREA_FILTER_LOADER = 1;

    private View mTopFilter;
    private DropMenu mFilterCountry;
    private DropMenu mFilterGoodsType;
    private ListView mListView;

    private AreaAdapter mAreaAdapter;
    private FilterAdapter mCountryAdapter;
    private FilterAdapter mGoodsTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_list);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_area_list);

        mCountryAdapter = new FilterAdapter(this);
        mGoodsTypeAdapter = new FilterAdapter(this);
        mAreaAdapter = new AreaAdapter(this);

        mTopFilter = findViewById(R.id.filter);
        mFilterCountry = (DropMenu) findViewById(R.id.country);
        mFilterGoodsType = (DropMenu) findViewById(R.id.goods_type);
        mListView = (ListView) findViewById(android.R.id.list);

        mFilterCountry.setAdapter(mCountryAdapter);
        mFilterGoodsType.setAdapter(mGoodsTypeAdapter);
        mListView.setAdapter(mAreaAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = parent.getContext();
                context.startActivity(new Intent(context, AreaDetailActivity.class).putExtra(IntentUtil.EXTRA_AREA_ID, id));
            }
        });

        getLoaderManager().initLoader(AREA_LOADER, null, this);
        getLoaderManager().initLoader(AREA_FILTER_LOADER, null, this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case AREA_LOADER:
                return new AreaLoader(this);
            case AREA_FILTER_LOADER:
                return new AreaFilterLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case AREA_LOADER:
                if (data != null) {
                    mAreaAdapter.clear();
                    mAreaAdapter.addAll((Area[]) data);
                }
                break;
            case AREA_FILTER_LOADER:
                if (data != null) {
                    AreaFilter[] areaFilters = (AreaFilter[]) data;
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

    private static class AreaLoader extends AsyncTaskLoader<Object> {

        public AreaLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_LIST,
                    new BasicNameValuePair(HttpUtil.QUERY_COUNTRY, "0"),
                    new BasicNameValuePair(HttpUtil.QUERY_SPECIAL, "0"));
            try {
                BizResult result = httpApi.doHttpRequestResult(httpGet);
                if (result.isSuccess()) {
                    return new Gson().fromJson(result.getMessage(), Area[].class);
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
                AreaFilter countryFilter = gson.fromJson(jsonObject.get(AreaFilter.COUNTRY), AreaFilter.class);
                AreaFilter goodstypeFilter = gson.fromJson(jsonObject.get(AreaFilter.GOODS_TYPE), AreaFilter.class);
                return new AreaFilter[]{countryFilter, goodstypeFilter};
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
            View itemView = convertView != null ? convertView :
                    LayoutInflater.from(getContext()).inflate(R.layout.area_list_item, parent, false);

            Area area = getItem(position);
            ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
            ImageLoader.getInstance().displayImage(area.getImageLarge(), image, options);
            UiUtil.findTextViewById(itemView, R.id.name).setText(area.getName());
            UiUtil.findTextViewById(itemView, R.id.goods_count).setText(String.valueOf(area.getCount()));
            UiUtil.findTextViewById(itemView, R.id.description).setText(area.getDescription());
            UiUtil.findTextViewById(itemView, R.id.special).setText(area.getSpecial());
            return itemView;
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }

    private class FilterAdapter extends ArrayAdapter<AreaFilter.Item> {

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
