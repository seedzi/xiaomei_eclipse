package com.xiaomei.yanyu.leveltwo;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.XiaoMeiApi;
import com.xiaomei.yanyu.bean.RecommendSharesDetail;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendSharesDetailActivity extends Activity implements LoaderManager.LoaderCallbacks<Object>, ViewPager.OnPageChangeListener {

	public static void startActivity(Activity ac,String id){
		Intent intent = new Intent(ac,RecommendSharesDetailActivity.class);
		intent.putExtra("id", id);
		ac.startActivity(intent);
        UiUtil.overridePendingTransition(ac);
	}
	
	private static final int SHARES_DETAIL_LOADER = 0;
	
	private String mId;

	private TitleActionBar mTitleBar;
    private ImageView mBackground;
    private SlidingMenu mSlidingMenu;
    private TextView mTitle;
    private ImageView mAvatar;
    private TextView mUserName;
    private TextView mDescription;
    private TextView mNumViews;
    private ViewPager mViewPager;
    private TextView mPageIndicator;

    private SharesDetailAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mId = getIntent().getStringExtra("id");
		setContentView(R.layout.activity_sliding_detail);
		initView();
		initData();
	}
	
	private void initView(){
	    mTitleBar = new TitleActionBar(getActionBar());
		mTitleBar.setTitle(R.string.detail);
		mTitleBar.setImageAction(R.drawable.bubble_selector);
		mTitleBar.setOnActionClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(mId))
					if(UserUtil.getUser()==null)
						LoginAndRegisterActivity.startActivity(RecommendSharesDetailActivity.this, true);
					else
						CommentListActivity.startActivity(RecommendSharesDetailActivity.this, "share", mId,true,false);
			}
		});
		
		mBackground = (ImageView) findViewById(android.R.id.background);
        mSlidingMenu = (SlidingMenu) findViewById(R.id.sliding_menu);
        mSlidingMenu.showMenu();

        // Summary
        mTitle = (TextView) findViewById(R.id.detail_title);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mUserName = (TextView) findViewById(R.id.username);
        mNumViews = (TextView) findViewById(R.id.num_views);
        mDescription = (TextView) findViewById(R.id.description);

        // Description
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new SharesDetailAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        mPageIndicator = (TextView) findViewById(R.id.page_indicator);
	}
	
    private void initData() {
        getLoaderManager().initLoader(SHARES_DETAIL_LOADER, null, this);
    }

    private void updateView(RecommendSharesDetail detail) {
        mTitle.setText(detail.getShareTitle());
        mUserName.setText(detail.getUsername());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(detail.getAvatar(), mAvatar);
        mDescription.setText(detail.getShareDes());
        mNumViews.setText(getString(R.string.num_views, detail.getNumViews()));
        imageLoader.displayImage(detail.getImageLarge(), mBackground);

        mPagerAdapter.addAll(detail.getItems());
        mPagerAdapter.notifyDataSetChanged();
        if (mPagerAdapter.getCount() > 0) {
            onPageSelected(0);
        }
    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case SHARES_DETAIL_LOADER:
                return new SharesDetailLoader(this, mId);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        switch (loader.getId()) {
            case SHARES_DETAIL_LOADER:
                if (data != null) {
                    updateView((RecommendSharesDetail) data);
                } else {
                    Toast.makeText(this, "获取数据异常", 0).show();
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPageIndicator.setText(String.valueOf(position + 1) + "/" + mPagerAdapter.getCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
            mSlidingMenu.setTouchModeAbove(mViewPager.getCurrentItem() == 0 ? SlidingMenu.TOUCHMODE_FULLSCREEN : SlidingMenu.TOUCHMODE_NONE);
        }
    }

    private static class SharesDetailLoader extends AsyncTaskLoader<Object> {

        private final String mId;

        public SharesDetailLoader(Context context, String id) {
            super(context);
            mId = id;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public Object loadInBackground() {
            XiaoMeiApi httpApi = XiaoMeiApplication.getInstance().getApi();
            try {
                return httpApi.getRecommendSharesDetailFromNet(mId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class SharesDetailAdapter extends ArrayPagerAdapter<RecommendSharesDetail.Item> {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.shares_detail_item, container, false);
            RecommendSharesDetail.Item item = getItem(position);
            UiUtil.findTextViewById(itemView, R.id.text).setText(item.getTitle());
            ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
            ImageLoader.getInstance().displayImage(item.getImageLarge(), image);
            container.addView(itemView);
            return itemView;
        }
    }
}
