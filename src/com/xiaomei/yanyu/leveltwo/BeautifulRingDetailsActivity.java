package com.xiaomei.yanyu.leveltwo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
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
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.bean.BeautifulRingDetail;
import com.xiaomei.yanyu.bean.ChannelEntity;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.leveltwo.control.LeveltwoControl;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.CircleImageView;
import com.xiaomei.yanyu.widget.MyLayout;
import com.xiaomei.yanyu.widget.PagerAdapter;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.ViewPager;
import com.xiaomei.yanyu.widget.ViewPager.OnPageChangeListener;

public class BeautifulRingDetailsActivity extends AbstractActivity<LeveltwoControl> implements OnTouchListener{

	@Deprecated
	public static void startActivity(Context context){
		Intent intent = new Intent(context,BeautifulRingDetailsActivity.class);
		context.startActivity(intent);
	}
	
	public static void startActivity(Activity ac,String id){
		Intent intent = new Intent(ac,BeautifulRingDetailsActivity.class);
		intent.putExtra("id", id);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitleBar;
	
	private MyPagerAdapter mAdapter;
	
	private SlidingMenu menu ;
	
	 //手势识别
	 private GestureDetector mGestureDetector;
	 
	 private String id;
	
	 private ImageView backImageview;
	 
	 private TextView pageSize;
	 
	 private ViewPager page ;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id = getIntent().getStringExtra("id");
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
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.share).setVisibility(View.GONE);
		mTitleBar.findViewById(R.id.comment).setVisibility(View.VISIBLE);
		mTitleBar.findViewById(R.id.comment).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(commentId))
					if(UserUtil.getUser()==null)
						LoginAndRegisterActivity.startActivity(BeautifulRingDetailsActivity.this, true);
					else
						CommentListActivity.startActivity(BeautifulRingDetailsActivity.this, "share", commentId,true,false);
			}
		});
		
		backImageview = (ImageView) findViewById(R.id.img);
		pageSize = (TextView) findViewById(R.id.page_size);
		getView();
		iniMenu();
		initMenuBehindLayoutViews();
	}
	
	private void iniMenu() {
	    menu = (SlidingMenu) findViewById(R.id.slidingmenulayout);
		menu.showMenu();
		
		ViewGroup content = (ViewGroup) menu.getContent();
		page = (ViewPager) content.findViewById(R.id.pager);
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
		mlistViews.add(convertView);
		
		convertView = (ViewGroup) mLInflater.inflate(R.layout.item_beautiful_ring_details, null);
		iv = (ImageView) convertView.findViewById(R.id.item_img);
		mlistViews.add(convertView);
		
		convertView = (ViewGroup) mLInflater.inflate(R.layout.item_beautiful_ring_details, null);
		iv = (ImageView) convertView.findViewById(R.id.item_img);
		mlistViews.add(convertView);
	}
	
	// ============================   文字信息  ==================================
	private TextView descriptionTv;
	private TextView browseSizeTv;
	private CircleImageView iconImage;
	private TextView nickNameTv;
	private TextView titleTv;
	
	private void initMenuBehindLayoutViews(){
		descriptionTv = (TextView) findViewById(R.id.description);
		browseSizeTv = (TextView) findViewById(R.id.browse_size);
		iconImage = (CircleImageView) findViewById(R.id.icon);
		nickNameTv = (TextView) findViewById(R.id.nick_name);
		titleTv = (TextView) findViewById(R.id.layout_title);
	}
	
	private void initData(){
		mControl.getDataAsyn(id);
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
	
	private String commentId;
	
	// ============================   callBack  ==================================
	public void getDataAsynCallBack(){
		BeautifulRingDetail data = mControl.getModel().getBeautifulRingDetail();
		commentId = data.getId();
		ImageLoader.getInstance().displayImage(data.getImage(), backImageview);
		ImageLoader.getInstance().displayImage(data.getAvatar(), iconImage);
		titleTv.setText(data.getShareTitle());
		descriptionTv.setText(data.getShareDes());
		nickNameTv.setText(data.getUsername());
		browseSizeTv.setText(data.getNumFavors() + "次浏览");
		mAdapter.setData(data.getItems());
		mAdapter.notifyDataSetChanged();
		page.setOnPageChangeListener(new MyOnPageChangeListener(data.getItems()));
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
				icon.setImageResource(R.drawable.ring_details_default_img);
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
			private  List<BeautifulRingDetail.Item>  channelEntities  ;

			MyOnPageChangeListener(  List<BeautifulRingDetail.Item> mList) {
				this.channelEntities = mList;
			}

			@Override
			public void onPageSelected(int position) {
				if(channelEntities!=null && channelEntities.size()!=0)
					pageSize.setText(position+1 + "/" + channelEntities.size());
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


}
