package com.xiaomei.yanyu.bean;

public class Mall {

	/*
	{
	    "msg": {
	        "data": [
	            {
	                "id": "1",
	                "title": "1234",
	                "link": "1015",
	                "createdate": 1429352715,
	                "is_open": "1",
	                "image": "http://bcs.duapp.com/drxiaomei/images/ads1/20150418124845_87872.png",
	                "is_delete": 0,
	                "parentid": 0,
	                "sort_order": "1",
	                "type": "1",
	                "pos": "shop_index"
	            },
	            {
	                "cat_mark": "肉毒素除皱",
	                "id": "4",
	                "keywords": "肉毒素除皱",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat4/20150418115813_66207.png",
	                "cat_des": "肉毒素除皱分类",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "肉毒素除皱",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "5",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat5/20150418115909_43308.png",
	                "cat_des": "填充塑形分类",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "填充塑形",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "6",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat6/20150418115934_42769.png",
	                "cat_des": "紧肤提升分类",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "紧肤提升",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "7",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat7/20150418120138_59621.png",
	                "cat_des": "肉毒素",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "肉毒素美型",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "8",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat8/20150418120209_81760.png",
	                "cat_des": "填充",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "填充凹陷",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "9",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat9/20150418120229_53341.png",
	                "cat_des": "激光",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "激光美肤",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "10",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat10/20150418120247_17849.png",
	                "cat_des": "特色美容",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "韩国特色美容",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "11",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat11/20150418120304_96695.png",
	                "cat_des": "激光除毛",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "激光除毛",
	                "parent_id": "0"
	            },
	            {
	                "cat_mark": "",
	                "id": "12",
	                "keywords": "",
	                "file": "http://bcs.duapp.com/drxiaomei/images/goodscat12/20150418120318_75337.png",
	                "cat_des": "体型雕塑",
	                "cat_type": "0",
	                "sort_order": "0",
	                "cat_name_en": "",
	                "cat_name": "体型雕塑",
	                "parent_id": "0"
	            }
	        ],
	        "title": "产品分类",
	        "key": "goods_cat"
	    },
	    "code": 0
	}
	*/
	
	private String id;
	private String title;
	private String link;
	private String createdate;
	private String isOpen;
	private String image;
	private String isDelete;
	private String parentid;
	private String sortOrder;
	private String type;
	private String pos;
	private String catMark;
	private String keywords;
	private String file;
	private String catDes;
	private String catType;
	private String catNameEn;
	private String catName;
	private String parentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getCatMark() {
		return catMark;
	}
	public void setCatMark(String catMark) {
		this.catMark = catMark;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getCatDes() {
		return catDes;
	}
	public void setCatDes(String catDes) {
		this.catDes = catDes;
	}
	public String getCatType() {
		return catType;
	}
	public void setCatType(String catType) {
		this.catType = catType;
	}
	public String getCatNameEn() {
		return catNameEn;
	}
	public void setCatNameEn(String catNameEn) {
		this.catNameEn = catNameEn;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
    	
}
