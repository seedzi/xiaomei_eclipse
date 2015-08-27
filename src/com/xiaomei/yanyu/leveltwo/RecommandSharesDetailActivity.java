package com.xiaomei.yanyu.leveltwo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.RecommendSharesDetail;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecommandSharesDetailActivity extends AbstractActivity<LeveltwoControl> implements ViewPager.OnPageChangeListener {

	public static void startActivity(Activity ac,String id){
		Intent intent = new Intent(ac,RecommandSharesDetailActivity.class);
		intent.putExtra("id", id);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private String mId;

	private TitleBar mTitleBar;
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
		setContentView(R.layout.activity_recommend_shares_detail);
		initView();
		initData();
	}
	
	private void initView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle("详情");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.comment).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(mId))
					if(UserUtil.getUser()==null)
						LoginAndRegisterActivity.startActivity(RecommandSharesDetailActivity.this, true);
					else
						CommentListActivity.startActivity(RecommandSharesDetailActivity.this, "share", mId,true,false);
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
        mControl.getDataAsyn(mId);
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

	// ============================   callBack  ==================================
	public void getDataAsynCallBack(){
		RecommendSharesDetail data = mControl.getModel().getRecommendSharesDetail();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(data.getImageLarge(), mBackground);
		mTitle.setText(data.getShareTitle());
		mUserName.setText(data.getUsername());
		imageLoader.displayImage(data.getAvatar(), mAvatar);
		mDescription.setText(data.getShareDes());
		mNumViews.setText(data.getNumViews() + "次浏览");
		
		mPagerAdapter.addAll(data.getItems());
		mPagerAdapter.notifyDataSetChanged();
	}
	
	public void getDataAsynExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	
    private static class SharesDetailAdapter extends PagerAdapter {

        private List<RecommendSharesDetail.Item> mItems = new ArrayList<RecommendSharesDetail.Item>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.shares_detail_item, container, false);
            RecommendSharesDetail.Item item = mItems.get(position);
            UiUtil.findTextViewById(itemView, R.id.text).setText(item.getTitle());
            ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
            ImageLoader.getInstance().displayImage(item.getImageLarge(), image);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        
        public void addAll(RecommendSharesDetail.Item[] items) {
            mItems.addAll(Arrays.asList(items));
        }
        
        public void clear() {
            mItems.clear();
        }
    }
}
