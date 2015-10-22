package com.xiaomei.yanyu.levelone.adapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.bean.RecommendShares;
import com.xiaomei.yanyu.leveltwo.RecommendSharesDetailActivity;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.ImageUtils;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class RecommendSharesAdapter extends ArrayAdapter<RecommendShares> {
    
    public RecommendSharesAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView :
            LayoutInflater.from(getContext()).inflate(R.layout.recommend_shares_item, parent, false);

        final RecommendShares recommendShares = getItem(position);
        ImageLoader.getInstance().displayImage(recommendShares.getAvatar(), UiUtil.findImageViewById(itemView, R.id.avatar));
        UiUtil.findTextViewById(itemView, R.id.name).setText(recommendShares.getUsername());
        UiUtil.findTextViewById(itemView, R.id.createdate).setText(recommendShares.getFormatedDate());
        ImageLoader.getInstance().displayImage(recommendShares.getShareFile(), UiUtil.findImageViewById(itemView, R.id.share_img));
        UiUtil.findTextViewById(itemView, R.id.share_mark).setText(recommendShares.getShareMark());
        UiUtil.findTextViewById(itemView, R.id.share_title).setText(recommendShares.getShareTitle());
        
        ImageUtils.setViewPressState(UiUtil.findImageViewById(itemView, R.id.share_img));
        UiUtil.findImageViewById(itemView, R.id.share_img).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int shareType = recommendShares.getShareType();
                Activity activity = (Activity) v.getContext();
                if (shareType == RecommendShares.TYPE_DETAIL) {
                    String content = XiaoMeiApplication.getInstance().getResources().getString(R.string.share_circles_txt);
                    GoodsDetailActivity.startActivity(activity, recommendShares.getLink(),recommendShares.getShareTitle(),content,recommendShares.getShareFile());
                } else if (shareType == RecommendShares.TYPE_DISPLAY) {
                    RecommendSharesDetailActivity.startActivity(activity, recommendShares.getId());
                }
            }
        });
        return itemView;
    }
}