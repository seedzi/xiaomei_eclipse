package com.xiaomei.leveltwo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.bean.BeautifulRingDetail;
import com.xiaomei.bean.ChannelEntity;
import com.xiaomei.comment.CommentsActivity;
import com.xiaomei.leveltwo.control.LeveltwoControl;
import com.xiaomei.widget.CircleImageView;
import com.xiaomei.widget.MyLayout;
import com.xiaomei.widget.PagerAdapter;
import com.xiaomei.widget.TitleBar;
import com.xiaomei.widget.ViewPager;
import com.xiaomei.widget.ViewPager.OnPageChangeListener;

public class BeautifulRingDetailsActivity extends AbstractActivity<LeveltwoControl> implements OnTouchListener,View.OnClickListener{

	public static void startActivity(Context context){
		Intent intent = new Intent(context,BeautifulRingDetailsActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
	private MyPagerAdapter mAdapter;
	
	private SlidingMenu menu ;
	
	 //手势识别
	 private GestureDetector mGestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_beautiful_ring_details_layout);
		initView();
		intGesture();
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
		findViewById(R.id.comment).setOnClickListener(this);
		getView();
		iniMenu();
		initMenuBehindLayoutViews();
	}
	
	private void iniMenu() {
	    menu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
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
	
	// ============================   手势  ==================================
	private void intGesture(){
		mGestureDetector = new GestureDetector(this, new MyGestureListener(this));
//		View view = findViewById(R.id.menu_layout);
		View view = menu.getMenu();
		view.setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		  return mGestureDetector.onTouchEvent(event);
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
	
		
	private class MyGestureListener extends SimpleOnGestureListener {

		private Context mContext;

		MyGestureListener(Context context) {
			mContext = context;
		}
		@Override
		// 按下触摸屏按下时立刻触发
		public boolean onDown(MotionEvent e) {
			return true;
		}
		// 短按，触摸屏按下片刻后抬起，会触发这个手势，如果迅速抬起则不会
		@Override
		public void onShowPress(MotionEvent e) {
		}
		// 释放，手指离开触摸屏时触发(长按、滚动、滑动时，不会触发这个手势)
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
		// 滑动，按下后滑动
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if(e2.getX()<e1.getX()){
				menu.showContent();
				return true;
			}
			return false;
		}

		// 长按，触摸屏按下后既不抬起也不移动，过一段时间后触发
		@Override
		public void onLongPress(MotionEvent e) {
		}

		// 滑动，触摸屏按下后快速移动并抬起，会先触发滚动手势，跟着触发一个滑动手势
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			return false;
		}

		// 双击，手指在触摸屏上迅速点击第二下时触发
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return false;
		}
		// 双击后按下跟抬起各触发一次
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}
		// 单击
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return false;
		}
	}


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.comment:
            CommentsActivity.startActivity(this);
            break;
        default:
            break;
        }
    }



}
