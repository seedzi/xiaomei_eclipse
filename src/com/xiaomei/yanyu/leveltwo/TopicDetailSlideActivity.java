package com.xiaomei.yanyu.leveltwo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicDetailSlideActivity extends AbstractActivity implements ViewPager.OnPageChangeListener {

	public static void startActivity(Activity ac,String data,String tilte,String des,String img_url,String viewcount){
		android.util.Log.d("111", "info = " + Info.toBean(data));
		Intent intent = new Intent(ac,TopicDetailSlideActivity.class);
		intent.putExtra("data", Info.toBean(data));
		intent.putExtra("tilte", tilte);
		intent.putExtra("des", des);
		intent.putExtra("img_url", img_url);
		intent.putExtra("viewcount", viewcount);
		ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitleBar;	

    private ImageView mBackground;
    private SlidingMenu mSlidingMenu;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mNumViews;
    private ViewPager mViewPager;
    private TextView mPageIndicator;

    private TopicDetailAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding_detail);
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
		mTitleBar.findViewById(R.id.right_root).setVisibility(View.GONE);

        mBackground = (ImageView)findViewById(android.R.id.background);
        mSlidingMenu = (SlidingMenu)findViewById(R.id.sliding_menu);
        mSlidingMenu.showMenu();

        // Summary
        mTitle = (TextView) findViewById(R.id.detail_title);
        mNumViews = (TextView) findViewById(R.id.num_views);
        mDescription = (TextView) findViewById(R.id.description);

        // Description
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new TopicDetailAdapter();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

        mPageIndicator = (TextView) findViewById(R.id.page_indicator);
	}
	
	private String tilte;
	private String des;
	private String img_url;
	private String viewcount;
	private void initData(){
		try {
			Info data = (Info) getIntent().getSerializableExtra("data");
			tilte = getIntent().getStringExtra("tilte");
			des = getIntent().getStringExtra("des");
			img_url = getIntent().getStringExtra("img_url");
			viewcount = getIntent().getStringExtra("viewcount");
			
			mTitle.setText(tilte);
			mDescription.setText(des);
			mNumViews.setText(viewcount + "次浏览");
			ImageLoader.getInstance().displayImage(img_url, mBackground);
			mPagerAdapter.addAll(data.list);
			mPagerAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			android.util.Log.d("444", "e = " + e.toString());
		}
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
	
	private static class TopicDetailAdapter extends ArrayPagerAdapter<Info.Bean> {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.topic_detail_item, container, false);

            Info.Bean item = getItem(position);
            ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
            ImageLoader.getInstance().displayImage(item.image, image);
            UiUtil.findTextViewById(itemView, R.id.text).setText(item.des);

            UiUtil.findTextViewById(itemView, R.id.title).setText(item.title);
            Context context = itemView.getContext();
            String sale = !TextUtils.isEmpty(item.price_xm) ? context.getString(R.string.ren_ming_bi) + " " + item.price_xm : null;
            UiUtil.findTextViewById(itemView, R.id.sale).setText(sale);
            String price = !TextUtils.isEmpty(item.price_market) ? context.getString(R.string.ren_ming_bi) + " " + item.price_market : null;
            UiUtil.findTextViewById(itemView, R.id.price).setText(price);

            final String link = item.link;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsDetailActivity.startActivity((Activity) v.getContext(), link);
                }
            });

            container.addView(itemView);
            return itemView;
        }
    }

    // ==================================================================   Bean  ===================================================================

    private static class Info implements Serializable{
		private static final long serialVersionUID = 1001L;
		private String des;
    	private String title;
    	private String img;
    	private String viewcount;
    	private List<Bean> list;
    	
    	
    	
    	private static class Bean implements Serializable{
    		private static final long serialVersionUID = 1002L;
    		private String image;
    		private String title;
    		private String link;
    		private String des;
    		private String price_xm;
    		private String price_market;
			@Override
			public String toString() {
				return "Bean [image=" + image + ", title=" + title + ", link="
						+ link + ", des=" + des + ", price_xm=" + price_xm
						+ ", price_market=" + price_market +
						"]";
			}
    	}
    	
    	public  static Info toBean(String str){
    		Log.d("333", "str = " + str);
    		try {
        		JSONObject jsonObject = new JSONObject(str);
        		Info info = new Info();
        		List<Bean> list = new ArrayList<TopicDetailSlideActivity.Info.Bean>();
        		JSONArray jArray = jsonObject.getJSONArray("images");
        		List<Bean> beans = new ArrayList<TopicDetailSlideActivity.Info.Bean>();
        		for(int i = 0;i<jArray.length(); i++){
        			Bean bean = new Bean();
        			JSONObject js = jArray.getJSONObject(i);
        			bean.image = js.getString("image_6");
        			bean.title = js.getString("title");
        			bean.link = js.getString("link");
        			bean.des = js.getString("des");
        			bean.price_xm = js.getString("price_xm");
        			bean.price_market = js.getString("price_market");
        			beans.add(bean);
        		}
        		if(jsonObject.has("viewcount"))
        			info.viewcount = jsonObject.getString("viewcount");
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
    }
    
}
