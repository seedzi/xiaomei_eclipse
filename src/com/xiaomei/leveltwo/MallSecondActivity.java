package com.xiaomei.leveltwo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaomei.R;
import com.xiaomei.bean.ChannelEntity;
import com.xiaomei.widget.PagerAdapter;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.ViewPager;
import com.xiaomei.widget.ViewPager.OnPageChangeListener;
import com.yuekuapp.BaseActivity;

public class MallSecondActivity extends BaseActivity {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,MallSecondActivity.class);
		context.startActivity(intent);
	}
	
	public static final int LOAD_PAGE_COUNT = 3;
	
	private TitleBar mTitleBar;
	private HorizontalScrollView headScrollView;
	private LinearLayout channelColumnLayout;
	
	private ViewPager mViewPager;
	private List<View> mListViews;
	private MyPagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall_layout);
		initView();
		initViewPager();
		setHeaderView(null);
	}
	
	private void initView() {
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle("内素除皱");
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		headScrollView = (HorizontalScrollView)findViewById(R.id.center_head);
		channelColumnLayout = (LinearLayout) findViewById(R.id.channel_column_layout);
	}
	
	private void initViewPager() {
		long time = System.currentTimeMillis();
		mListViews = new ArrayList<View>();
		getViews(LOAD_PAGE_COUNT);
		ViewConfiguration configuration = ViewConfiguration.get(this);
		mViewPager = (ViewPager) findViewById(R.id.vpager);
		mViewPager.setmMinimumVelocity(configuration.getScaledMaximumFlingVelocity());
		
		mAdapter = new MyPagerAdapter(mListViews, null);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener(null));
		mViewPager.setCurrentItem(0);
	}

	
	protected void setHeaderView(List<ChannelEntity> channelEntities) {
//		int count = channelEntities.size();
		int count = 10;
		if (count <= 4) { //
			headScrollView.setEnabled(false);
		} else {
			headScrollView.setEnabled(true);
		}
		LinearLayout newsCenterColumnHeadItem = null;
		LayoutInflater inflater = LayoutInflater.from(this);
		channelColumnLayout.removeAllViews();
		for (int i = 0; i < count; i++) {
			newsCenterColumnHeadItem = (LinearLayout) inflater.inflate(R.layout.item_channel_navigation, null);
			TextView channel_view = (TextView) newsCenterColumnHeadItem.findViewById(R.id.col_channel_name);
			channel_view.setText("点击项");
			channel_view.setTag(i);  //test huzhi
//			channel_view.setId(channelEntities.get(i).getCatid());
//			channel_view.setText(channelEntities.get(i).getCatname());
			channel_view.setOnClickListener(new ChannelItemOnclickListener(channelEntities, mViewPager));
//			headlistview.add(channel_view);
			channelColumnLayout.addView(newsCenterColumnHeadItem);
		}
		
	}
	
	/**
	* @Title: setFocusChannel
	* @Description: 设置每个导航中点中的item的背景
	* @param @param channelEntities    
	* @return void    返回类型
	* @throws
	 */
	protected void setFocusChannel(List<ChannelEntity> channelEntities) {
		/*
		synchronized (this) {
			int count = channelEntities.size();
			channelColumnLayout = (LinearLayout) headScrollView.findViewById(R.id.channel_column_layout);
			if (channelColumnLayout != null) {
				for (int i = 0; i < count; i++) {
					if (channelEntities.get(i).isCurrentChannel(mChannelId)) {
						TextView currCahnnelView = (TextView) headScrollView.findViewById(mChannelId);
						if (currCahnnelView != null) {
							currCahnnelView.setTextColor(color.color_title);
							currCahnnelView.setBackgroundResource(R.drawable.title_check_item_bg_common);
//							ThemeSettingsHelper.setTextViewColor(this,currCahnnelView,R.color.color_title);
//							ThemeSettingsHelper.setViewBackgroud(this,
//									currCahnnelView,R.drawable.title_check_item_bg_common);
							int[] p1 = new int[2];
							currCahnnelView.getLocationInWindow(p1);
							if (p1[0] + currCahnnelView.getWidth() > headScrollView.getWidth()) {
								headScrollView.scrollBy(((View) currCahnnelView.getParent()).getWidth(), 0);
							} else if (p1[0] < 0) {
								headScrollView.scrollBy(p1[0]- DensityUtils.dip2pix(getApplicationContext(),10), 0);
							}
							
						}
					} else {
						TextView preChannelView = (TextView) headScrollView.findViewById(channelEntities.get(i).getCatid());
						preChannelView.setTextColor(color.font_color_bbbbbb);
						preChannelView.setBackgroundResource(R.drawable.transparentColorVersin3_3);
//						ThemeSettingsHelper.setTextViewColor(this,preChannelView,R.color.font_color_bbbbbb);
//						ThemeSettingsHelper.setViewBackgroud(this,preChannelView,R.drawable.transparentColorVersin3_3);
					}
				}
			}
		}
		*/
	}
	
	private void getViews(int count) {
		mListViews.clear();
		LayoutInflater mInflater = getLayoutInflater();
		for (int i = 0; i < count; i++) {
			ViewGroup attachView = (ViewGroup) mInflater.inflate(R.layout.item_mall_channel_list, null);
			mListViews.add(attachView);
			MallItemBuilder builder = new MallItemBuilder(attachView);
			attachView.setTag(builder);
		}
	}
	
	
	/**item click 事件*/
	class ChannelItemOnclickListener implements OnClickListener {
		List<ChannelEntity> mChannelList = null;
		ViewPager viewPager = null;
	
		ChannelItemOnclickListener(List<ChannelEntity> mChannelList,
				ViewPager mViewPager) {
			this.mChannelList = mChannelList;
			this.viewPager = mViewPager;
		}
	
		@Override
		public void onClick(View v) {
			Toast.makeText(MallSecondActivity.this,"xx:"+ (Integer)v.getTag(),0 ).show();
			viewPager.setCurrentItem((Integer)v.getTag());
			/*
			for (int i = 0; i < mChannelList.size(); i++) {
				
				if (mChannelList.get(i).isCurrentChannel(v.getId())) {
					viewPager.setCurrentItem(i);
				}
			}*/
		}
	}
	
	// ============================   viewPager  ==================================
	class MyPagerAdapter extends PagerAdapter {
		private List<View> listViews;
		List<ChannelEntity> mChannelList;

		public MyPagerAdapter(List<View> listViews,
				List<ChannelEntity> mChannelList) {
			this.listViews = listViews;
			this.mChannelList = mChannelList;
		}

		@Override
		public void destroyItem(View collection, int position, Object arg2) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return 10;
//			return mChannelList == null || mChannelList.size() == 0 ? 1
//					: mChannelList.size();
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			try {
				((ViewPager) collection).addView(
						listViews.get(position % listViews.size()), 0);
			} catch (Exception e) {
			}
			if (mChannelList != null && mChannelList.size() > position) {
//				NewsViewBuilderBase builder = (NewsViewBuilderBase) listViews
//						.get(position % listViews.size()).getTag();
//				boolean tag = getflagOutTimeGetData(mChannelList.get(position));
//				builder.showChannelNewsToUi(mChannelList.get(position), true,
//						tag);
			}
			MallItemBuilder builder = (MallItemBuilder) listViews
			.get(position % listViews.size()).getTag();
			builder.show(getApplicationContext());
			return listViews.get(position % listViews.size());
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}
		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void startUpdate(View arg0) {
		}
	}
	
	
	class MyOnPageChangeListener implements OnPageChangeListener {
		boolean refresh = false;
		private List<ChannelEntity> channelEntities;

		MyOnPageChangeListener(List<ChannelEntity> channelEntities) {
			this.channelEntities = channelEntities;
		}

		@Override
		public void onPageSelected(int position) {
			/*
			refresh = true;
			currentIdx = position;
			if (channelEntities != null && channelEntities.size() > position
					&& channelEntities.get(position) != null) {
				mChannelId = channelEntities.get(position).getCatid();
				setFocusChannel(channelEntities);
			}
			switch (position) {
			case 0:
				YueKuAppApplication.getInstance().getMenu()
						.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				break;
			default:
				if (position == channelEntities.size() - 1) {
					YueKuAppApplication.getInstance().getMenu()
							.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
				YueKuAppApplication.getInstance().getMenu()
						.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				break;
			}*/
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageComplete(int position) {
			/*
			if (refresh) {
				if (channelEntities != null
						&& channelEntities.size() > position
						&& channelEntities.get(position) != null) {
					NewsViewBuilderBase builder = (NewsViewBuilderBase) listViews
							.get(position % listViews.size()).getTag();
					boolean flag = getIsOutTimeGetData(channelEntities
							.get(position));
					builder.showChannelNewsToUi(channelEntities.get(position),
							true, flag);
				}
				refresh = false;
			}*/
		}
	}
	
}
