package com.xiaomei.yanyu.bean;
/**
 * 1.2.1 版本的首页bean
 *
 */
public class HomeItem {
	
	/**布局类型*/
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**子bean*/
	public static class Item{
		public String img;
		public String name;
		public String count;
		public String title;
		public String comments;
	}
	
}
