package com.xiaomei.yanyu.api;

public class HttpUrlManager {
	
	private static String HOST = "http://api.drxiaomei.com"; 
	/**产品详情*/
	public static String GOODS_DETAIL_URL  = "http://z.drxiaomei.com/goods.php";
	/**机构详情*/
	public static String MECHANISM_DETAIL_URL = "http://z.drxiaomei.com/hospital.php";//?hosp_id=45
	
	public static String COMMENT_DETAIL_URL = HOST +"/share-comment.php";//http://drxiaomei.duapp.com/share-comment.php?itemid=跟上美丽圈ID
	
	public static String UPDATE_USER_ICON = HOST + "/server/action/upoadAvatar.php";
 	
	/**首页*/
	String getHomeListUrl(){
		return HOST + "/server/show/start.php";
	}
	/**机构*/
	String getMechanismListUrl(){
		return HOST + "/server/show/hospital_list.php";
	}
	/**圈子*/
	String getRingListUrl(){
		return HOST + "/server/show/share_list.php";
	}
	/**圈子详情*/
	String getRingDetailUrl(){
		return HOST + "/api/share_detail.php";
	}
	/**商城*/
	String getMallListUrl(){
		return HOST + "/server/show/goods_list.php";
	}
	/**商城首页*/
	String getMallHomeListUrl(){
		return HOST + "/server/show/goods_home.php";
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
    /**第三方登录*/
    String thirdLogin(){
        return HOST + "/server/third/weixinlogin.php";
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
	/**取消用户订单*/
    String cancelUserOrderUrl(){
        return HOST + "/server/action/cancleOrder.php";
    }
	/**用户消息*/
	String getUserMsgUrl(){
		return HOST + "/show/myMsg";
	}
	/**用户阅读消息标记*/
	String actionUserMsgUrl(){
		return HOST + "/action/msg";
	}
	/**获取产品详情*/
	String goodsDetailUrl(){
		return HOST + "/server/show/goodsinfo.php";//http://api.drxiaomei.com/server/show/goodsinfo.php?goods_id=45
	}
	/**活取对应商品或者分享评论列表*/
	String showCommentList(){
	    return HOST + "/server/show/commentList.php";
	}
	/**添加商品评论接口*/
    String actionGoodsComment(){
        return HOST + "/server/action/goodsComment.php";
    }
    /**添加分享评论接口*/
    String actionShareComment(){
        return HOST + "/server/action/shareComment.php";
    }	
    /**微信支付*/
    String payWechat(){
		return HOST + "/server/pay/wechat.php";
    }
}

