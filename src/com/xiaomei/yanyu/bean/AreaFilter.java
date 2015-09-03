/*
 * Copyright (c) 2015 Xiamei Inc. All rights reserved.
 */

package com.xiaomei.yanyu.bean;

/**
 * Created by sunbreak on 9/2/15.
 */
public class AreaFilter {

    public static final String COUNTRY = "country";
    public static final String GOODS_TYPE = "proj";

    private String label;
    private Item[] key;

    public Item[] getItems() {
        return key;
    }

    public class Item {
        private String label;
        private String key;

        public String getLabel() {
            return label;
        }
    }
}
