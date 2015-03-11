package com.xiaomei.bean;

import java.util.List;

public class Section {
	
	private String key;
	
	private int ordering;
	
	private List<Entity> list = null;
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}




	public static class Entity{
		
		private String title;
		
		private String img;
		
		private String addr;
		
		private String price_xm;
		
		private String price_market;
		
		private String price_market_h;
		
		private String saled;
		
		private String stock;
		
		private String url;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getPrice_xm() {
			return price_xm;
		}

		public void setPrice_xm(String price_xm) {
			this.price_xm = price_xm;
		}

		public String getPrice_market() {
			return price_market;
		}

		public void setPrice_market(String price_market) {
			this.price_market = price_market;
		}

		public String getSaled() {
			return saled;
		}

		public void setSaled(String saled) {
			this.saled = saled;
		}

		public String getStock() {
			return stock;
		}

		public void setStock(String stock) {
			this.stock = stock;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPrice_market_h() {
			return price_market_h;
		}

		public void setPrice_market_h(String price_market_h) {
			this.price_market_h = price_market_h;
		}
		
		
		
	}

}
