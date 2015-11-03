package com.xiaomei.yanyu.activity;

import java.util.Collection;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.adapter.MerchantAdapter;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.builder.GoodsBuilder;
import com.xiaomei.yanyu.api.builder.MerchantBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.leveltwo.GoodsAdapter;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by sunbreak on 9/4/15.
 */
public class AreaDetailActivity extends Activity implements LoaderCallbacks<Object> {

    private static final int AREA_GOODS_LOADER = 1;

    private static final int AREA_MERCHANT_LOADER = 2;

    private static final int PAGER_GOODS = 0;

    public static final int PAGER_MERCHANT = 1;

    private long mAreaId;

    private AreaPagerAdapter mPagerAdaper;
    private GoodsAdapter mGoodsAdapter;
    private MerchantAdapter mMerchantAdapter;

    private ViewPager mViewPager;

    private AreaPagerEntry mGoodsPagerEntry;

    private AreaPagerEntry mMerchantPagerEntry;

    private PageIndicator mIndicator;

    public static void startActivity(Activity activity, long id, String name, String image, String description) {
        activity.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
        activity.startActivity(new Intent(activity, AreaDetailActivity.class)
                .putExtra(IntentUtil.EXTRA_AREA_ID, id)
                .putExtra(IntentUtil.EXTRA_AREA_NAME, name)
                .putExtra(IntentUtil.EXTRA_AREA_IMAGE, image)
                .putExtra(IntentUtil.EXTRA_AREA_DESCRIPTION, description));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_detail);

        Intent intent = getIntent();
        mAreaId = intent.getLongExtra(IntentUtil.EXTRA_AREA_ID, IntentUtil.INVALID_LONG);
        String name = intent.getStringExtra(IntentUtil.EXTRA_AREA_NAME);
        String imageUrl = intent.getStringExtra(IntentUtil.EXTRA_AREA_IMAGE);
        String description = intent.getStringExtra(IntentUtil.EXTRA_AREA_DESCRIPTION);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(name);

        ImageView image = (ImageView) findViewById(R.id.image);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.area_detail_image_default)
                .showImageForEmptyUri(R.drawable.area_detail_image_default)
                .showImageOnFail(R.drawable.area_detail_image_default)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, image, options);
        ((TextView) findViewById(R.id.description)).setText(description);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mPagerAdaper = new AreaPagerAdapter();
        mViewPager.setAdapter(mPagerAdaper);
        mIndicator = (PageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);

        mGoodsAdapter = new GoodsAdapter(this);
        mMerchantAdapter = new MerchantAdapter(this);
        mGoodsPagerEntry = new AreaPagerEntry(R.string.indicator_area_goods,
                R.layout.area_goods_list, mGoodsAdapter,
                new GoodsAdapter.GoodsItemClickListener());
        mMerchantPagerEntry = new AreaPagerEntry(R.string.indicator_area_merchant,
                R.layout.area_merchant_list, mMerchantAdapter,
                new MerchantAdapter.MerchantItemClickListener());

        getLoaderManager().initLoader(AREA_GOODS_LOADER, null, this);
        getLoaderManager().initLoader(AREA_MERCHANT_LOADER, null, this);
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case AREA_GOODS_LOADER:
                return new AreaGoodsLoader(this, mAreaId);
            case AREA_MERCHANT_LOADER:
                return new AreaMerchantLoader(this, mAreaId);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case AREA_GOODS_LOADER:
                if (data != null) {
                    mGoodsAdapter.clear();
                    Collection<? extends Goods> collection = (Collection<? extends Goods>)data;
                    mGoodsAdapter.addAll(collection);
                    if (collection.size() > 0 && !mPagerAdaper.constains(mGoodsPagerEntry)) {
                        mPagerAdaper.add(PAGER_GOODS, mGoodsPagerEntry);
                        mIndicator.notifyDataSetChanged();
                        mViewPager.setCurrentItem(0);
                    }
                }
                break;
            case AREA_MERCHANT_LOADER:
                if (data != null) {
                    mMerchantAdapter.clear();
                    Collection<? extends Merchant> collection = (Collection<? extends Merchant>) data;
                    mMerchantAdapter.addAll(collection);
                    if (collection.size() > 0 && !mPagerAdaper.constains(mMerchantPagerEntry)) {
                        mPagerAdaper.add(mMerchantPagerEntry);
                        mIndicator.notifyDataSetChanged();
                        mViewPager.setCurrentItem(0);
                    }
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {
        
    }

    public static class AreaGoodsLoader extends AsyncTaskLoader<Object> {

        private long areaId;

        private List<Goods> data;

        public AreaGoodsLoader(Context context, long areaId) {
            super(context);
            this.areaId = areaId;
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_GOODS_LIST,
                    new BasicNameValuePair(HttpUtil.QUERY_AREA_ID, String.valueOf(areaId)),
                    new BasicNameValuePair(HttpUtil.QUERY_CURPAGE, "1"),
                    new BasicNameValuePair(HttpUtil.QUERY_PERPAGE, "10"));
            try {
                data = httpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
                return data;
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
            if (data != null) {
                deliverResult(data);
                return;
            }

            forceLoad();
        }
    }

    public static class AreaMerchantLoader extends AsyncTaskLoader<Object> {

        private long areaId;

        private List<Merchant> data;

        public AreaMerchantLoader(Context context, long areaId) {
            super(context);
            this.areaId = areaId;
        }

        @Override
        public Object loadInBackground() {
            HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
            HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_MERCHANT_LIST,
                    new BasicNameValuePair(HttpUtil.QUERY_AREA_ID, String.valueOf(areaId)),
                    new BasicNameValuePair(HttpUtil.QUERY_CURPAGE, "1"),
                    new BasicNameValuePair(HttpUtil.QUERY_PERPAGE, "10"));
            try {
                data = httpApi.doHttpRequestObject(httpGet, new MerchantBuilder());
                return data;
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
            if (data != null) {
                deliverResult(data);
                return;
            }

            forceLoad();
        }
    }

    public class AreaPagerAdapter extends ArrayPagerAdapter<AreaPagerEntry> {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            AreaPagerEntry item = getItem(position);
            Context context = container.getContext();
            View itemView = LayoutInflater.from(context).inflate(item.layout, container,
                    false);

            ListView listView = (ListView) itemView;
            listView.setAdapter(item.adapter);
            listView.setOnItemClickListener(item.onItemClickListener);
            container.addView(itemView);
            itemView.setTag(item.adapter instanceof GoodsAdapter ? PAGER_GOODS : PAGER_MERCHANT);
            return itemView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(getItem(position).titleRes);
        }

        @Override
        public int getItemPosition(Object object) {
            int tag = (Integer)((View)object).getTag();
            return getCount() > 1 ? tag : 0;
        }
    }

    public static class AreaPagerEntry {

        public int titleRes;

        public int layout;

        public ListAdapter adapter;

        public OnItemClickListener onItemClickListener;

        public AreaPagerEntry(int titleRes, int layout, ListAdapter adapter,
                OnItemClickListener onItemClickListener) {
            this.titleRes = titleRes;
            this.layout = layout;
            this.adapter = adapter;
            this.onItemClickListener = onItemClickListener;
        }
    }
}
