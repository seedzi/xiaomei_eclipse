/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;

/**
 * Created by sunbreak on 8/26/15.
 */
public class RecommendSharesDetail {

    private String share_title;
    private String image_6;
    private String username;
    private String avatar;
    private String share_des;
    private int num_favors;
    private Item[] images;

    public String getShareTitle() {
        return share_title;
    }

    public String getImageLarge() {
        return image_6;
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

    public int getNumViews() {
        return num_favors;
    }

    public Item[] getItems() {
        return images;
    }

    public class Item {

        private String title;
        private String image_6;

        public String getTitle() {
            return title;
        }

        public String getImageLarge() {
            return image_6;
        }
    }
}
