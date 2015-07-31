/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;


import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.util.DateUtils;

/**
 * Created by sunbreak on 7/25/15.
 */
public class UserShare {
    
    public static final int MAX_IMAGE_COUNT = 9;
    
    private String id;
    private String username;
    private String avatar;
    private String share_des;
    private String createdate;
    private int num_view;
    private String num_favors;
    private CommentPreview comments;
    private transient ShareImage[] images;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getShareDes() {
        return share_des;
    }

    public String getFormatedDate() {
        long time = System.currentTimeMillis();
        try {
            time = Long.valueOf(createdate);
        } catch (Exception e) {
        }
        return DateUtils.getTextByTime(
                XiaoMeiApplication.getInstance(),
                time,
                R.string.date_fromate_anecdote);//DateUtils.formateDate(Long.valueOf(createdate) * 1000);
    }

    public ShareImage[] getShareImages() {
        return images;
    }

    public void setShareImages(ShareImage[] images) {
        this.images = images;
    }

    public String getNumView() {
        return String.valueOf(num_view);
    }

    public String getNumFavors() {
        return num_favors;
    }

    public int getCommentCount() {
        return comments != null ? comments.total : 0;
    }

    public Comment[] getPreviewComments() {
        return comments != null ? comments.list : null;
    }

    public static class ShareImage {
        private String image_plus;

        public String getImage() {
            return image_plus;
        }
    }

    public static class CommentPreview {
        private int total;
        private Comment[] list;
    }

    public static class Comment {
        private String username;
        private String avatar;
        private String content;
        private String createdate;

        public String getUsername() {
            return username;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getContent() {
            return content;
        }

        public String getFormatedDate() {
            long time = System.currentTimeMillis();
            try {
                time = Long.valueOf(createdate);
            } catch (Exception e) {
            }
            return DateUtils.getTextByTime(
                    XiaoMeiApplication.getInstance(),
                    time,
                    R.string.date_fromate_anecdote);
//            return DateUtils.formateDate(Long.valueOf(createdate) * 1000);
        }
    }
}
