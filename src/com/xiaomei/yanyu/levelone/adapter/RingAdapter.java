package com.xiaomei.yanyu.levelone.adapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.leveltwo.BeautifulRingDetailsActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class RingAdapter extends ArrayAdapter<BeautifulRing> {
    
    public RingAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.item_ring_layout, parent, false);
        
        final BeautifulRing bean = getItem(position);
        ImageView shareImg = UiUtil.findImageViewById(itemView, R.id.share_img);
        shareImg.setImageResource(R.drawable.ring_default_img);
        ImageLoader.getInstance().displayImage(bean.getShareFile(), shareImg);
        ImageLoader.getInstance().displayImage(bean.getAvatar(),UiUtil.findImageViewById(itemView, R.id.person_icon));
        FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, (int)(ScreenUtils.getScreenWidth(getContext())*516/720));
        shareImg.setLayoutParams(ll);
        UiUtil.findTextViewById(itemView, R.id.description).setText(bean.getShareTitle());
        UiUtil.findTextViewById(itemView, R.id.person_name).setText(bean.getUsername());
        UiUtil.findTextViewById(itemView, R.id.buble_size).setText(bean.getNumComments());
        UiUtil.findTextViewById(itemView, R.id.like_size).setText(bean.getNumFavors());
        UiUtil.findTextViewById(itemView, R.id.title).setText(bean.getShareMark());
        UiUtil.findTextViewById(itemView, R.id.time).setText(DateUtils.getTextByTime(getContext(), Long.valueOf(bean.getCreatedate()),R.string.date_fromate_anecdote));
        
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equals(bean.getShareType())) {
                    GoodsDetailActivity.startActivity((Activity)getContext(), bean.getLink());
                } else {
                    BeautifulRingDetailsActivity.startActivity((Activity)getContext(), bean.getId());
                }
            }
        });
        return itemView;
    }
}