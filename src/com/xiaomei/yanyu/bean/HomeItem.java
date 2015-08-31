package com.xiaomei.yanyu.bean;

import java.util.List;

/**
 * 1.2.1 版本的首页bean
 *
 */
public class HomeItem {
	
	/**布局类型*/
	private String type;
	
	private Recite recite;
	
	private List<Item> mList;


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<Item> getmList() {
        return mList;
    }

    public void setmList(List<Item> mList) {
        this.mList = mList;
    }
    
    public Recite getRecite() {
		return recite;
	}

	public void setRecite(Recite recite) {
		this.recite = recite;
	}



	/**子bean*/
	public static class Item{
		public String img;
		public String name;
		public String count;
		public String title;
		public String comments;
	}
	/**
	 * 背书
	 */
	public static class Recite{
		public String jump;
		public String url;
	}
}
