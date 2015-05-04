package com.xiaomei.yanyu.bean;

import java.util.List;

public class BeautifulRingDetail {
	
	private String id;
	
	private String goodsId;
	
	private String hospId;
	
	private String userid;
	
	private String username;
	
	private String avatar;
	
	private String shareTitle;
	
	private String shareDes;
	
	private String sortOrder;
	
	private String isOpen;
	
	private String shareMake;
	
	private String shareFile;
	
	private String numFavors;
	
	private String numComments;
	
	private String createdate;
	
	private List<Item> items;
	
	private String image;
	
	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getGoodsId() {
		return goodsId;
	}



	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}



	public String getHospId() {
		return hospId;
	}



	public void setHospId(String hospId) {
		this.hospId = hospId;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public String getShareTitle() {
		return shareTitle;
	}



	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}



	public String getShareDes() {
		return shareDes;
	}



	public void setShareDes(String shareDes) {
		this.shareDes = shareDes;
	}



	public String getSortOrder() {
		return sortOrder;
	}



	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}



	public String getIsOpen() {
		return isOpen;
	}



	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}



	public String getShareMake() {
		return shareMake;
	}



	public void setShareMake(String shareMake) {
		this.shareMake = shareMake;
	}



	public String getShareFile() {
		return shareFile;
	}



	public void setShareFile(String shareFile) {
		this.shareFile = shareFile;
	}



	public String getNumFavors() {
		return numFavors;
	}



	public void setNumFavors(String numFavors) {
		this.numFavors = numFavors;
	}



	public String getNumComments() {
		return numComments;
	}



	public void setNumComments(String numComments) {
		this.numComments = numComments;
	}



	public String getCreatedate() {
		return createdate;
	}



	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}



	public List<Item> getItems() {
		return items;
	}



	public void setItems(List<Item> items) {
		this.items = items;
	}



	public static class Item{
		
		private String id;
		
		private String type;
		
		private String typeid;
		
		private String url;
		
		private String tilte;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTypeid() {
			return typeid;
		}

		public void setTypeid(String typeid) {
			this.typeid = typeid;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTilte() {
			return tilte;
		}

		public void setTilte(String tilte) {
			this.tilte = tilte;
		}
		
		
	}

}
