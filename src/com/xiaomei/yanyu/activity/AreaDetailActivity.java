package com.xiaomei.yanyu.activity;

import java.util.Collection;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.adapter.MerchantAdapter;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.builder.GoodsBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.leveltwo.GoodsAdapter;
import com.xiaomei.yanyu.view.LayoutPagerAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by sunbreak on 9/4/15.
 */
public class AreaDetailActivity extends Activity implements LoaderCallbacks<Object> {

    private static final int AREA_LOADER = 0;

    private static final int AREA_GOODS_LOADER = 1;

    private static final int AREA_MERCHANT_LOADER = 2;

    private View mGoodsIndicator;
    private View mMerchantIndicator;
    private ViewPager mViewPager;

    private AreaPagerAdapter mPagerAdaper;
    private GoodsAdapter mGoodsAdapter;
    private MerchantAdapter mMerchantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_detail);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_area_list);
        
        ViewGroup indicator = (ViewGroup) findViewById(R.id.text_pager_indicator);
        mGoodsIndicator = indicator.getChildAt(0);
        ((TextView) mGoodsIndicator.findViewById(android.R.id.title)).setText(R.string.indicator_area_goods);
        mMerchantIndicator = indicator.getChildAt(1);
        ((TextView) mMerchantIndicator.findViewById(android.R.id.title)).setText(R.string.indicator_area_merchant);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdaper = new AreaPagerAdapter();

        mGoodsAdapter = new GoodsAdapter(this);
        mMerchantAdapter = new MerchantAdapter(this);

        getLoaderManager().initLoader(AREA_LOADER, null, this);
        getLoaderManager().initLoader(AREA_GOODS_LOADER, null, this);
        getLoaderManager().initLoader(AREA_MERCHANT_LOADER, null, this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case AREA_GOODS_LOADER:
                return new AreaGoodsLoader(this);
            case AREA_MERCHANT_LOADER:
                return new AreaMerchantLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case AREA_GOODS_LOADER:
                if (data != null) {
                    mGoodsAdapter.clear();
                    mGoodsAdapter.addAll((Collection<? extends Goods>) data);
                }
                break;
            case AREA_MERCHANT_LOADER:
                if (data != null) {
                    mMerchantAdapter.clear();
                    mMerchantAdapter.addAll((Collection<? extends Merchant>) data);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
        
    }

    public static class AreaGoodsLoader extends AsyncTaskLoader<Object> {

        public AreaGoodsLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_GOODS_LIST,
                    new BasicNameValuePair(HttpUtil.QUERY_AREA_ID, "1"),
                    new BasicNameValuePair(HttpUtil.QUERY_CURPAGE, "1"),
                    new BasicNameValuePair(HttpUtil.QUERY_PERPAGE, "10"));
            try {
                return httpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
            } catch (XiaoMeiCredentialsException e) {
                e.printStackTrace();
            } catch (XiaoMeiIOException e) {
                e.printStackTrace();
            } catch (XiaoMeiOtherException e) {
                e.printStackTrace();
            } catch (XiaoMeiJSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }

    public static class AreaMerchantLoader extends AsyncTaskLoader<Object> {

        public AreaMerchantLoader(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }

    public class AreaPagerAdapter extends LayoutPagerAdapter {

        public AreaPagerAdapter() {
            super(2);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();
            int layout = position == 0 ? R.layout.area_goods_list : R.layout.area_merchant_list;
            View itemView = LayoutInflater.from(context).inflate(layout, container, false);

            ((ListView) itemView).setAdapter(position == 0 ? mGoodsAdapter : mMerchantAdapter);
            return itemView;
        }
    }
}
