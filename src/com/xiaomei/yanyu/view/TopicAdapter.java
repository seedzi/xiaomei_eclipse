package com.xiaomei.yanyu.view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Topic;
import com.xiaomei.yanyu.bean.Topic.Segment;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.leveltwo.TopicDetailSlideActivity;
import com.xiaomei.yanyu.util.UiUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class TopicAdapter extends ArrayAdapter<Topic> {

    private DisplayImageOptions options;

    public TopicAdapter(Context context) {
        super(context, 0);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.home_pro_hos_intr_default)
                .showImageForEmptyUri(R.drawable.home_pro_hos_intr_default)
                .showImageOnFail(R.drawable.home_pro_hos_intr_default)
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView :
            LayoutInflater.from(getContext()).inflate(R.layout.topic_list_item, parent, false);
        
        Topic topic = getItem(position);
        ImageView image = UiUtil.findImageViewById(itemView, R.id.image);
        ImageLoader.getInstance().displayImage(topic.getImageLarge(), image, options);
        return itemView;
    }

    public static class TopicItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Topic topic = (Topic) parent.getItemAtPosition(position);
            Activity activity = (Activity) view.getContext();
            if (Topic.TYPE_DETAIL.equals(topic.getType())) {
                GoodsDetailActivity.startActivity(activity, topic.getUrl());
            } else if (Topic.TYPE_DISPLAY.equals(topic.getType())) {
                TopicDetailSlideActivity.startActivity(activity, new Gson().toJson(topic.getInfo()), topic.getTitle(),
                        topic.getInfo().getDes(), topic.getImageLarge(), String.valueOf(topic.getViewCount()));
            }
        }
    }
}
