package com.xiaomei.yanyu.levelone.adapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.bean.UserShare.Comment;
import com.xiaomei.yanyu.bean.UserShare.ShareImage;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.util.UiUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;

public class UserShareAdapter extends ArrayAdapter<UserShare> {

    private DisplayImageOptions mImageOption;

    public UserShareAdapter(Context context) {
        super(context, 0);
        mImageOption = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.tiezi_zhanwei).build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.item_user_post_layout, parent, false);
        
        final UserShare item = getItem(position);
        
        ImageView userIcon = UiUtil.findImageViewById(itemView, R.id.poster_user_icon);
        userIcon.setImageResource(R.drawable.user_head_default);
        ImageLoader.getInstance().displayImage(item.getAvatar(), userIcon);
        UiUtil.findTextViewById(itemView, R.id.poster_user_name).setText(item.getUsername());
        UiUtil.findTextViewById(itemView, R.id.poster_content).setText(item.getShareDes());
        UiUtil.findTextViewById(itemView, R.id.poster_user_time).setText(item.getFormatedDate());
        
        
        inflateImages(itemView, item.getShareImages());
        
        View commentLayout1 = UiUtil.findViewById(itemView, R.id.commont_1);
        View commentLayout2 = UiUtil.findViewById(itemView, R.id.commont_2);
        View commentLayout3 = UiUtil.findViewById(itemView, R.id.commont_3);
        commentLayout1.setVisibility(View.GONE);
        commentLayout2.setVisibility(View.GONE);
        commentLayout3.setVisibility(View.GONE);
        Comment[] comments =  item.getPreviewComments();
        int commentsSize = comments != null ? comments.length : 0;
        for (int j = 0; j < 3 && j < commentsSize; j++) {
            View commentLayout; 
            switch (j) {
                case 0:
                    commentLayout = commentLayout1;
                    break;
                case 1:
                    commentLayout = commentLayout2;
                    break;
                case 2:
                    commentLayout = commentLayout3;
                    break;
                default:
                    throw new IllegalStateException("More than 3 comments");
            }
            commentLayout.setVisibility(View.VISIBLE);
            ImageView commentUserIcon = UiUtil.findImageViewById(commentLayout, R.id.user_icon);
            Comment comment = comments[j];
            ImageLoader.getInstance().displayImage(comment.getAvatar(), commentUserIcon);
            UiUtil.findTextViewById(commentLayout, R.id.user_name)
            .setText(comment.getUsername());
            UiUtil.findTextViewById(commentLayout, R.id.content).setText(comment.getContent());
            UiUtil.findTextViewById(commentLayout, R.id.time).setText(comment.getFormatedDate());
        }
        
        View moreComment = UiUtil.findViewById(itemView, R.id.more_commont);
        moreComment.setVisibility(commentsSize >= 3 ? View.VISIBLE : View.GONE);
        moreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentListActivity.startActivity(getContext(),"share", item.getId(), true, false);
            }
        });
        
        UiUtil.findTextViewById(itemView, R.id.browse_size).setText(item.getNumView());
        UiUtil.findTextViewById(itemView, R.id.fav_size).setText(item.getNumFavors());
        UiUtil.findTextViewById(itemView, R.id.comment_size).setText(String.valueOf(item.getCommentCount()));
        
        View commentSize = UiUtil.findViewById(itemView, R.id.comment_size);
        commentSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentListActivity.startActivity(getContext(),"share", item.getId(), true, true);
            }
        });
        return itemView ;
    }
    
    private void inflateImages(View itemView, ShareImage[] shareImages) {
        GridLayout gridLayout = UiUtil.findById(itemView, R.id.share_images);
        gridLayout.removeAllViews();
        if (shareImages == null || shareImages.length == 0) {
            gridLayout.setVisibility(View.GONE);
        } else {
            gridLayout.setVisibility(View.VISIBLE);
            int length = shareImages.length;
            int imageLayout = length == 1 ? R.layout.user_shares_image_item_large : R.layout.user_shares_image_item;
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (int i = 0; i < length &&  i < UserShare.MAX_IMAGE_COUNT; i++) {
                ImageView imageView = (ImageView) inflater.inflate(imageLayout, gridLayout, false);
                gridLayout.addView(imageView);
                if(TextUtils.isEmpty(shareImages[i].getImage())){
                    imageView.setImageResource(R.drawable.tiezi_zhanwei);
                }else{
                    ImageLoader.getInstance().displayImage(shareImages[i].getImage(), imageView, mImageOption);
                }
            }
        }
    }
}
