/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;

import com.xiaomei.yanyu.util.DateUtils;

/**
 * Created by sunbreak on 7/25/15.
 */
public class RecommendShares {

    public static final int TYPE_DETAIL = 0;
    public static final int TYPE_DISPLAY = 1;

    private String id;
    private String username;
    private String avatar;
    private String share_title;
    private String image_6;
    private String share_mark;
    private int share_type;
    private String link;
    private String createdate;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFormatedDate() {
        return DateUtils.formateDate(Long.valueOf(createdate) * 1000);
    }

    public String getShareTitle() {
        return share_title;
    }

    public String getShareFile() {
        return image_6;
    }

    public String getShareMark() {
        return share_mark;
    }
    
    public int getShareType() {
        return share_type;
    }
    
    public String getLink() {
        return link;
    }
}
