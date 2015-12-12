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
	
	private String hosp_tel;
	
	private List<Mark> goods_mark;
	
	private List<Coupon> avail_coupons;
	
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
	
	public String getHospTel(){
		return hosp_tel;
	}

	public List<Coupon> getAvailCoupons(){
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
	{
	    "msg": {
	        "des": "MTS微针+射频+超声，三合一",
	        "city_name": "首尔",
	        "rate_avg": "96",
	        "is_open": "1",
	        "is_delete": "0",
	        "is_front": "0",
	        "hosp_name": "狎鸥亭YK整形外科",
	        "file_url": "http:\/\/static.drxiaomei.com\/images\/goods1030\/goods_thumb_1030_200x184.jpg",
	        "city": "3",
	        "rate_environment": "4",
	        "cat_name": "再生",
	        "cat_id": "55",
	        "id": "1030",
	        "title": "【再生】MTS微针+射频+超声，三合一",
	        "sales": "10",
	        "price_xm": "1400",
	        "goods_type": "0",
	        "origin_place": "2",
	        "goods_mark": [
	            
	        ],
	        "avail_coupons": [
	            {
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
	                "discount": "10"
	            }
	        ],
	        "price_market": "2200",
	        "rate_effect": "5",
	        "parent_cat_id": "6",
	        "content": "<!-- 术前术后对比 -->\n<div class=\"col-mod contrast\">\n\t<div class=\"title\">\n\t\t<h3>\n\t\t\t术前术后对比\n\t\t<\/h3>\n\t<\/div>\n\t<div class=\"con clearfix\">\n\t\t<p class=\"intro\">\n\t\t\t当前很多皮肤管理会损伤皮肤表皮。而MTS仪器由细微针头构成，通过直接刺激皮肤真皮层，引导胶原蛋白的再生。皮肤表皮受到的影响被最小化。MTS对较大面积的斑痕、粉刺或毛孔粗大的问题有非常显著的效果。\n\t\t<\/p>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150503173047_80903.jpg\" \/> \n\t<\/div>\n<\/div>\n<!-- 产品介绍 -->\n<div class=\"col-mod pro-intro\">\n\t<div class=\"title\">\n\t\t<h3>\n\t\t\t产品介绍\n\t\t<\/h3>\n\t<\/div>\n\t<div class=\"con\">\n\t\t<h4 class=\"sub-title\">\n\t\t\tYK倾心打造，MTS微针+射频超声皮肤管理\n\t\t<\/h4>\n\t\t<p class=\"intro\">\n\t\t\tYK的皮肤再生管理“别有用心”，先用MTS微针深层刺激皮肤，再用射频与超声进行再生管理，大力激活皮肤中的胶原蛋白因子。如果与PRP等自体再生因子治疗结合，效果将更佳。\n\t\t<\/p>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150415134338_79518.jpg\" \/> \n\t<\/div>\n<\/div>\n<!-- 品牌介绍 -->\n<div class=\"col-mod brand-intro line-h bt1\">\n\t<div class=\"title\">\n\t\t<h3>\n\t\t\t品牌介绍\n\t\t<\/h3>\n\t<\/div>\n\t<div class=\"con\">\n\t\t<h4 class=\"sub-title\">\n\t\t\tMTS微针+射频+超声\n\t\t<\/h4>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150415134347_76379.jpg\" \/> \n\t<\/div>\n<\/div>\n<!-- 治疗细节 -->\n<div class=\"col-mod treat-detail\">\n\t<div class=\"title\">\n\t\t<h3>\n\t\t\t治疗细节\n\t\t<\/h3>\n\t<\/div>\n\t<div class=\"con\">\n\t\t<p class=\"intro\">\n\t\t\tMTS微针+射频+超声，采用软膏麻醉或睡眠麻醉，治疗时间约60分钟，恢复时间只需1-2天，1-2个月后开始有效果。\n\t\t<\/p>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150415134355_79678.jpg\" \/> \n\t<\/div>\n<\/div>\n<!-- 治疗过程 -->\n<div class=\"col-mod treat-proccess\">\n\t<div class=\"title\">\n\t\t<h3>\n\t\t\t治疗过程\n\t\t<\/h3>\n\t<\/div>\n\t<div class=\"con\">\n\t\t<p class=\"intro\">\n\t\t\t治疗过程简单，只需三步，安全无副作用。\n\t\t<\/p>\n<!-- scroll start -->\n\t\t<div id=\"treat-proccess-scroll\" class=\"swiper-container\">\n\t\t\t<div class=\"swiper-wrapper\">\n\t\t\t\t<div class=\"swiper-slide\">\n\t\t\t\t\t<h5 class=\"title\">\n\t\t\t\t\t\t<span>01<\/span>洗颜\n\t\t\t\t\t<\/h5>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150415134402_49449.jpg\" \/> \n\t\t\t\t<\/div>\n\t\t\t\t<div class=\"swiper-slide\">\n\t\t\t\t\t<h5 class=\"title\">\n\t\t\t\t\t\t<span>02<\/span>MTS治疗软\n\t\t\t\t\t<\/h5>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150415134409_81177.jpg\" \/> \n\t\t\t\t<\/div>\n\t\t\t\t<div class=\"swiper-slide\">\n\t\t\t\t\t<h5 class=\"title\">\n\t\t\t\t\t\t<span>03<\/span>超声+射频再生管理\n\t\t\t\t\t<\/h5>\n<img src=\"http:\/\/static.drxiaomei.com\/images\/goods1030\/20150427163817_68278.jpg\" alt=\"\" \/><br \/>\n\t\t\t\t<\/div>\n\t\t\t<\/div>\n\t\t\t<div class=
	12-12 15:00:11.206: D/aaa(7357): goods = com.xiaomei.yanyu.bean.Goods@46eb51a8
*/
}