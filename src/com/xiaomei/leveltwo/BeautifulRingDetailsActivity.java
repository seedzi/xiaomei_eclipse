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
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.bean.BeautifulRingDetail;
import com.xiaomei.bean.ChannelEntity;
import com.xiaomei.leveltwo.control.LeveltwoControl;
import com.xiaomei.widget.CircleImageView;
import com.xiaomei.widget.MyLayout;
import com.xiaomei.widget.PagerAdapter;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.ViewPager;
import com.xiaomei.widget.ViewPager.OnPageChangeListener;

public class BeautifulRingDetailsActivity extends BaseActiviy<LeveltwoControl> {

	public static void startActivity(Context context){
		Intent intent = new Intent(context,BeautifulRingDetailsActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	private MyPagerAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_beautiful_ring_details_layout);
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
		findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		getView();
		iniMenu();
		initMenuBehindLayoutViews();
	}
	
	private void iniMenu() {
		SlidingMenu menu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
		menu.showMenu();
		
		ViewGroup content = (ViewGroup) menu.getContent();
		ViewPager page = (ViewPager) content.findViewById(R.id.pager);
		mAdapter = new MyPagerAdapter();
		page.setAdapter(mAdapter);
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
	
	// ============================   文字信息  ==================================
	private TextView descriptionTv;
	private TextView browseSizeTv;
	private CircleImageView iconImage;
	private TextView nickNameTv;
	private TextView sexLocationTv;
	private TextView titleTv;
	
	private void initMenuBehindLayoutViews(){
		descriptionTv = (TextView) findViewById(R.id.description);
		browseSizeTv = (TextView) findViewById(R.id.browse_size);
		iconImage = (CircleImageView) findViewById(R.id.icon);
		nickNameTv = (TextView) findViewById(R.id.nick_name);
		sexLocationTv = (TextView) findViewById(R.id.sex_location);
		titleTv = (TextView) findViewById(R.id.layout_title);
	}
	
	private void initData(){
		mControl.getDataAsyn();
	}
	
	// ============================   callBack  ==================================
	public void getDataAsynCallBack(){
		BeautifulRingDetail data = mControl.getModel().getBeautifulRingDetail();
		titleTv.setText(data.getShareTitle());
		mAdapter.setData(data.getItems());
		mAdapter.notifyDataSetChanged();
	}
	
	public void getDataAsynExceptionCallBack(){
		Toast.makeText(this, "获取数据异常", 0).show();
	}
	
	// ============================   viewPager  ==================================
	
	private List<View> mlistViews;
		class MyPagerAdapter extends PagerAdapter {
			
			private List<BeautifulRingDetail.Item> mList;

			public MyPagerAdapter() {
			}
			
			public void setData(List<BeautifulRingDetail.Item> list){
				mList = list;
			}

			@Override
			public void destroyItem(View collection, int position, Object arg2) {
			}

			@Override
			public void finishUpdate(View arg0) {
			}

			@Override
			public int getCount() {
				return mList == null ? 0 : mList.size();
			}

			@Override
			public Object instantiateItem(View collection, int position) {
				View converView =  mlistViews.get(position % mlistViews.size());
				ViewGroup vG = (ViewGroup) converView.getParent();
				if(vG!=null)
					vG.removeView(converView);
				((ViewPager) collection).addView(converView, 0);
				ImageView icon = (ImageView) converView.findViewById(R.id.item_img);
				ImageLoader.getInstance().displayImage(mList.get(position).getUrl(), icon);
				TextView tv = (TextView) converView.findViewById(R.id.item_description);
				tv.setText(mList.get(position).getTilte());
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
