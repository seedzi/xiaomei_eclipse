package com.xiaomei.yanyu.bean;

public class BeautifulRing {
	
	private String shareType;
	private String shareMark;
	private String isOpen;
	private String createdate;
	private String userid;
	private String avatar;
	private String shareFile;
	private String id;
	private String shareDes;
	private String numComments;
	private String username;
	private String goodsId;
	private String hospId;
	private String numFavors;
	private String sortOrder;
	private String shareTitle;
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getShareMark() {
		return shareMark;
	}
	public void setShareMark(String shareMark) {
		this.shareMark = shareMark;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getShareFile() {
		return shareFile;
	}
	public void setShareFile(String shareFile) {
		this.shareFile = shareFile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShareDes() {
		return shareDes;
	}
	public void setShareDes(String shareDes) {
		this.shareDes = shareDes;
	}
	public String getNumComments() {
		return numComments;
	}
	public void setNumComments(String numComments) {
		this.numComments = numComments;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getNumFavors() {
		return numFavors;
	}
	public void setNumFavors(String numFavors) {
		this.numFavors = numFavors;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	
	
	
/*
	{
	    "msg": {
	        "shares": [
	            {
	                "share_type": "1",
	                "share_mark": "0",
	                "is_open": "1",
	                "createdate": "1429870527",
	                "userid": "0",
	                "avatar": "0",
	                "share_file": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150417102652_89724.jpg",
	                "id": "6",
	                "share_des": "广藏市场,坐落于首尔市的中心位置,这里不同于明洞的时尚喧闹,区别于新沙的高贵小资.广藏市场拥有的是时间沉淀下来的风土人情.来来往往的人流,熙熙攘攘的叫卖声,还有琳琅满目的各式商品.在这里,你可以体会到韩国传统的市井文化,品尝到最具特色的街头小吃~",
	                "num_comments": "0",
	                "username": "0",
	                "goods_id": "0",
	                "hosp_id": "0",
	                "images": [
	                    {
	                        "id": "96",
	                        "title": "广藏市场最有名的就是生拌牛肉~生拌牛肉作为烧酒的下酒菜相当受欢迎。在广藏市场里您可以享用到肉质厚实、口感湿润的生拌牛肉片。 (约人民币100元)",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204023_51409.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    },
	                    {
	                        "id": "97",
	                        "title": "以绿豆为主原料的绿豆煎饼，有时也被称作“绿豆芽煎饼”，在煎饼专卖店或马格利米酒小酒馆都有供应。外焦里嫩的绿豆煎饼配上酒香浓郁的马格利米酒才是最地道的吃法~(rmb 25)",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204051_32582.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    },
	                    {
	                        "id": "98",
	                        "title": "市场里怎么能少了拌饭呢~这里的蔬菜都来自于市场供应,绝对的新鲜水灵~逛市场累了的时候,来一碗市场拌饭~绝对的实惠又美味哦~(rmb 25)",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204100_52189.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    },
	                    {
	                        "id": "99",
	                        "title": "说到韩国人最日常的吃法,莫过于泡菜炒饭啦~酸爽的泡菜经过快速的翻炒和米饭完美的融合~再配上嫩嫩的流黄儿鸡蛋~每一口都是那么回味无穷! ",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204108_74529.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    },
	                    {
	                        "id": "100",
	                        "title": "荞麦外皮煎至两面焦黄~每咬一口都外焦里嫩~特别是里面馅料丰富,滋味足足",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204114_53311.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    },
	                    {
	                        "id": "101",
	                        "title": "市场内贩卖的物品种类丰富，蔬果、肉类、海鲜，应有尽有。看看这琳琅满目的摊位,你是不是也不自觉地停下脚步,准备耐心的挑选下呢?~",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share6\/20150415204132_11881.jpg",
	                        "link": null,
	                        "typeid": "6"
	                    }
	                ],
	                "num_favors": "0",
	                "sort_order": "0",
	                "share_title": "韩国美食攻略之广藏市场"
	            },
	            {
	                "share_type": "0",
	                "share_mark": "0",
	                "is_open": "1",
	                "createdate": "1429870325",
	                "userid": "0",
	                "avatar": "0",
	                "share_file": "0",
	                "id": "19000011",
	                "share_des": "作为关注度颇高的注射除皱，为了求美者不被铺天盖地的宣传所蒙蔽，小美在此为您解读关于注射除皱产品的八大误区。",
	                "num_comments": "0",
	                "username": "0",
	                "goods_id": "0",
	                "hosp_id": "0",
	                "images": [
	                    {
	                        "id": "181",
	                        "title": "误区一：注射一次可以保持两年甚至几年 正确认识： 在皱纹逐渐开始恢复前，高品质的注射产品疗效可以持续长达6-8个月。如果重复使用，效果会趋向持续更久。而在数次注射后疗效可能适当增长，但一般均不会超过一年。",
	                        "type": "share",
	                        "url": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000011\/20150423154749_64741.jpg",
	                        "link": null,
	                        "typeid": "19000011"
	                    },
	                    {
	                        "id": "182",
	                        "title": "误区二：在皮肤上涂抹高级的护肤产品，可以有效去除皱纹，没必要注射除皱 正确认识： 随着年龄增长，皮肤里的胶原蛋白、水分含量会渐渐流失，皮下脂肪也会萎缩下垂，造成皮肤松弛和老化，形成皮肤表面上的凹陷，就会产生皱纹。 皱纹是日积月累的阳光伤害和皮肤的天然支撑结构不可避免的弱化造成的，与皮肤干燥引起的浅表性细纹完全不同。",
	                        "type": "share",
	                        "url": "http:\
*/
	/*
	private String id;
	private String goodsId;
	private String hospId;
	private String userid;
	private String shareTitle;
	private String shareDes;
	private String sortOrder;
	private String isOpen;
	private String shareMark;
	private String shareFile;
	private String numFavors;
	private String numComments;
	private String modifydate;
	private String shareType;
	
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
	public String getShareMark() {
		return shareMark;
	}
	public void setShareMark(String shareMark) {
		this.shareMark = shareMark;
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
	public String getModifydate() {
		return modifydate;
	}
	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	*/
}
