package com.xiaomei.yanyu.api;

import com.xiaomei.yanyu.DebugRelease;

public class HttpUrlManager {
	
	private static String HOST = "http://api.drxiaomei.com"; 
	
	private static final String SERVER = HOST + "/server";
	
    private static final String ACTION = SERVER + "/action";
	
	/**产品详情*/
	public static String GOODS_DETAIL_URL  = "http://z.drxiaomei.com/goods.php";
	/**机构详情*/
	public static String MERCHANT_DETAIL_URL = "http://z.drxiaomei.com/hospital.php";//?hosp_id=45
	
	public static String COMMENT_DETAIL_URL = HOST +"/share-comment.php";//http://drxiaomei.duapp.com/share-comment.php?itemid=跟上美丽圈ID
	
	public static String UPDATE_USER_ICON = HOST + "/server/action/upoadAvatar.php";
	
	public static String ADD_LIKE = ACTION +  "/addlike.php";
	
	public static String DEL_LIKE = ACTION +  "/dellike.php";

    private static final String BEE = SERVER + "/bee";
    public static final String AREA_LIST = BEE + "/citys.php";
    public static final String AREA_FILTER_LIST = BEE + "/city_filter.php";
    public static final String AREA_GOODS_LIST = BEE + "/city_prod.php";;
    public static final String AREA_MERCHANT_LIST = BEE + "/city_hosp.php";
    public static final String GOODS_TOPIC_LIST = BEE + "/hist_prod.php";;
    public static final String MERCHANT_TOPIC_LIST = BEE + "/hist_hosp.php";;

    static{
		if(DebugRelease.isDebug){
			HOST = "http://180.76.146.212";
		}else{
			HOST = "http://api.drxiaomei.com";
		}
	}
	
	public static String NEW_POST = HOST + "/server/action/sharePublish.php";

	public static String UPLOAD_FILE = HOST + "/server/action/uploadUserShare.php";
	
	/**首页*/
	String getHomeListUrl(){
		return HOST + "/server/show/start.php";
	}
	   /**首页新版*/
    String getHomeListUrl2(){
        return HOST + "/server/bee/home.php";
    }
	/**机构*/
	String getMerchantListUrl(){
		return HOST + "/server/show/hospital_list.php";
	}
	/**圈子*/
	String getRecommendSharesUrl(){
		return HOST + "/server/show/share_list.php";
	}
	/**圈子详情*/
	String getRecommendSharesDetailUrl(){
		return HOST + "/server/show/share_info.php";
	}
	/**商城*/
	String getMallListUrl(){
		return HOST + "/server/show/goods_list.php";
	}
	/**商品分类*/
	String getGoodsFilterUrl(){
	    return HOST + "/server/show/goods_filter.php";
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
		return HOST + "/server/show/myMsg.php";
	}
	/**用户收藏*/
	String getUserMyFavUrl(){
		return HOST + "/server/show/myFav.php";
	}
	/**用户收藏操作*/
	String actionUserFavUrl(){
		return HOST + "/server/action/fav.php";
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
    
    /**添加分享子评论接口*/
    String actionShareSubcomment(){
        return HOST + "/server/action/shareSubcomment.php";
    }
    
    /**微信支付*/
    String payWechat(){
		return HOST + "/server/pay/wechat.php";
    }
    /**用户反馈*/
    String feedbackUrl(){
    	return HOST + "/server/action/feedback.php";
    }
    /**促销消息*/
    String myPromotionUrl(){
        return HOST + "/server/show/myPromotion.php";
    }
    /**是否收藏*/
    String isFav(){
        return HOST + "/server/show/isfav.php";
    }
    /**广场列表*/
    String userShareList(){
        return HOST + "/server/show/user_share_list.php";
    }
    
    // =========================================================================================
    // 优惠卷模块
    // =========================================================================================
    /**进入优惠卷*/
    public static String enterPreferentialVolumeUrl(){
        return HOST + "/server/soupon/add_goods_coupon.php";
    }
    /**我的优惠卷*/
    public static String userPreferentialVolumeUrl(){
        return HOST + "/server/coupon/user_coupon.php";
    }
    /**增加优惠卷*/
    public static String addPreferentialVolumeUrl(){
        return HOST + "/server/soupon/add_goods_coupon.php";
    }
}   



