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
		public String img;   //图片
		public String name;  //名字
		public String count;
		public String title; 
		public String comments;
		
		public String user;
		public String url;
		
		public String list;
		public String city; //城市名字
		public String cityId; //城市id号
		public String jump; //每个背书的junp
		public String goodsId; //热门项目的 goods_id
		public String shareId; // share id
		public String viewcount;
		public String id;
		public String type;
	}
	
	/**
	 * 背书
	 */
	public static class Recite{
		public String jump;
		public String img;
	}
}
