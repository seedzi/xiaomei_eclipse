package com.xiaomei.yanyu.bean;

public class Hospital {
	
/*	
	{
	    "msg": {
	        "hospitals": [
	            {
	                "rate_avg": "98.5",
	                "tel": "+82-02-562-5800",
	                "addr": "韩国首尔江南区驿三洞822 －1江南A座9层",
	                "hosp_name": "COOKI整形外科",
	                "city": "3",
	                "rate_environment": "4",
	                "open_time": "周一到周四 10:00－19:00 周五 10:00－21:00 周六 10:00－16:00",
	                "id": "3",
	                "surrounding": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "area": "1",
	                "file": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/hosp3\/20150413095720_64494.jpg",
	                "hosp_type": "1",
	                "parent_id": "0",
	                "services": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "keywords": "0",
	                "hosp_des": "专业定制你的美",
	                "link_url": "www.cookips.co.kr",
	                "hosp_name_en": "jg1",
	                "rate_effect": "5",
	                "watermark": "0",
	                "payments": "[\"1\",\"2\",\"3\"]",
*/
	
	private String rateAvg;
	
	private String tel;
	
	private String addr;
	
	private String hospName;
	
	private String city;
	
	private String rateEnvironment;
	
	private String openTime;
	
	private String id;
	
	private String surrounding;
	
	private String area;
	
	private String file;
	
	private String hospType;
	
	private String parentId;
	
	private String services;
	
	private String keywords;
	
	private String hospDes;
	
	private String linkUrl;
	
	private String hospNameEn;
	
	private String rateEffect;
	
	private String watermark;
	
	private String payments;
	
	private String rateService;

	public String getRateAvg() {
		return rateAvg;
	}

	public void setRateAvg(String rateAvg) {
		this.rateAvg = rateAvg;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getHospName() {
		return hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRateEnvironment() {
		return rateEnvironment;
	}

	public void setRateEnvironment(String rateEnvironment) {
		this.rateEnvironment = rateEnvironment;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSurrounding() {
		return surrounding;
	}

	public void setSurrounding(String surrounding) {
		this.surrounding = surrounding;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getHospType() {
		return hospType;
	}

	public void setHospType(String hospType) {
		this.hospType = hospType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getHospDes() {
		return hospDes;
	}

	public void setHospDes(String hospDes) {
		this.hospDes = hospDes;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getHospNameEn() {
		return hospNameEn;
	}

	public void setHospNameEn(String hospNameEn) {
		this.hospNameEn = hospNameEn;
	}

	public String getRateEffect() {
		return rateEffect;
	}

	public void setRateEffect(String rateEffect) {
		this.rateEffect = rateEffect;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setWatermark(String watermark) {
		this.watermark = watermark;
	}

	public String getPayments() {
		return payments;
	}

	public void setPayments(String payments) {
		this.payments = payments;
	}

	public String getRateService() {
		return rateService;
	}

	public void setRateService(String rateService) {
		this.rateService = rateService;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	private String id;
	private String parentId;
	private String hospName;
	private String hospNameEn;
	private String hospType;
	private String keywords;
	private String hospDes;
	private String sortOrder;
	private String hospMark;
	private String file;
	private String linkUrl;
	private String openTime;
	private String addr;
	private String tel;
	private String city;
	private String area;
	private String services;
	private String surrounding;
	private String payments;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getHospName() {
		return hospName;
	}
	public void setHospName(String hospName) {
		this.hospName = hospName;
	}
	public String getHospNameEn() {
		return hospNameEn;
	}
	public void setHospNameEn(String hospNameEn) {
		this.hospNameEn = hospNameEn;
	}
	public String getHospType() {
		return hospType;
	}
	public void setHospType(String hospType) {
		this.hospType = hospType;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getHospDes() {
		return hospDes;
	}
	public void setHospDes(String hospDes) {
		this.hospDes = hospDes;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getHospMark() {
		return hospMark;
	}
	public void setHospMark(String hospMark) {
		this.hospMark = hospMark;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getSurrounding() {
		return surrounding;
	}
	public void setSurrounding(String surrounding) {
		this.surrounding = surrounding;
	}
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	*/
	
	
	
	
	
	/*
	"id": "16",
    "parent_id": "0",
    "hosp_name": "韩国科莱秀(KLASSE)整形外科医院 ",
    "hosp_name_en": "k231",
    "hosp_type": "3",
    "keywords": "",
    "hosp_des": "科莱秀整形医院是韩国最高学府首尔大学医科大学和韩国医院三星首尔医院是同门医疗机构，代表院长金政敏是首尔大学医科专业毕业，同时拥有最具权威的医疗团队组成的。医院以正直和信赖为运营主旨，为患者提供1对1的专业商谈，让手术结果达到患者满意的程度。本院开设有面部轮廓、唇部/人中、干细胞隆胸、臂部、提升等多种手术项目，我们有足够的经验，可以向患者保证最好最满意的礼物。科莱秀整形医院从手术前检查，手术，术后管理一切以顾客为中心。提供便利，最满意的一条龙服务而努力。同时，科莱秀整形医院精确分析患者的状态，进行安全正确",
    "sort_order": "0",
    "hosp_mark": "",
    "file": "http://bcs.duapp.com/drxiaomei/images/jg16/20150305140549_86663.jpg",
    "link_url": "www.yk5151.com (韩文) / http://www.yk5151.net (中文)",
    "open_time": "周一至周六 10:00AM - 5:00PM",
    "addr": "10 Da-dong, Jung-gu, Seoul, 韩国",
    "tel": "82-2-542-5151",
    "city": "3",
    "area": "2",
    "services": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
    "surrounding": "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\"]",
    "payments": "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\"]"
	 */
}
