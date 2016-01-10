package com.xiaomei.yanyu.bean;

import java.io.Serializable;

import com.xiaomei.yanyu.R;

public class Coupon implements Serializable{

	private static final long serialVersionUID = 101L;
	
    // 可用
    public static final int STATUS_ENABLED = 0;

    // 尚未开始
    public static final int STATUS_NOT_BEGIN = 1;

    // 已使用
    public static final int STATUS_USED = 2;

    // 已过期
    public static final int STATUS_EXPIRED = 3;

	public String id = "";
    public String userid = "";
    public String couponid = "";
    public String code = "";
    public String display = "";
    public String base = "";
    public String discount = "";
    public String beg = "";
    public String expire = "";
    public String type = "";
    public String used = "";
    public String uptime = "";

    public int status;

    public int getStatusDisplayRes() {
        switch (status) {
            case STATUS_ENABLED:
                return R.string.coupon_status_enabled;
            case STATUS_NOT_BEGIN:
                return R.string.coupon_status_not_begin;
            case STATUS_USED:
                return R.string.coupon_status_used;
            case STATUS_EXPIRED:
                return R.string.coupon_status_expired;
        }
        return 0;
    }
}
