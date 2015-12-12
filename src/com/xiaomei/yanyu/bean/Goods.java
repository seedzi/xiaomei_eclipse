package com.xiaomei.yanyu.bean;

import java.util.List;

public class Goods {
	
	private String id;
	
	private String hosp_id;
	
	private String title;
	
	private String des;
	
	private String price_market;
	
	private String price_xm;
	
	private String price_front;
	
	private String is_front;
	
	private String file_url;
	
	private String hosp_name;
	
	private String sales;
	
	private String city_name;
	
	private List<Mark> goods_mark;
	
	private List<AvailCoupon> avail_coupons;
	
	public String getId() {
		return id;
	}

	public String getHospId() {
		return hosp_id;
	}

	public String getTitle() {
		return title;
	}

	public String getDes() {
		return des;
	}

	public String getPriceMarket() {
		return price_market;
	}

	public String getPriceXm() {
		return price_xm;
	}

	public String getPriceFront() {
		return price_front;
	}

	public String getIsFront() {
		return is_front;
	}

	public String getFileUrl() {
		return file_url;
	}

	public String getHospName() {
		return hosp_name;
	}

	public String getSales() {
		return sales;
	}

	public String getCityName() {
		return city_name;
	}

	public List<AvailCoupon> getAvailCoupons(){
	    return avail_coupons;
	}
	
	public List<Mark> getMarks() {
		return goods_mark;
	}

	public static class Mark{
		private String color;
		private String label;
		public String getColor() {
			return color;
		}

		public String getLabel() {
			return label;
		}
	}
	/*
    "id": "60",
    "expire": "2016-04-01",
    "beg": "2015-10-01",
    "couponid": "4",
    "uptime": "2015-12-11 15:02:18",
    "base": "100",
    "userid": "19062558",
    "display": "通用测试优惠劵",
    "code": "common",
    "used": "0",
    "type": "1",
    "discount": "10"*/
	private static class AvailCoupon{
	    private String id;
	    private String expire;
	    private String beg;
	    private String couponid;
	    private String uptime;
	    private String base;
	    private String userid;
	    private String display;
	    private String code;
	    private String used;
	    private String type;
	    private String discount;
	    
	    public String getId(){
	        return id;
	    }
	    public String getExpire(){
	        return expire;
	    }
	    public String getBeg(){
	        return beg;
	    }
	    
	    public String getCouponid(){
	        return couponid;
	    }
	    
	    public String getUptime(){
	        return uptime;
	    }
	    
	    public String getBase(){
	        return base;
	    }
	    
	    public String getUserid(){
	        return userid;
	    }
	    
	    public String getDisplay(){
	        return display;
	    }
	    
	    public String getCode(){
	        return code;
	    }
	    
	    public String getUsed(){
	        return used;
	    }
	    
	    public String getType(){
	        return type;
	    }
	    
	    public String getDiscount(){
	        return discount;
	    }
	}
}