package com.xiaomei.yanyu.levelone.adapter;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.ArrayPagerAdapter;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.activity.AreaDetailActivity;
import com.xiaomei.yanyu.activity.AreaListActivity;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.bean.HomeItem.Item;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.widget.DotLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeAdapter2 extends ArrayAdapter<Object> implements View.OnClickListener{
	
	private final int LAYOUT_TYPE_TOPIC = 0; //热点轮播
	
	private final int LAYOUT_TYPE_RECOMMENDED_AREA = 1;//推荐地区
	
	private final int LAYOUT_TYPE_HOT_ITEMS = 2;//热门项目
	
	private final int LAYOUT_TYPE_CONSULTATION = 3;//咨询
	
	private final int LAYOUT_TYPE_PRODUCT_INTRODUCTION = 4;//产品 介绍
	
	private final int LAYOUT_TYPE_HOSP_INTRODUCTION = 5;//机构 介绍

	private final int LAYOUT_TYPE_SHARE = 6; //圈子精华分享
	
	private final int LAYOUT_TYPE_COUNT = 7;
	
	private List<HomeItem> mData = new ArrayList<HomeItem>();  //数据
	
	private AbsListView.LayoutParams mAbsLParams;
	
	private int mScreenWidth;
	
	private LayoutInflater mInflater;
	
	/**
	 * 热点adapter
	 */
	private TopicPageAdapter mTopicPageAdapter;
	
	/**
	 * 咨询adapter
	 */
	private ConsultationPageAdapter mConsultationPageAdapter;
	
    public List<HomeItem> getData() {
        return mData;
    }

    public void setData(List<HomeItem> mData) {
        this.mData = mData;
        mTopicPageAdapter.clear();
        mTopicPageAdapter.addAll(mData.get(0).getmList()); 
        
        mConsultationPageAdapter.clear();
        mConsultationPageAdapter.addAll(mData.get(3).getmList());
    }

    public HomeAdapter2(Context context) {
        super(context, 0);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mInflater = LayoutInflater.from(getContext());

        mTopicPageAdapter = new TopicPageAdapter();
        
        mConsultationPageAdapter = new ConsultationPageAdapter();
    }
	
	@Override
	public int getCount() {
//		return mData==null?0:mData.size();
		return 7;
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
				holder.mViewPager.setAdapter(mTopicPageAdapter);
				PageIndicator topicIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);
				topicIndicator.setViewPager(holder.mViewPager);
				break;
			case LAYOUT_TYPE_RECOMMENDED_AREA: //推荐地区
				List<Item>  areaList = mData.get(position).getmList();
				
			    DisplayImageOptions options = new DisplayImageOptions.Builder()
			        .showImageForEmptyUri(R.drawable.home_area_default)
			        .showImageOnLoading(R.drawable.home_area_default)
			        .showImageOnFail(R.drawable.home_area_default).build();
			    
				convertView = mInflater.inflate(R.layout.home_recommended_area_layout, null);
				convertView.findViewById(R.id.top).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        context.startActivity(new Intent(context, AreaListActivity.class));
                    }
                });
				holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
				holder.img2 = (ImageView) convertView.findViewById(R.id.img2);
				holder.img3 = (ImageView) convertView.findViewById(R.id.img3);
				holder.img4 = (ImageView) convertView.findViewById(R.id.img4);
				
				holder.img1.setTag(areaList.get(0).cityId);
				holder.img2.setTag(areaList.get(1).cityId);
				holder.img3.setTag(areaList.get(2).cityId);
				holder.img4.setTag(areaList.get(3).cityId);
				
				holder.img1.setOnClickListener(mHotCityItemClickListener);
				holder.img2.setOnClickListener(mHotCityItemClickListener);
				holder.img3.setOnClickListener(mHotCityItemClickListener);
				holder.img4.setOnClickListener(mHotCityItemClickListener);
				
				holder.cityCount1 = (TextView) convertView.findViewById(R.id.count1);
				holder.cityCount2 = (TextView) convertView.findViewById(R.id.count2);
				holder.cityCount3 = (TextView) convertView.findViewById(R.id.count3);
				holder.cityCount4 = (TextView) convertView.findViewById(R.id.count4);
				
				holder.cityName1 = (TextView) convertView.findViewById(R.id.city_name1);
				holder.cityName2 = (TextView) convertView.findViewById(R.id.city_name2);
				holder.cityName3 = (TextView) convertView.findViewById(R.id.city_name3);
				holder.cityName4 = (TextView) convertView.findViewById(R.id.city_name4);
				
				ImageLoader.getInstance().displayImage(areaList.get(0).img, holder.img1,options);
	    		ImageLoader.getInstance().displayImage(areaList.get(1).img, holder.img2,options);
	    		ImageLoader.getInstance().displayImage(areaList.get(2).img, holder.img3,options);
	    		ImageLoader.getInstance().displayImage(areaList.get(3).img, holder.img4,options);
	    		
                setOnAreaClickListener(holder.img1, areaList.get(0).cityId);
                setOnAreaClickListener(holder.img2, areaList.get(1).cityId);
                setOnAreaClickListener(holder.img3, areaList.get(3).cityId);
                setOnAreaClickListener(holder.img4, areaList.get(2).cityId);
	    		
	    		holder.cityCount1.setText(areaList.get(0).count);
	    		holder.cityCount2.setText(areaList.get(1).count);
	    		holder.cityCount3.setText(areaList.get(2).count);
	    		holder.cityCount4.setText(areaList.get(3).count);
	    		
	    		holder.cityName1.setText(areaList.get(0).city);
	    		holder.cityName2.setText(areaList.get(1).city);
	    		holder.cityName3.setText(areaList.get(2).city);
	    		holder.cityName4.setText(areaList.get(3).city);
	    		
	    		holder.recite = (ImageView) convertView.findViewById(R.id.recite);
	    		
	    		holder.moreClickView = convertView.findViewById(R.id.more_buton);
	    		holder.moreClickView.setOnClickListener(mHotCityMoreClickListener);
	    		ImageUtils.setViewPressState(holder.moreClickView);
	    		
	    		DisplayImageOptions reciteOptions = new DisplayImageOptions.Builder()
			        .showImageForEmptyUri(R.drawable.recommended_area_recite)
			        .showImageOnLoading(R.drawable.recommended_area_recite)
			        .showImageOnFail(R.drawable.recommended_area_recite).build();
	    		ImageLoader.getInstance().displayImage(mData.get(position).getRecite().img, holder.recite,reciteOptions);
	    		holder.recite.setTag(mData.get(position).getRecite().jump);
	    		holder.recite.setOnClickListener(mReciteClickListener);
	    		ImageUtils.setViewPressState(holder.recite);
				break;
			case LAYOUT_TYPE_HOT_ITEMS: //热门项目
		          DisplayImageOptions options1 = new DisplayImageOptions.Builder()
                  .showImageForEmptyUri(R.drawable.home_hot_default)
                  .showImageOnLoading(R.drawable.home_hot_default)
                  .showImageOnFail(R.drawable.home_hot_default).build();
				convertView = mInflater.inflate(R.layout.home_hot_items_layout, null);
				holder.scrollView = (HorizontalScrollView) convertView.findViewById(R.id.scroll);
				holder.horizontalLayout = (LinearLayout) convertView.findViewById(R.id.horizontal_layout);
				FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(mScreenWidth,mScreenWidth*260/720);
				holder.horizontalLayout.setLayoutParams(ll);
                    int spaceHorizontal = (int) convertView.getResources().getDimension(R.dimen.home_item_space_horizontal);
				for(HomeItem.Item item:mData.get(position).getmList()){
					 ImageView img = new ImageView(getContext());
					 LinearLayout.LayoutParams vl = new LinearLayout.LayoutParams((mScreenWidth*260/720)*208/260,
							 LinearLayout.LayoutParams.MATCH_PARENT);
					 vl.rightMargin = spaceHorizontal;
					img.setLayoutParams(vl);
					img.setScaleType(ScaleType.FIT_XY);
					img.setTag(item.goodsId);
					img.setOnClickListener(mHotitemsClickListener);
					ImageUtils.setViewPressState(img);
					holder.horizontalLayout .addView(img);   
					ImageLoader.getInstance().displayImage(item.img,img,options1);
				}
				break;
			case LAYOUT_TYPE_CONSULTATION://一对一咨询
				convertView = mInflater.inflate(R.layout.home_consultation_layout, null);
				holder.mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
				FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
				holder.mViewPager.setLayoutParams(fl);
				holder.mViewPager.setAdapter(mConsultationPageAdapter);
				PageIndicator consultationIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);
				consultationIndicator.setViewPager(holder.mViewPager);
				holder.recite = (ImageView) convertView.findViewById(R.id.recite);
				
				DisplayImageOptions reciteOptions1 = new DisplayImageOptions.Builder()
		        .showImageForEmptyUri(R.drawable.consultation_recite)
		        .showImageOnLoading(R.drawable.consultation_recite)
		        .showImageOnFail(R.drawable.consultation_recite).build();
	    		ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, holder.recite,reciteOptions1);
	    		holder.recite.setTag(mData.get(position).getRecite().jump);
	    		holder.recite.setOnClickListener(mReciteClickListener);
	    		ImageUtils.setViewPressState(holder.recite);
				break;
			case LAYOUT_TYPE_PRODUCT_INTRODUCTION: //产品介绍
               DisplayImageOptions options2 = new DisplayImageOptions.Builder()
                  .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
                  .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
                  .showImageOnFail(R.drawable.home_pro_hos_intr_default).build();
				convertView = mInflater.inflate(R.layout.home_product_intr_layout, null);
				holder.img1 = (ImageView) convertView.findViewById(R.id.img);
				holder.img1.setTag(mData.get(position).getmList().get(0).goodsId);
				holder.recite = (ImageView) convertView.findViewById(R.id.recite);
				LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenWidth(getContext())*730/720);
				holder.img1.setLayoutParams(ll1);
				ImageLoader.getInstance().displayImage(mData.get(position).getmList().get(0).img, holder.img1,options2);
				
				DisplayImageOptions reciteOptions2 = new DisplayImageOptions.Builder()
		        .showImageForEmptyUri(R.drawable.product_recite)
		        .showImageOnLoading(R.drawable.product_recite)
		        .showImageOnFail(R.drawable.product_recite).build();
				ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, holder.recite,reciteOptions2);
				holder.recite.setTag(mData.get(position).getRecite().jump);
	    		holder.recite.setOnClickListener(mReciteClickListener);
	    		ImageUtils.setViewPressState(holder.recite);
	    		
	    		holder.moreClickView = convertView.findViewById(R.id.more_buton);
	    		holder.moreClickView.setOnClickListener(mProductMoreClickListener);
	    		ImageUtils.setViewPressState(holder.moreClickView);
				break;
			case LAYOUT_TYPE_HOSP_INTRODUCTION: //机构介绍
               DisplayImageOptions options3 = new DisplayImageOptions.Builder()
                  .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
                  .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
                  .showImageOnFail(R.drawable.home_pro_hos_intr_default).build();
				convertView = mInflater.inflate(R.layout.home_hospital_intr_layout, null);
				holder.img1 = (ImageView) convertView.findViewById(R.id.img);
				holder.recite = (ImageView) convertView.findViewById(R.id.recite);
				LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenWidth(getContext())*730/720);
				holder.img1.setLayoutParams(ll2);
				ImageLoader.getInstance().displayImage(mData.get(position).getmList().get(0).img, holder.img1,options3);
				
				DisplayImageOptions reciteOptions3 = new DisplayImageOptions.Builder()
		        .showImageForEmptyUri(R.drawable.hospital_recite)
		        .showImageOnLoading(R.drawable.hospital_recite)
		        .showImageOnFail(R.drawable.hospital_recite).build();
				ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, holder.recite,reciteOptions3);
				holder.recite.setTag(mData.get(position).getRecite().jump);
	    		holder.recite.setOnClickListener(mReciteClickListener);
	    		ImageUtils.setViewPressState(holder.recite);
	    		
	    		holder.moreClickView = convertView.findViewById(R.id.more_buton);
	    		holder.moreClickView.setOnClickListener(mProductMoreClickListener);
	    		ImageUtils.setViewPressState(holder.moreClickView);
				break;
			case LAYOUT_TYPE_SHARE: //圈子精华分享
				convertView = mInflater.inflate(R.layout.home_share_layout, null);
				setupHomeShare(holder, (ViewGroup)convertView);
				break;
			default:
				break;
			}
		}
		return convertView;
	}

    public void setOnAreaClickListener(View itemView, final String cityId) {
        ImageUtils.setViewPressState(itemView);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, AreaDetailActivity.class).putExtra(IntentUtil.EXTRA_AREA_ID, Long.valueOf(cityId)));
            }
        });
    }

	@Override
	public int getItemViewType(int position) {
	    String type = mData.get(position).getType();
	    if(type.equals("header")){
	        return LAYOUT_TYPE_TOPIC;
	    }else if(type.equals("city")){
	        return LAYOUT_TYPE_RECOMMENDED_AREA;
	    }else if(type.equals("hot")){
	        return LAYOUT_TYPE_HOT_ITEMS;
	    }else if(type.equals("consult")){
	        return LAYOUT_TYPE_CONSULTATION;
	    }else if(type.equals("proj")){
	        return LAYOUT_TYPE_PRODUCT_INTRODUCTION;
	    }else if(type.equals("hosp")){
	        return LAYOUT_TYPE_HOSP_INTRODUCTION;
	    }else if(type.equals("share")){
	        return LAYOUT_TYPE_SHARE;
	    }
		return -1;
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
		private ImageView img1;
		private  ImageView img2;
		private  ImageView img3;
		private  ImageView img4;
		private LinearLayout horizontalLayout;
		private HorizontalScrollView scrollView;
		
		private ViewGroup item1;
		private ViewGroup item2;
		private ViewGroup item3;
		private ViewGroup item4;
		private ViewGroup item5;
		private ViewGroup item6;
		private ViewGroup item7;
		private ViewGroup item8;
		private ImageView recite;
		
		
		private TextView cityName1;
		private TextView cityName2;
		private TextView cityName3;
		private TextView cityName4;
		
		private TextView cityCount1;
		private TextView cityCount2;
		private TextView cityCount3;
		private TextView cityCount4;
		
		private View moreClickView;
	}

	private void setupHomeShare(Holder holder,ViewGroup convertView){
		holder.item1 = (ViewGroup) convertView.findViewById(R.id.item1);
		holder.item2 = (ViewGroup) convertView.findViewById(R.id.item2);
		holder.item3 = (ViewGroup) convertView.findViewById(R.id.item3);
		holder.item4 = (ViewGroup) convertView.findViewById(R.id.item4);
		holder.item5 = (ViewGroup) convertView.findViewById(R.id.item5);
		holder.item6 = (ViewGroup) convertView.findViewById(R.id.item6);
		holder.item7 = (ViewGroup) convertView.findViewById(R.id.item7);
		holder.item8 = (ViewGroup) convertView.findViewById(R.id.item8);
		
	    int img_width = (ScreenUtils.getScreenWidth(getContext())-60)/2;
		
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
		        img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rl.leftMargin = 20;
		rl.rightMargin = 10;
		rl.bottomMargin = 20;
		holder.item1.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.RIGHT_OF,R.id.item1);
		rl.leftMargin = 10;
		rl.rightMargin = 20;
		rl.bottomMargin = 20;
		holder.item2.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.BELOW,R.id.item1);
		rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rl.leftMargin = 20;
		rl.rightMargin = 10;
		rl.bottomMargin = 20;
		holder.item3.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.BELOW,R.id.item1);
		rl.addRule(RelativeLayout.RIGHT_OF,R.id.item3);
		rl.leftMargin = 10;
		rl.rightMargin = 20;
		rl.bottomMargin = 20;
		holder.item4.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.BELOW,R.id.item3);
		rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rl.leftMargin = 20;
		rl.rightMargin = 10;
		rl.bottomMargin = 20;
		holder.item5.setLayoutParams(rl);
	
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.RIGHT_OF,R.id.item5);
		rl.addRule(RelativeLayout.BELOW,R.id.item3);
		rl.leftMargin = 10;
		rl.rightMargin = 20;
		rl.bottomMargin = 20;
		holder.item6.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		rl.addRule(RelativeLayout.BELOW,R.id.item6);
		rl.leftMargin = 20;
		rl.rightMargin = 10;
		holder.item7.setLayoutParams(rl);
		
        rl = new RelativeLayout.LayoutParams(
                img_width,
                LinearLayout.LayoutParams.WRAP_CONTENT);
		rl.addRule(RelativeLayout.RIGHT_OF,R.id.item7);
		rl.addRule(RelativeLayout.BELOW,R.id.item6);
		rl.leftMargin = 10;
		rl.rightMargin = 20;
		holder.item8.setLayoutParams(rl);
		
		setupItem(holder.item1, mData.get(6).getmList(), 0);
		setupItem(holder.item2, mData.get(6).getmList(), 1);
		setupItem(holder.item3, mData.get(6).getmList(), 2);
		setupItem(holder.item4, mData.get(6).getmList(), 3);
		setupItem(holder.item5, mData.get(6).getmList(), 4);
		setupItem(holder.item6, mData.get(6).getmList(), 5);
		setupItem(holder.item7, mData.get(6).getmList(), 6);
		setupItem(holder.item8, mData.get(6).getmList(), 7);
	}
	
	private void setupItem(ViewGroup viewGroup , List<HomeItem.Item> list,int position){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.home_share_default)
        .showImageOnLoading(R.drawable.home_share_default)
        .showImageOnFail(R.drawable.home_share_default).build();
	    int img_width = (ScreenUtils.getScreenWidth(getContext())-60)/2;
	    int img_height = img_width*370/330;
	    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(img_width, img_height);
		ImageView img =  (ImageView) viewGroup.findViewById(R.id.img);
		img.setLayoutParams(ll);
		
		TextView description = (TextView) viewGroup.findViewById(R.id.description);
		ImageView icon = (ImageView) viewGroup.findViewById(R.id.icon);
		TextView username = (TextView) viewGroup.findViewById(R.id.user_name);
		TextView commentSize = (TextView) viewGroup.findViewById(R.id.size);
		ImageLoader.getInstance().displayImage(list.get(position).img,img,options);
		description.setText(list.get(position).title);
		username.setText(list.get(position).user);
		commentSize.setText(list.get(position).comments);
		img.setTag(list.get(position).shareId);
		img.setOnClickListener(mShareItemsClickListener);
		ImageUtils.setViewPressState(img);
		
		DisplayImageOptions options1 = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.user_head_default)
        .showImageOnLoading(R.drawable.user_head_default)
        .showImageOnFail(R.drawable.user_head_default).build();
		ImageLoader.getInstance().displayImage("",icon,options1);
	}
	
	/**
	 * 热点PageAdapter
	 */
	private class TopicPageAdapter extends ArrayPagerAdapter<HomeItem.Item>{
	    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.home_topic_item_default)
        .showImageOnLoading(R.drawable.home_topic_item_default)
        .showImageOnFail(R.drawable.home_topic_item_default).build();
	    public TopicPageAdapter(){
	    }
		@Override
		public Object instantiateItem(ViewGroup paramView, int paramInt) {
			 ImageView img = new ImageView(getContext());
			 ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					 ViewGroup.LayoutParams.MATCH_PARENT);
			img.setLayoutParams(vl);
			img.setScaleType(ScaleType.FIT_XY);
	        paramView.addView(img);  
			ImageLoader.getInstance().displayImage(getItem(paramInt).img,(ImageView)img,options);
			img.setTag(getItem(paramInt));
			img.setOnClickListener(mmTopicClickListener);
			ImageUtils.setViewPressState(img);
	        return img; 
		}
	}

	/**
     * 咨询PageAdapter
     */
	private class ConsultationPageAdapter extends ArrayPagerAdapter<HomeItem.Item>{
        DisplayImageOptions options2 = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.home_consultation_default)
        .showImageOnLoading(R.drawable.home_consultation_default)
        .showImageOnFail(R.drawable.home_consultation_default).build();
	    public ConsultationPageAdapter(){
	    }
		@Override
		public Object instantiateItem(ViewGroup paramView, int paramInt) {
			 ImageView img = new ImageView(getContext());
			 ViewGroup.LayoutParams vl = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					 ViewGroup.LayoutParams.MATCH_PARENT);
			img.setLayoutParams(vl);
			img.setScaleType(ScaleType.FIT_XY);
	        paramView.addView(img);  
			ImageLoader.getInstance().displayImage(getItem(paramInt).img,(ImageView)img,options2);
			img.setTag(getItem(paramInt).jump);
			img.setOnClickListener(mConsultationClickListener);
	        return img; 
		}
	}
	
	/**
	 * 热点轮播item点击事件
	 */
	private View.OnClickListener mmTopicClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
		    try {
    		    HomeItem.Item itme = (HomeItem.Item) arg0.getTag();
    		    Integer type = Integer.valueOf(itme.type);
    		    switch (type) {
                case 0://H5形式
                    
                    break;
                case 1: //卡片形式
                   
                        JSONObject jsonObject = new JSONObject(itme.list);
                        String viewcount = itme.viewcount;
                        String title =itme.title;
                        String des = jsonObject.optString("des");
                        String img = itme.img;
                        TopicDetailSlideActivity.startActivity((Activity)getContext(),itme.list, title ,des, img, viewcount);
    
                    break;
                default:
                    break;
                }
            } catch (Exception e) {
            }
		}
	};

	/**
	 * 热门地区 城市的点击事件，传过来的参数city_id
	 */
	private View.OnClickListener mHotCityItemClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
		}
	};
	
	/**
	 * 热门地区的更多点击事件
	 */
	private View.OnClickListener mHotCityMoreClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String s = (String) arg0.getTag();
		}
	};

	/**
	 * 每个item的背书项的点击事件
	 */
	private View.OnClickListener mReciteClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String url = (String) arg0.getTag();
			android.util.Log.d("333", "url = " + url);
		}
	};
	
	/**
	 * 热门项目的item的点击事件
	 */
	private View.OnClickListener mHotitemsClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String goodsId = (String) arg0.getTag();
			android.util.Log.d("333", "goodsId = " + goodsId);
		}
	};
	
	/**
	 * 一对一咨询的item点击事件
	 */
	private View.OnClickListener mConsultationClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String jump = (String) arg0.getTag();
			android.util.Log.d("333", "jump = " + jump);
		}
	};
	
	/**
	 * 产品介绍 更多 的点击事件
	 */
	private View.OnClickListener mProductMoreClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
		}
	};
	
	/**
	 * 机构介绍 更多 的点击事件
	 */
	private View.OnClickListener mHOSPMoreClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
		}
	};
	
	/**
	 * 圈子精华分享的item的点击事件
	 */
	private View.OnClickListener mShareItemsClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String shareId = (String) arg0.getTag();
			android.util.Log.d("333", "shareid = " + shareId);
		}
	};
	
	
	
	@Override
	public void onClick(View arg0) {
		
	}
}
