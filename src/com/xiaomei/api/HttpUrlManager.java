package com.xiaomei.api;

public class HttpUrlManager {
	
	private static String HOST = "http://drxmapi.duapp.com"; 
	
	public static String GOODS_DETAIL_URL  = HOST + "/goods.php";
	
	public static String COMMENT_DETAIL_URL = "http://drxiaomei.duapp.com/share-comment.php";//http://drxiaomei.duapp.com/share-comment.php?itemid=跟上美丽圈ID
	
	public static String UPDATE_USER_ICON = HOST + "/server/action/upoadAvatar.php";
 	
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
	/**圈子详情*/
	String getRingDetailUrl(){
		return HOST + "/api/share_detail.php";
	}
	/**商城*/
	String getMallListUrl(){
		return HOST + "/api/goods.php";
	}
	/**注册*/
	String getUserRegisterUrl(){
		return HOST + "/server/user/register.php";
	}
	/**登录*/
	String getUserLoginUrl(){
		return HOST + "/server/user/login.php";
	}
	/**登出*/
	String getUserLoginOutUrl(){
		return HOST + "/server/user/logout.php";
	}
	/**短信验证*/
	String getVerificationCodeUrl(){
		return HOST + "/server/user/rdcode.php";
	}
	/**找回密碼*/
	String getFindPwdUrl(){
		return HOST + "/server/user/findpwd.php";
	}
	/**获取用户信息*/
	String getUserInfoUrl(){
		return HOST + "/server/show/userinfo.php";
	}
	/**更新用户信息*/
	String updateUserInfoUrl(){
		return HOST + "/server/action/update.php";
	}
	/**更新用户头像*/
	String upoadAvatarUrl(){
		return HOST + "/server/action/uploadAvatar.php";
	}
	/**获取用户订单*/
	String getUserOrderUrl(){
		return HOST + "/server/show/myOrder.php";
	}
	/**生成用户订单*/
	String addUserOrderUrl(){
		return HOST + "/server/action/order.php";
	}
	/**用户消息*/
	String getUserMsgUrl(){
		return HOST + "/show/myMsg";
	}
	/**用户阅读消息标记*/
	String actionUserMsgUrl(){
		return HOST + "/action/msg";
	}
	/**产品详情页*/
	String goodsDetailUrl(){
		return HOST + "/goods.php";
	}
}

