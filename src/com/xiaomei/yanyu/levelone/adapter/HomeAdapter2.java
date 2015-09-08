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
import com.xiaomei.yanyu.activity.TopicListActivity;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.bean.HomeItem.Item;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.RecommendSharesDetailActivity;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.ImageLoaderUtil;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.IntentUtil;
import com.xiaomei.yanyu.util.ScreenUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
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
	
    private static final int AREA_COUNT = 4;

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
                    convertView = getTopicViewPager();
				break;
			case LAYOUT_TYPE_RECOMMENDED_AREA: //推荐地区
                    convertView = getRecommendArea(position, parent);
				break;
			case LAYOUT_TYPE_HOT_ITEMS: //热门项目
                    convertView = getHotGoods(position);
				break;
			case LAYOUT_TYPE_CONSULTATION://一对一咨询
                    convertView = getConsultationViewPager(position);
				break;
			case LAYOUT_TYPE_PRODUCT_INTRODUCTION: //产品介绍
                    convertView = getGoodsTopic(position);
				break;
			case LAYOUT_TYPE_HOSP_INTRODUCTION: //机构介绍
               DisplayImageOptions options3 = new DisplayImageOptions.Builder()
                  .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
                  .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
                  .showImageOnFail(R.drawable.home_pro_hos_intr_default).build();
				convertView = mInflater.inflate(R.layout.home_hospital_intr_layout, null);
				convertView.findViewById(R.id.top).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        TopicListActivity.startActivity((Activity) context, context.getString(R.string.title_merchant_topic), HttpUrlManager.MERCHANT_TOPIC_LIST);
                    }
                });
				holder.img1 = (ImageView) convertView.findViewById(R.id.img);
				holder.recite = (ImageView) convertView.findViewById(R.id.recite);
				LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenWidth(getContext())*730/720);
				holder.img1.setLayoutParams(ll2);
				ImageLoader.getInstance().displayImage(mData.get(position).getmList().get(0).img, holder.img1,options3);
				ImageUtils.setViewPressState(holder.img1);
				holder.img1.setOnClickListener(mHOSPItemClickListener);
				holder.img1.setTag(mData.get(position).getmList().get(0));
				
				DisplayImageOptions reciteOptions3 = new DisplayImageOptions.Builder()
		        .showImageForEmptyUri(R.drawable.hospital_recite)
		        .showImageOnLoading(R.drawable.hospital_recite)
		        .showImageOnFail(R.drawable.hospital_recite).build();
				ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, holder.recite,reciteOptions3);
				holder.recite.setTag(mData.get(position).getRecite().jump);
	    		holder.recite.setOnClickListener(mReciteClickListener);
	    		ImageUtils.setViewPressState(holder.recite);
	    		
	    		holder.moreClickView = convertView.findViewById(R.id.more_buton);
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

    private View getGoodsTopic(int position) {
        View convertView;
        DisplayImageOptions options2 = new DisplayImageOptions.Builder()
              .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
              .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
              .showImageOnFail(R.drawable.home_pro_hos_intr_default).build();
        	convertView = mInflater.inflate(R.layout.home_product_intr_layout, null);
        	convertView.findViewById(R.id.top).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    TopicListActivity.startActivity((Activity) context, context.getString(R.string.title_goods_topic), HttpUrlManager.GOODS_TOPIC_LIST);
                }
            });
        	ImageView img1 = (ImageView) convertView.findViewById(R.id.img);
        	img1.setTag(mData.get(position).getmList().get(0).goodsId);
        	ImageView recite = (ImageView) convertView.findViewById(R.id.recite);
        	LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(getContext()), ScreenUtils.getScreenWidth(getContext())*730/720);
        	img1.setLayoutParams(ll1);
        	ImageLoader.getInstance().displayImage(mData.get(position).getmList().get(0).img, img1,options2);
            ImageUtils.setViewPressState(img1);
            img1.setOnClickListener(mProductItemClickListener);
            img1.setTag(mData.get(position).getmList().get(0));
        	
        	DisplayImageOptions reciteOptions2 = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.product_recite)
            .showImageOnLoading(R.drawable.product_recite)
            .showImageOnFail(R.drawable.product_recite).build();
        	ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, recite,reciteOptions2);
        	recite.setTag(mData.get(position).getRecite().jump);
        	recite.setOnClickListener(mReciteClickListener);
        	ImageUtils.setViewPressState(recite);
        return convertView;
    }

    private View getConsultationViewPager(int position) {
        View convertView;
        convertView = mInflater.inflate(R.layout.home_consultation_layout, null);
        ViewPager mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
        FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
        mViewPager.setLayoutParams(fl);
        mViewPager.setAdapter(mConsultationPageAdapter);
        PageIndicator consultationIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);
        consultationIndicator.setViewPager(mViewPager);
        ImageView recite = (ImageView) convertView.findViewById(R.id.recite);
        
        DisplayImageOptions reciteOptions1 = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.consultation_recite)
        .showImageOnLoading(R.drawable.consultation_recite)
        .showImageOnFail(R.drawable.consultation_recite).build();
        ImageLoader.getInstance().displayImage(mData.get(position).getRecite().jump, recite,reciteOptions1);
        recite.setTag(mData.get(position).getRecite().jump);
        recite.setOnClickListener(mReciteClickListener);
        ImageUtils.setViewPressState(recite);
        return convertView;
    }

    private View getTopicViewPager() {
        View convertView;
        convertView = mInflater.inflate(R.layout.home_topic_layout, null);
        ViewPager mViewPager = (ViewPager) convertView.findViewById(R.id.pager);
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScreenWidth*335/720);
        mViewPager.setLayoutParams(flp);
        mViewPager.setAdapter(mTopicPageAdapter);
        PageIndicator topicIndicator = (PageIndicator) convertView.findViewById(R.id.indicator);
        topicIndicator.setViewPager(mViewPager);
        return convertView;
    }

    private View getRecommendArea(int position, ViewGroup parent) {
        View convertView;
        HomeItem homeItem = mData.get(position);
        List<Item>  areaList = homeItem.getmList();
        
        convertView = mInflater.inflate(R.layout.home_recommend_area, parent, false);
        View itemTitle = convertView.findViewById(R.id.home_item_title);
        itemTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, AreaListActivity.class));
            }
        });
        ((TextView) itemTitle.findViewById(android.R.id.title)).setText(R.string.home_item_recommend_area);
        
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = ImageLoaderUtil.getDisplayOptions(R.drawable.home_area_default);
        View[] thumbs = new View[]{convertView.findViewById(R.id.thumb1), convertView.findViewById(R.id.thumb2),
                convertView.findViewById(R.id.thumb3), convertView.findViewById(R.id.thumb4)};
        Context context = convertView.getContext();
        for (int i = 0; i < AREA_COUNT; i++) {
            Item item = areaList.get(i);
            View areaItemThumb = thumbs[i];
            ImageView image = (ImageView) areaItemThumb.findViewById(R.id.image);
            imageLoader.displayImage(item.img, image, options);
            setOnAreaClickListener(image, item.cityId);
            ((TextView) areaItemThumb.findViewById(R.id.name)).setText(item.city);
            ((TextView) areaItemThumb.findViewById(R.id.goods_count)).setText(context.getString(R.string.area_goods_count, item.count));
        }
        
        ImageView recite = (ImageView) convertView.findViewById(R.id.recite);
        imageLoader.displayImage(homeItem.getRecite().img, recite,
                ImageLoaderUtil.getDisplayOptions(R.drawable.recommended_area_recite));
        recite.setTag(homeItem.getRecite().jump);
        recite.setOnClickListener(mReciteClickListener);
        ImageUtils.setViewPressState(recite);
        return convertView;
    }

    private View getHotGoods(int position) {
        View convertView;
        DisplayImageOptions options1 = new DisplayImageOptions.Builder()
          .showImageForEmptyUri(R.drawable.home_hot_default)
          .showImageOnLoading(R.drawable.home_hot_default)
          .showImageOnFail(R.drawable.home_hot_default).build();
        convertView = mInflater.inflate(R.layout.home_hot_items_layout, null);
        LinearLayout horizontalLayout = (LinearLayout) convertView.findViewById(R.id.horizontal_layout);
        FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(mScreenWidth,mScreenWidth*260/720);
        horizontalLayout.setLayoutParams(ll);
            int spaceHorizontal = (int) convertView.getResources().getDimension(R.dimen.home_item_space_horizontal);
        for(HomeItem.Item item:mData.get(position).getmList()){
             ImageView img = new ImageView(getContext());
             LinearLayout.LayoutParams vl = new LinearLayout.LayoutParams((mScreenWidth*260/720)*208/260,
                     LinearLayout.LayoutParams.MATCH_PARENT);
             vl.rightMargin = spaceHorizontal;
            img.setLayoutParams(vl);
            img.setScaleType(ScaleType.FIT_XY);
            img.setTag(item);
            img.setOnClickListener(mHotitemsClickListener);
            ImageUtils.setViewPressState(img);
            horizontalLayout .addView(img);   
            ImageLoader.getInstance().displayImage(item.img,img,options1);
        }
        return convertView;
    }

	/**
	 * 热门城市item点击
	 */
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
		img.setTag(list.get(position));
		img.setOnClickListener(mShareItemsClickListener);
		ImageUtils.setViewPressState(img);
		
		DisplayImageOptions options1 = new DisplayImageOptions.Builder()
        .showImageForEmptyUri(R.drawable.user_head_default)
        .showImageOnLoading(R.drawable.user_head_default)
        .showImageOnFail(R.drawable.user_head_default).build();
		ImageLoader.getInstance().displayImage(list.get(position).avatar,icon,options1);
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
			ImageUtils.setViewPressState(img);
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
                	if(TextUtils.isEmpty(itme.goodsId)){
                		GoodsDetailActivity.startActivity((Activity)getContext(),itme.url);
                	}else{
                		GoodsDetailActivity.startActivity((Activity)getContext(),itme.url,itme.goodsId);
                	}
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
	 * 每个item的背书项的点击事件
	 */
	private View.OnClickListener mReciteClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String url = (String) arg0.getTag();
			GoodsDetailActivity.startActivity((Activity)getContext(), url);
		}
	};
	
	/**
	 * 热门项目的item的点击事件
	 */
	private View.OnClickListener mHotitemsClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			HomeItem.Item item = (Item) arg0.getTag();
			GoodsDetailActivity.startActivity((Activity)getContext(), item.url,item.goodsId);
		}
	};
	
	/**
	 * 一对一咨询的item点击事件
	 */
	private View.OnClickListener mConsultationClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String jump = (String) arg0.getTag();
			GoodsDetailActivity.startActivity((Activity)getContext(), jump);
		}
	};
	/**
     * 产品item 的点击事件
     */
	private View.OnClickListener mProductItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	try {
                HomeItem.Item itme = (HomeItem.Item) v.getTag();
                Integer type = Integer.valueOf(itme.type);
                switch (type) {
				case 0: //h5
					GoodsDetailActivity.startActivity((Activity)getContext(),itme.url,itme.goodsId);
					break;
				case 1:
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
     * 机构item 的点击事件
     */
    private View.OnClickListener mHOSPItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                HomeItem.Item itme = (HomeItem.Item) v.getTag();
                Integer type = Integer.valueOf(itme.type);
                switch (type) {
				case 0: //h5
					GoodsDetailActivity.startActivity((Activity)getContext(),itme.url);
					break;
				case 1:
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
	 * 圈子精华分享的item的点击事件
	 */
	private View.OnClickListener mShareItemsClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
	          try {
	                HomeItem.Item itme = (HomeItem.Item) arg0.getTag();
	                Integer type = Integer.valueOf(itme.type);
	                switch (type) {
	                case 0://H5形式
	                      GoodsDetailActivity.startActivity((Activity)getContext(),itme.url);
	                    break;
	                case 1: //卡片形式
	                        RecommendSharesDetailActivity.startActivity((Activity)getContext(), itme.shareId);
	                    break;
	                default:
	                    break;
	                }
	            } catch (Exception e) {
	            }
		}
	};
	
	
	
	@Override
	public void onClick(View arg0) {
		
	}
}
