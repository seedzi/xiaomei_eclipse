package com.xiaomei.leveltwo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomei.R;
import com.xiaomei.bean.ChannelEntity;
import com.xiaomei.widget.ViewPager;
import com.yuekuapp.BaseActivity;

public class MallSecondActivity extends BaseActivity {
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,MallSecondActivity.class);
		context.startActivity(intent);
	}
	
	public static final int LOAD_PAGE_COUNT = 3;
	
	private HorizontalScrollView headScrollView;
	private LinearLayout channelColumnLayout;
	
	private ViewPager mViewPager;
	private List<View> listViews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall_layout);
		initView();
		setHeaderView(null);
	}
	
	private void initView() {
		long time = System.currentTimeMillis();
		headScrollView = (HorizontalScrollView)findViewById(R.id.center_head);
		channelColumnLayout = (LinearLayout) findViewById(R.id.channel_column_layout);
	}
	
	private void initViewPager() {
		long time = System.currentTimeMillis();
		listViews = new ArrayList<View>();
		getViews(LOAD_PAGE_COUNT);
		ViewConfiguration configuration = ViewConfiguration.get(this);
//		mViewPager = findView(R.id.vPager);
//		mViewPager.setmMinimumVelocity(configuration.getScaledMaximumFlingVelocity());
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
//			channel_view.setId(channelEntities.get(i).getCatid());
//			channel_view.setText(channelEntities.get(i).getCatname());
//			channel_view.setOnClickListener(new ChannelItemOnclickListener(channelEntities, mViewPager));
//			headlistview.add(channel_view);
			channelColumnLayout.addView(newsCenterColumnHeadItem);
		}
		
	}
	
	private void getViews(int count) {
		/*
		long time = System.currentTimeMillis();
		listViews.clear();
		LayoutInflater mInflater = getLayoutInflater();
		for (int i = 0; i < count; i++) {
			View v = mInflater.inflate(R.layout.item_news_channel_list_, null);
			listViews.add(v);
			NewsViewBuilderBase newsViewBuilder = new NewsViewBuilderBase(this, v,new ChannelEntity(10, "要闻"),showFouces);
			v.setTag(newsViewBuilder);
		}*/
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
			for (int i = 0; i < mChannelList.size(); i++) {
//				if (mChannelList.get(i).isCurrentChannel(v.getId())) {
//					viewPager.setCurrentItem(i);
//				}
			}
		}
	}
}
