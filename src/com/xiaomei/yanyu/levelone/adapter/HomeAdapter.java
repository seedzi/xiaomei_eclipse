package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Section;
import com.xiaomei.yanyu.bean.Section.Entity;
import com.xiaomei.yanyu.leveltwo.RecommandSharesDetailActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.HomeStyle2;
import com.xiaomei.yanyu.leveltwo.WebViewActivity;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.UiUtil;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeAdapter extends ArrayAdapter<Section> {

    private static final int TYPE_TOPIC = 0;
    private static final int TOPIC_NAV = 1;
    private static final int TOPIC_RECOMMEND = 2;
    private static final int TOPIC_SHARE = 3;
    private static final int TOPIC_VIP = 4;

    private static final int DEFAULT_INDEX = 0;
    private static final int NAV_ASSURANCE = 0;
    private static final int NAV_PROCESS = 1;
    private static final int NAV_SURGERY = 2;
    private static final int NAV_ABOUT = 3;
	
	public HomeAdapter(Context context){
	    super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Section section = getItem(position);
		
		switch (getItemViewType(position)) {
		    case TYPE_TOPIC:
		        return getTopicView(section, convertView, parent);
		    case TOPIC_NAV:
		        return getNavView(section, convertView, parent);
		    case TOPIC_RECOMMEND:
		        return getRecommendView(section, convertView, parent);
		    case TOPIC_SHARE:
		        return getShareView(section, convertView, parent);
		    case TOPIC_VIP:
		        return getVipView(section, convertView, parent);
		}
		return null;
	}

    private View getTopicView(final Section section, View convertView, ViewGroup parent) {
	    View itemView = inflateView(R.layout.section_topic, convertView, parent);

	    AbsListView.LayoutParams ll = new AbsListView.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (ScreenUtils.getScreenWidth(getContext()) * 730 / 720)
                        + ScreenUtils.dip2px(getContext(), 10));
	    UiUtil.findViewById(itemView, R.id.root).setLayoutParams(ll);
	    
        final Entity entity = section.getList().get(DEFAULT_INDEX);
        ImageView icon = UiUtil.findImageViewById(itemView, R.id.icon);
        icon.setImageResource(R.drawable.home_default_img);
        ImageLoader.getInstance().displayImage(entity.getImg(), icon);
        icon.getLayoutParams().height = (int) (ScreenUtils.getScreenWidth(getContext())*730/720);
        
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                if (Section.TYPE_DETAIL.equals(section.getType())) {
                    GoodsDetailActivity.startActivity(activity, entity.getUrl());
                } else if (Section.TYPE_DISPLAY.equals(section.getType())) {
                    HomeStyle2.startActivity(activity, section.getList_String(), section.getTitle() , section.getDes(), entity.getImg(), section.getViewCount());
                }
            }
        });
        setOnClickListener(itemView, section);
        return itemView;
    }

    private View getNavView(Section section, View convertView, ViewGroup parent) {
        View itemView = inflateView(R.layout.section_nav, convertView, parent);

        List<Entity> list = section.getList();
        setNavOnClickListener(UiUtil.findViewById(itemView, R.id.youhui), list.get(NAV_ASSURANCE).getUrl());
        setNavOnClickListener(UiUtil.findViewById(itemView, R.id.payment), list.get(NAV_PROCESS).getUrl());
        setNavOnClickListener(UiUtil.findViewById(itemView, R.id.riji), list.get(NAV_SURGERY).getUrl());
        setNavOnClickListener(UiUtil.findViewById(itemView, R.id.jiangli), list.get(NAV_ABOUT).getUrl());
        return itemView;
    }

    private View getRecommendView(Section section, View convertView, ViewGroup parent) {
        View itemView = inflateView(R.layout.section_jingxuan, convertView, parent);

        Entity entity = section.getList().get(DEFAULT_INDEX);
        ImageView icon = UiUtil.findImageViewById(itemView, R.id.icon);
        icon.getLayoutParams().width = ScreenUtils.getScreenWidth(getContext())*430/720;
        icon.setImageResource(R.drawable.home_transverse_default_img);
        ImageLoader.getInstance().displayImage(entity.getImg(), icon);
        
        setNavOnClickListener(itemView, entity.getUrl());
        return itemView;
    }

    private View getShareView(final Section section, View convertView, ViewGroup parent) {
        View itemView = inflateView(R.layout.section_share, convertView, parent);

        Entity entity = section.getList().get(DEFAULT_INDEX);
        ImageView userIcon = UiUtil.findImageViewById(itemView, R.id.person_icon);
        ImageLoader.getInstance().displayImage(entity.getAvator(), userIcon);

        UiUtil.findTextViewById(itemView, R.id.person_name).setText(entity.getUsername());
        UiUtil.findTextViewById(itemView, R.id.time).setText(entity.getDate());

        ImageView icon = UiUtil.findImageViewById(itemView, R.id.icon);
        icon.getLayoutParams().height = ScreenUtils.getScreenWidth(getContext())*516/720;
        ImageLoader.getInstance().displayImage(entity.getImg(), icon);

        UiUtil.findTextViewById(itemView, R.id.title).setText(entity.getTitle());
        UiUtil.findTextViewById(itemView, R.id.buble_size).setText(entity.getNumcomment());
        UiUtil.findTextViewById(itemView, R.id.like_size).setText(entity.getNumfavorite());

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity)v.getContext();
                Entity entity = section.getList().get(DEFAULT_INDEX);
                if (Section.TYPE_DETAIL.equals(section.getType())) {
                    GoodsDetailActivity.startActivity(activity, entity.getUrl());
                } else if (Section.TYPE_DISPLAY.equals(section.getType())) {
                    RecommandSharesDetailActivity.startActivity(activity, entity.getShareId());
                }
            }
        });
        return itemView;
    }
    
    private View getVipView(Section section, View convertView, ViewGroup parent) {
        View itemView = inflateView(R.layout.section_vip, convertView, parent);

        Entity entity = section.getList().get(DEFAULT_INDEX);
        ImageView icon = UiUtil.findImageViewById(itemView, R.id.icon);
        icon.getLayoutParams().height = ScreenUtils.getScreenWidth(getContext())*428/720;
        icon.setImageResource(R.drawable.home_transverse_default_img);
        ImageLoader.getInstance().displayImage(entity.getImg(), icon);

        setOnClickListener(itemView, section);
        return itemView;
    }

    private View inflateView(int itemLayout, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView
                : LayoutInflater.from(getContext()).inflate(itemLayout, parent,
                        false);
        return itemView;
    }

    private void setNavOnClickListener(View view, final String url) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.startActivity((Activity) v.getContext(), url);
            }
        });
    }
    
    private void setOnClickListener(View view, final Section section) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity)v.getContext();
                Entity entity = section.getList().get(DEFAULT_INDEX);
                if (Section.TYPE_DETAIL.equals(section.getType())) {
                    GoodsDetailActivity.startActivity(activity, entity.getUrl());
                } else if (Section.TYPE_DISPLAY.equals(section.getType())) {
                    HomeStyle2.startActivity(activity, section.getList_String(), section.getTitle() , section.getDes(), entity.getImg(), section.getViewCount());
                }
            }
        });
    }

    @Override
	public int getViewTypeCount() {
		return 5;
	}
	
	@Override
	public int getItemViewType(int position) {
		Section section = getItem(position);
		if(section.getKey().equals("topic"))
			return TYPE_TOPIC;
		if(section.getKey().equals("nav"))
			return TOPIC_NAV;
		if(section.getKey().equals("jingxua"))
			return TOPIC_RECOMMEND;
		if(section.getKey().equals("share"))
			return TOPIC_SHARE;
		if(section.getKey().equals("vip"))
			return TOPIC_VIP;
		return TOPIC_VIP;
	}
}
