/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;

/**
 * Created by sunbreak on 7/20/15.
 */
public class Merchant {
    private long id;
    private String hosp_name;
    private String image_6;
    private String hosp_des;
    private String tel;
    private String addr;
    private int rate_service;
    private int rate_effect;
    private int rate_environment;

    public long getId() {
        return id;
    }

    public String getName() {
        return hosp_name;
    }

    public String getImageLarge() {
        return image_6;
    }

    public String getDescription() {
        return hosp_des;
    }

    public String getAddr() {
        return addr;
    }

    public int getRateService() {
        return rate_service;
    }

    public int getRateEffect() {
        return rate_effect;
    }

    public int getRateEnvironment() {
        return rate_environment;
    }
}
