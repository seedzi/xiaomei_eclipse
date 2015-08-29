package com.xiaomei.yanyu.levelone.adapter;


import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.YanYuUtils;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class HomeAdapter2 extends ArrayAdapter<Object> {
	
	private final int LAYOUT_TYPE_TOPIC = 0; //热点轮播
	
	private final int LAYOUT_TYPE_RECOMMENDED_AREA = 1;//推荐地区
	
	private final int LAYOUT_TYPE_HOT_ITEMS = 2;//热门项目
	
	private final int LAYOUT_TYPE_CONSULTATION = 3;//咨询
	
	private final int LAYOUT_TYPE_INTRODUCTION = 4;//产品 机构介绍

	private final int LAYOUT_TYPE_SHARE = 5; //圈子精华分享
	
	private final int LAYOUT_TYPE_COUNT = 6;
	
	private List<HomeItem> mData = new ArrayList<HomeItem>();  //数据
	
	private AbsListView.LayoutParams mAbsLParams;
	
	private int mScreenWidth;
	
	private LayoutInflater mInflater;
	
	/**
	 * 热点的view list
	 */
	private List<View> mTopicView = new ArrayList<View>(); 
	   /**
     * 咨询的view list
     */
    private List<View> mConsultationView = new ArrayList<View>();  
	/**
	 * 热点adapter
	 */
	private TopicPageAdapter mTopicPageAdapter;
	   /**
     * 热门项目adapter
     */
    private GalleryAdapter mGalleryAdapter;
	/**
	 * 咨询adapter
	 */
	private ConsultationPageAdapter mConsultationPageAdapter;

	
    public List<HomeItem> getData() {
        return mData;
    }

    public void setData(List<HomeItem> mData) {
        this.mData = mData;
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
		return mData==null?0:mData.size();
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
				break;
			case LAYOUT_TYPE_RECOMMENDED_AREA: //热门项目
				convertView = mInflater.inflate(R.layout.home_recommended_area_layout, null);
				holder.img1 = (ImageView) convertView.findViewById(R.id.img1);
				holder.img2 = (ImageView) convertView.findViewById(R.id.img2);
				holder.img3 = (ImageView) convertView.findViewById(R.id.img3);
				holder.img4 = (ImageView) convertView.findViewById(R.id.img4);
				
				RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams((mScreenWidth-10)/2, (mScreenWidth-10)*270/(2*355));
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rl.addRule(RelativeLayout.BELOW, R.id.top); 
				holder.img1.setLayoutParams(rl);
				
				rl = new RelativeLayout.LayoutParams((mScreenWidth-10)/2, (mScreenWidth-10)*270/(2*355));
	            rl.addRule(RelativeLayout.RIGHT_OF, R.id.img1); 
	            holder.img2.setLayoutParams(rl);
	            
	            rl = new RelativeLayout.LayoutParams((mScreenWidth-10)/2, (mScreenWidth-10)*270/(2*355));
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rl.addRule(RelativeLayout.BELOW, R.id.img1); 
                rl.topMargin =10;
                holder.img3.setLayoutParams(rl);
	            
                rl = new RelativeLayout.LayoutParams((mScreenWidth-10)/2, (mScreenWidth-10)*270/(2*355));
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rl.addRule(RelativeLayout.RIGHT_OF, R.id.img3); 
                holder.img4.setLayoutParams(rl);
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
				holder.gallery = (RecyclerView) convertView.findViewById(R.id.recycler);
				
				break;
			default:
				break;
			}
		}
		return convertView;
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
	        return LAYOUT_TYPE_INTRODUCTION;
	    }else if(type.equals("hosp")){
	        return LAYOUT_TYPE_INTRODUCTION;
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
		private android.support.v7.widget.RecyclerView  gallery;
	}
	
	
	
	/**
	 * 热点PageAdapter
	 */
	private static class TopicPageAdapter extends PagerAdapter{
	    
	    protected SparseArray<View> mViews; 
	    
	    public TopicPageAdapter(){
	        mViews = new SparseArray<View>();
	    }
	    
	    private List<HomeItem.Item> data;
	    
		public List<HomeItem.Item> getData() {
            return data;
        }
        public void setData(List<HomeItem.Item> data) {
            this.data = data;
        }

        @Override
		public int getCount() {
			return data==null?0:data.size();
		}

		@Override
		public void startUpdate(View paramView) {
		}

		@Override
		public Object instantiateItem(View paramView, int paramInt) {
		    View view = mViews.get(paramInt);  
	        if (view == null) {  
	            view = newView(paramInt);  
	            mViews.put(paramInt, view);  
	        }  
	        ((ViewGroup)paramView).addView(view);  
	        return view; 
		}
		
		 public  View newView(int position){
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
		    return paramView == paramObject;
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
	 * 热门项目adapter
	 */
    public static class GalleryAdapter extends
            RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        private List<String> mDatas;

        public GalleryAdapter(Context context, List<String> datats) {
            mInflater = LayoutInflater.from(context);
            mDatas = datats;
        }
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
            }
            ImageView mImg;
            TextView mTxt;
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = mInflater.inflate(R.layout.gallery_item_layout,
                    viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.mImg = (ImageView) view
                    .findViewById(R.id.img);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
//            viewHolder.mImg.setImageResource(mDatas.get(i));
            ImageLoader.getInstance().displayImage(mDatas.get(i), viewHolder.mImg);
        }

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
}
