/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;

/**
 * Created by sunbreak on 9/2/15.
 */
public class Area {
    private long cityid;
    private String city;
    private String img_6;
    private String desc;
    private String special;
    private int count;

    public long getId() {
        return cityid;
    }

    public String getImageLarge() {
        return img_6;
    }

    public String getName() {
        return city;
    }

    public int getCount() {
        return count;
    }

    public String getDescription() {
        return desc;
    }

    public String getSpecial() {
        return special;
    }
}
