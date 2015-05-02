package com.xiaomei.yanyu.levelone.model;

import java.util.List;

import com.xiaomei.yanyu.bean.Section;

public class HomeListModel {
	
	private List<Section> list;

	public List<Section> getList() {
		return list;
	}

	public void setList(List<Section> list) {
		this.list = list;
	}
	
	private int pageNum = 1;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public void increasePageNum(){
		pageNum ++;
	}
	
	public void reducePageNum(){
		pageNum --;
	}
	
}
