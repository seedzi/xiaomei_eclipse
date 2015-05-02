package com.xiaomei.yanyu.bean;

public class WechatBean {
/*
 {"msg":{"sign":"AF2544440E3062E94D66814F47C33ECD","timestamp":1430550279,"package":"Sign=WXPay","noncestr":"bxlk4pvmnyvagk0zkcxynrd5z6q7xiwh",
 "partnerid":"1237015302","appid":"wx67f54f6d2c0d66c8","prepayid":"wx2015050215044035dd3556680046729158"},"code":0}
*/

	private String sign;
	private String timestamp;
	private String packageTxt;
	private String noncestr;
	private String partnerid;
	private String appid;
	private String prepayid;
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPackageTxt() {
		return packageTxt;
	}
	public void setPackageTxt(String packageTxt) {
		this.packageTxt = packageTxt;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	@Override
	public String toString() {
		return "WechatBean [sign=" + sign + ", timestamp=" + timestamp
				+ ", packageTxt=" + packageTxt + ", noncestr=" + noncestr
				+ ", partnerid=" + partnerid + ", appid=" + appid
				+ ", prepayid=" + prepayid + "]";
	}
	
	
	
}
