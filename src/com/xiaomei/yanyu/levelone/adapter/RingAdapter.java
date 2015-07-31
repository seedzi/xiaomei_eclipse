package com.xiaomei.yanyu.levelone.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.leveltwo.BeautifulRingDetailsActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.DateUtils;
import com.xiaomei.yanyu.util.ScreenUtils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class RingAdapter extends BaseAdapter implements View.OnClickListener{
    
    private LayoutInflater mLayoutInflater;

    private List<BeautifulRing> mData;
    
    private Activity mActivity;
    
    public RingAdapter(Activity ac) {
        mActivity = ac;
        mLayoutInflater = LayoutInflater.from(ac);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_ring_layout, null);
            holder = new Holder();
            holder.userIconIv = (ImageView) convertView.findViewById(R.id.person_icon);
            holder.userNaemTv = (TextView) convertView.findViewById(R.id.person_name);
            holder.titleTv = (TextView) convertView.findViewById(R.id.title);
            holder.timeTv = (TextView) convertView.findViewById(R.id.time);
            holder.bubleSizeTv = (TextView) convertView.findViewById(R.id.buble_size);
            holder.likeSizeTv = (TextView) convertView.findViewById(R.id.like_size);
            holder.shareImg = (ImageView) convertView.findViewById(R.id.share_img);
            holder.descriptionTv = (TextView) convertView.findViewById(R.id.description);
            holder.shareButton = convertView.findViewById(R.id.share);
            convertView.setTag(holder);
            holder.shareImg.setOnClickListener(this);
            holder.shareButton .setOnClickListener(this);
        }
        holder = (Holder) convertView.getTag();
        attachDate(holder, mData.get(position));
        return convertView;
    }
    
    private void attachDate(Holder holder,BeautifulRing bean){
        holder.shareImg.setImageResource(R.drawable.ring_default_img);
        ImageLoader.getInstance().displayImage(bean.getShareFile(), holder.shareImg);
        ImageLoader.getInstance().displayImage(bean.getAvatar(),holder.userIconIv);
        FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams
                (LayoutParams.MATCH_PARENT, (int)(ScreenUtils.getScreenWidth(mActivity)*516/720));
        holder.shareImg.setLayoutParams(ll);
        holder.descriptionTv.setText(bean.getShareTitle());
        holder.userNaemTv.setText(bean.getUsername());
        holder.bubleSizeTv.setText(bean.getNumComments());
        holder.likeSizeTv.setText(bean.getNumFavors());
        holder.titleTv.setText(bean.getShareMark());
        holder.timeTv.setText(DateUtils.getTextByTime(mActivity, Long.valueOf(bean.getCreatedate()),R.string.date_fromate_anecdote));
        holder.shareImg.setTag(holder);
        holder.id = bean.getId();
        holder.share_type = bean.getShareType();
        holder.link = bean.getLink();
    }
    
    public void setData(List<BeautifulRing> data){
        mData = data;
    }
    
    public List<BeautifulRing> getData(){
        return mData;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.share:
//          SystemUtils.shareMsg(getActivity(), ""/*getActivity().getClass().getSimpleName()*/, "颜语", "小美医生", null);
            break;
        case R.id.share_img:
            try {
                if(((Holder)v.getTag()).share_type.equals("0")){
                    GoodsDetailActivity.startActivity(mActivity,((Holder)v.getTag()).link);
                }else{
                    BeautifulRingDetailsActivity.startActivity(mActivity,((Holder)v.getTag()).id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }

    }
    
    private class Holder{
        private ImageView shareImg;
        private ImageView userIconIv;
        private TextView userNaemTv;
        private TextView timeTv;
        private TextView titleTv;
        private TextView descriptionTv;
        private TextView likeSizeTv;
        private TextView bubleSizeTv;
        private View shareButton;
        private String id;
        private String share_type;
        private String link;
    }
    
}