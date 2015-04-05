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
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.bean.ChannelEntity;
import com.xiaomei.widget.MyLayout;
import com.xiaomei.widget.PagerAdapter;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.ViewPager;
import com.xiaomei.widget.ViewPager.OnPageChangeListener;

public class BeautifulRingDetailsActivity extends BaseActiviy {

	public static void startActivity(Context context){
		Intent intent = new Intent(context,BeautifulRingDetailsActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_beautiful_ring_details_layout);
		initView();
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
		getView();
		iniMenu();
	}
	
	private void iniMenu() {
		SlidingMenu menu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
		menu.showMenu();
		
		ViewGroup content = (ViewGroup) menu.getContent();
		ViewPager page = (ViewPager) content.findViewById(R.id.pager);
		page.setAdapter(new MyPagerAdapter());
		MyLayout myLayout = (MyLayout) findViewById(R.id.mylayout);
		myLayout.setChild_viewpager(page);
	}
	
	private void getView(){
		LayoutInflater mLInflater = LayoutInflater.from(getApplicationContext());
		mlistViews = new ArrayList<View>();
		ViewGroup  convertView = (ViewGroup) mLInflater.inflate(R.layout.item_beautiful_ring_details, null);
		ImageView iv = (ImageView) convertView.findViewById(R.id.item_img);
		iv.setImageResource(R.drawable.sex_1);
		mlistViews.add(convertView);
		
		convertView = (ViewGroup) mLInflater.inflate(R.layout.item_beautiful_ring_details, null);
		iv = (ImageView) convertView.findViewById(R.id.item_img);
		iv.setImageResource(R.drawable.sex_2);
		mlistViews.add(convertView);
		
		convertView = (ViewGroup) mLInflater.inflate(R.layout.item_beautiful_ring_details, null);
		iv = (ImageView) convertView.findViewById(R.id.item_img);
		iv.setImageResource(R.drawable.sex_3);
		mlistViews.add(convertView);
	}
	
	// ============================   viewPager  ==================================
	
	private List<View> mlistViews;
		class MyPagerAdapter extends PagerAdapter {

			public MyPagerAdapter() {
			}

			@Override
			public void destroyItem(View collection, int position, Object arg2) {
			}

			@Override
			public void finishUpdate(View arg0) {
			}

			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public Object instantiateItem(View collection, int position) {
				View converView =  mlistViews.get(position % mlistViews.size());
				ViewGroup vG = (ViewGroup) converView.getParent();
				if(vG!=null)
					vG.removeView(converView);
				((ViewPager) collection).addView(converView, 0);
				return converView;
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
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageComplete(int position) {
			}
		}
	
}
