package com.xiaomei.yanyu.levelone.adapter;


import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.YanYuUtils;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Switch;

public class HomeAdapter2 extends ArrayAdapter<Object> {
	
	private final int LAYOUT_TYPE_TOPIC = 0; //热点轮播
	
	private final int LAYOUT_TYPE_RECOMMENDED_AREA = 1;//推荐地区
	
	private final int LAYOUT_TYPE_HOT_ITEMS = 2;//热门项目
	
	private final int LAYOUT_TYPE_CONSULTATION = 3;//咨询
	
	private final int LAYOUT_TYPE_INTRODUCTION = 4;//产品 机构介绍

	private final int LAYOUT_TYPE_SHARE = 5; //圈子精华分享
	
	private final int LAYOUT_TYPE_COUNT = 6;
	
	private AbsListView.LayoutParams mAbsLParams;
	
	private int mScreenWidth;
	
	private LayoutInflater mInflater;
	
    public HomeAdapter2(Context context) {
        super(context, 0);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mInflater = LayoutInflater.from(getContext());
    }
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if(convertView==null){
			holder = new Holder();
			switch (getItemViewType(position)) {
			case LAYOUT_TYPE_TOPIC: // 首页热点图
				convertView = mInflater.inflate(R.layout.home_topic_layout, null);
				holder.mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
				FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
				holder.mViewPager.setLayoutParams(flp);
				break;
			case LAYOUT_TYPE_RECOMMENDED_AREA:
				convertView = mInflater.inflate(R.layout.home_recommended_area_layout, null);
				break;
			case LAYOUT_TYPE_HOT_ITEMS:
				convertView = mInflater.inflate(R.layout.home_hot_items_layout, null);
				break;
			case LAYOUT_TYPE_CONSULTATION:
				convertView = mInflater.inflate(R.layout.home_consultation_layout, null);
				break;
			case LAYOUT_TYPE_INTRODUCTION:
				convertView = mInflater.inflate(R.layout.home_introduction_layout, null);
				break;
			case LAYOUT_TYPE_SHARE:
				convertView = mInflater.inflate(R.layout.home_share_layout, null);
				break;
			default:
				break;
			}
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position) {
		return position%6;
	}
	
	@Override
	public int getViewTypeCount() {
		return LAYOUT_TYPE_COUNT;
	}
	
	/***
	 * Holder
	 */
	private class Holder{
		private ViewPager mViewPager;
	}
	
	
	/**
	 * 咨询PageAdapter
	 */
	private class ConsultationPageAdapter  extends PagerAdapter{
		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public void startUpdate(View paramView) {
		}

		@Override
		public Object instantiateItem(View paramView, int paramInt) {
			return null;
		}

		@Override
		public void destroyItem(View paramView, int paramInt, Object paramObject) {
		}

		@Override
		public void finishUpdate(View paramView) {
		}

		@Override
		public boolean isViewFromObject(View paramView, Object paramObject) {
			return false;
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void restoreState(Parcelable paramParcelable,
				ClassLoader paramClassLoader) {
		}
	}
	/**
	 * 热点PageAdapter
	 */
	private class TopicPageAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public void startUpdate(View paramView) {
		}

		@Override
		public Object instantiateItem(View paramView, int paramInt) {
			return null;
		}

		@Override
		public void destroyItem(View paramView, int paramInt, Object paramObject) {
		}

		@Override
		public void finishUpdate(View paramView) {
		}

		@Override
		public boolean isViewFromObject(View paramView, Object paramObject) {
			return false;
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void restoreState(Parcelable paramParcelable,
				ClassLoader paramClassLoader) {
		}
	}
}
