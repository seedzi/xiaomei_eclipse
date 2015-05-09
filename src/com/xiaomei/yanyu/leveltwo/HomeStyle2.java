package com.xiaomei.yanyu.leveltwo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRingDetail;
import com.xiaomei.yanyu.bean.ChannelEntity;
import com.xiaomei.yanyu.comment.CommentsActivity;
import com.xiaomei.yanyu.leveltwo.BeautifulRingDetailsActivity.MyOnPageChangeListener;
import com.xiaomei.yanyu.widget.CircleImageView;
import com.xiaomei.yanyu.widget.MyLayout;
import com.xiaomei.yanyu.widget.PagerAdapter;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.ViewPager;
import com.xiaomei.yanyu.widget.ViewPager.OnPageChangeListener;

public class HomeStyle2 extends AbstractActivity implements OnTouchListener,View.OnClickListener{
	public static void startActivity(Activity ac,String data,String tilte,String des,String img_url){
		android.util.Log.d("111", "info = " + Info.toBean(data));
		Intent intent = new Intent(ac,HomeStyle2.class);
		intent.putExtra("data", Info.toBean(data));
		intent.putExtra("tilte", tilte);
		intent.putExtra("des", des);
		intent.putExtra("img_url", img_url);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitleBar;
	
	private MyPagerAdapter mAdapter;
	
	private SlidingMenu menu ;
	
	 //手势识别
	 private GestureDetector mGestureDetector;
	 
	 private ImageView backImageview;
	 
	 private TextView pageSize;
	 
	 private ViewPager page;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_style2);
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
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.GONE);
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
	
	private String tilte;
	private String des;
	private String img_url;
	private void initData(){
		try {
			Info data = (Info) getIntent().getSerializableExtra("data");
			tilte = getIntent().getStringExtra("tilte");
			des = getIntent().getStringExtra("des");
			img_url = getIntent().getStringExtra("img_url");
			
			android.util.Log.d("111", "title = " + tilte);
			android.util.Log.d("111", "des = " + des);
			android.util.Log.d("111", "img_url = " + img_url);
			
			titleTv.setText(tilte);
			descriptionTv.setText(des);
			ImageLoader.getInstance().displayImage(img_url, backImageview);
			mAdapter.setData(data.list);
			mAdapter.notifyDataSetChanged();
			page.setOnPageChangeListener(new MyOnPageChangeListener(data.list));
		} catch (Exception e) {
			android.util.Log.d("444", "e = " + e.toString());
		}

//		mControl.getDataAsyn(id);
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
	
	// ============================   viewPager  ==================================
	private String link = "";
	private List<View> mlistViews;
		class MyPagerAdapter extends PagerAdapter implements View.OnClickListener{
			
			private List<Info.Bean> mList;

			public MyPagerAdapter() {
			}
			
			public void setData(List<Info.Bean> list){
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
				ImageLoader.getInstance().displayImage(mList.get(position).image, icon);
				TextView tv = (TextView) converView.findViewById(R.id.item_description);
				tv.setText(mList.get(position).des);
				icon.setOnClickListener(this);
				TextView title = (TextView) converView.findViewById(R.id.title);
				title.setText(mList.get(position).title);
				TextView price = (TextView) converView.findViewById(R.id.price);
				price.setText(getResources().getString(R.string.ren_ming_bi) +" " +  mList.get(position).price_xm);
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

			@Override
			public void onClick(View v) {
				android.util.Log.d("111", "url = " + link);
				GoodsDetailActivity.startActivity(HomeStyle2.this ,link);
			}
		}
		
		class MyOnPageChangeListener implements OnPageChangeListener {
			boolean refresh = false;
			private  List<Info.Bean> channelEntities;

			MyOnPageChangeListener( List<Info.Bean> channelEntities) {
				this.channelEntities = channelEntities;
			}

			@Override
			public void onPageSelected(int position) {
				if(channelEntities!=null && channelEntities.size()!=0)
					pageSize.setText(position+1 + "/" + channelEntities.size());
				
				link = channelEntities.get(position).link;
				android.util.Log.d("111", "onPageSelected link = " + link);
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
    
    // ==================================================================   Bean  ===================================================================

    private static class Info implements Serializable{
		private static final long serialVersionUID = 1001L;
		private String des;
    	private String title;
    	private String img;
    	private List<Bean> list;
    	
    	
    	
    	private static class Bean implements Serializable{
    		private static final long serialVersionUID = 1002L;
    		private String image;
    		private String title;
    		private String link;
    		private String des;
    		private String price_xm;
			@Override
			public String toString() {
				return "Bean [image=" + image + ", title=" + title + ", link="
						+ link + ", des=" + des + ", price_xm=" + price_xm
						+ "]";
			}
    	}
    	
    	public  static Info toBean(String str){
    		try {
        		JSONObject jsonObject = new JSONObject(str);
        		Info info = new Info();
        		List<Bean> list = new ArrayList<HomeStyle2.Info.Bean>();
        		JSONArray jArray = jsonObject.getJSONArray("images");
        		List<Bean> beans = new ArrayList<HomeStyle2.Info.Bean>();
        		for(int i = 0;i<jArray.length(); i++){
        			Bean bean = new Bean();
        			JSONObject js = jArray.getJSONObject(i);
        			bean.image = js.getString("image_6");
        			bean.title = js.getString("title");
        			bean.link = js.getString("link");
        			bean.des = js.getString("des");
        			bean.price_xm = js.getString("price_xm");
        			beans.add(bean);
        		}
        		info.list = beans;
        		return info;
			} catch (Exception e) {
				android.util.Log.d("222", "e = " + e.toString());
				return null;
			}
    	}

		@Override
		public String toString() {
			return "Info [des=" + des + ", title=" + title + ", list=" + list
					+ "]";
		}
    	
    	
    	
    	
    	
    	/*
    	{
    "list": {
        "images": [
            {
                "des": "春夏秋冬针对皮肤疾患，拉皮，腹肌整形等手术性领域有着丰富的经验和精湛的手术技术。代表院长姜胜勋不仅对皮肤和轮廓有着多样化的治疗技术，还是个电视红人，很多韩国明星都指定他为专门医生。",
                "title": "【春夏秋冬皮肤科】",
                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic60\/20150502190704_43072.jpg",
                "price_xm": false,
                "link": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=",
                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic60\/20150502190700_14289.jpg",
                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic60\/20150502190708_96534.jpg",
                "hosp_name": false
            },
            {
                "des": "皮肤表层沉积的角质，成为引发皮肤粉刺、让肤色暗淡的罪魁祸首。春夏秋冬4Seasons皮肤科医院的天然去角质法阿拉丁磨砂，用纯天然成分去除皮肤表层的角质，并诱导皮肤细胞的活跃再生。",
                "title": "【阿拉丁磨砂焕肤】",
                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic50\/20150502190227_61170.jpg",
                "price_xm": "4500",
                "link": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=1056",
                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic50\/20150502190220_43198.jpg",
                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic50\/20150502190235_78038.jpg",
                "hosp_name": "UP2C整形外科"
            },
            {
                "des": "FMTS（Fractional MTS）是新一代微针，通过垂直滚动，均匀地打进皮肤，并通过快速旋转大幅降低对皮肤表层的影响。FMTS结合皮肤自身再生功能，抑制皮肤老化，增强皮肤弹性、治疗毛孔、痘印、色斑等。 ",
                "title": "【FMTS微针T】",
                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic51\/20150502190333_28429.jpg",
                "price_xm": "4500",
                "link": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=1055",
                "image_5": "http:\toBean/\/bcs.duapp.com\/drxiaomei\/images\/topic51\/20150502190329_35551.jpg",
                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic51\/20150502190338_79355.jpg",
                "hosp_name": "UP2C整形外科"
            },
            {
                "des": "春夏秋冬的激光Toning将原有激光技术进一步提升为高功率进行升级治疗，把激光照射到皮肤上的时间缩短，最小化了对皮肤组织的损伤，有效治疗、色斑、雀斑、肤色不均匀暗沉等问题。",
                "title": "【Toning激光美白】",
                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic57\/20150502190433_15600.jpg",
                "price_xm": false,
                "link": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=123",
                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic57\/20150502190425_39149.jpg",
                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic57\/20150502190438_22913.jpg",
                "hosp_name": false
            },
            {
                "des": "每天长时间面对电脑工作的白领，肌肤很容易干燥缺水。Derma Queen –嫩肤皇后，将药物更有效的注射到皮肤深处。仪器治疗结合最前沿LED照射与后续管理，让水嫩效果更佳持久。",
                "title": "【嫩肤皇后水光针】",
                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic58\/20150502190523_87112.jpg",
                "price_xm": false,
                "link": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=456",
                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic58\/20150502190518_18950.jpg",
                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic58\/20150502190527_45623.jpg",
                "hosp_name": false
            }
        ],
        "des": "不想和爱的人永远隔着一层小心翼翼，灯光暗掉的时刻你还美在何处？你看，就让你尽情的看，多近，都好。你触，细腻的肌肤掠过你的沧桑，知道，你会一生守护照料，心安理得，带着孩童般的欢愉和稚嫩的脸我安详睡去。\nBe Who You Should Be",
        "title": "坏皮肤呀！都走开！Be Who You Should Be"
    },
    "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic1\/20150502190132_86619.jpg",
    "title": "坏皮肤呀！都走开！Be Who You Should Be",
    "type": "1",
    "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/topic1\/20150502190121_80612.jpg",
    "url": "http:\/\/z.drxiaomei.com\/goods.php?goods_id=1015",
    
    	*/
    }
    
}
