package com.xiaomei.api;

public class HttpUrlManager {
	
	private static String HOST = "http://drxmapi.duapp.com"; 
	
	/**首页*/
	String getHomeListUrl(){
		return HOST + "/api/index.php";
	}
	
	/**机构*/
	String getMechanismListUrl(){
		return HOST + "/api/hospital.php";
	}
	
	/**圈子*/
	String getRingListUrl(){
		return HOST + "/api/share.php";
	}
	
	/**商城*/
	String getMallListUrl(){
		return HOST + "/api/goods.php";
	}
	
	String getUserRegisterUrl(){
		return HOST + "/server/user/register.php";
	}

	String getUserLoginUrl(){
		return HOST + "/server/user/login.php";
	}
	
	String getVerificationCodeUrl(){
		return HOST + "/server/user/rdcode.php";
	}
	
	String getFindPwdUrl(){
		return HOST + "/server/user/findpwd.php";
	}
	
}

