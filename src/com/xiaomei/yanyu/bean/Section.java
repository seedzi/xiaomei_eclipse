package com.xiaomei.yanyu.bean;

import java.util.List;

public class Section {
	
	private String key;
	
	private int ordering;
	
	private String title;
	
	private String type;
	
	private String list_String;
	
	private String des;
	
	private List<Entity> list = null;
	
	
	public String getList_String() {
		return list_String;
	}

	public void setList_String(String list_String) {
		this.list_String = list_String;
	}

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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}



	public static class Entity{
		
		private String title;
		
		private String img;
		
		private String url;
		
		private String numfavorite;
		
		private String date;
		
		private String username;
		
		private String numcomment;
		
		private String avator;
		

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

		public String getNumfavorite() {
			return numfavorite;
		}

		public void setNumfavorite(String numfavorite) {
			this.numfavorite = numfavorite;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNumcomment() {
			return numcomment;
		}

		public void setNumcomment(String numcomment) {
			this.numcomment = numcomment;
		}

		public String getAvator() {
			return avator;
		}

		public void setAvator(String avator) {
			this.avator = avator;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		

		@Override
		public String toString() {
			return "Entity [title=" + title + ", img=" + img + ", url=" + url
					+ ", numfavorite=" + numfavorite + ", date=" + date
					+ ", username=" + username + ", numcomment=" + numcomment
					+ ", avator=" + avator + "]";
		}


//		public String getPrice_market_h() {
//			return price_market_h;
//		}
//
//		public void setPrice_market_h(String price_market_h) {
//			this.price_market_h = price_market_h;
//		}
		
		
		
	}

}
