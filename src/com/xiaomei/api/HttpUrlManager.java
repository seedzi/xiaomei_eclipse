package com.xiaomei.api;

public class HttpUrlManager {
	
	private static String HOST = "http://drxmapi.duapp.com"; 
	
	String getHomeListUrl(){
		return HOST + "/api/index.php";
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

