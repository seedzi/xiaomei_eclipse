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
	
	private String avail_coupons;

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
}