package com.xiaomei.yanyu.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.builder.AddUserOrderBuilder;
import com.xiaomei.yanyu.api.builder.HomeBuilder;
import com.xiaomei.yanyu.api.builder.RecommendSharesBuilder;
import com.xiaomei.yanyu.api.builder.RecommendSharesDetailBuilder;
import com.xiaomei.yanyu.api.builder.GoodsBuilder;
import com.xiaomei.yanyu.api.builder.GoodsOptionBuilder;
import com.xiaomei.yanyu.api.builder.MerchantBuilder;
import com.xiaomei.yanyu.api.builder.ListCommentBuilder;
import com.xiaomei.yanyu.api.builder.ListOrderBuilder;
import com.xiaomei.yanyu.api.builder.LoginOutBuilder;
import com.xiaomei.yanyu.api.builder.MallListBuilder;
import com.xiaomei.yanyu.api.builder.NetResultBuilder;
import com.xiaomei.yanyu.api.builder.OrderCommentBuilder;
import com.xiaomei.yanyu.api.builder.SectionBuilder;
import com.xiaomei.yanyu.api.builder.SingleGoodsBuilder;
import com.xiaomei.yanyu.api.builder.UploadFIleBuilder;
import com.xiaomei.yanyu.api.builder.UserLoginBuilder;
import com.xiaomei.yanyu.api.builder.UserMsgBuilder;
import com.xiaomei.yanyu.api.builder.UserRegisterBuilder;
import com.xiaomei.yanyu.api.builder.UserShareListBuilder;
import com.xiaomei.yanyu.api.builder.WechatBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.AbstractHttpApi;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpApiWithSession;
import com.xiaomei.yanyu.api.http.HttpUtil;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.GoodsOption;
import com.xiaomei.yanyu.bean.HomeItem;
import com.xiaomei.yanyu.bean.LoginResult;
import com.xiaomei.yanyu.bean.Mall;
import com.xiaomei.yanyu.bean.Merchant;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order2;
import com.xiaomei.yanyu.bean.RecommendShares;
import com.xiaomei.yanyu.bean.RecommendSharesDetail;
import com.xiaomei.yanyu.bean.Section;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.bean.User.UserInfo;
import com.xiaomei.yanyu.bean.UserMessage;
import com.xiaomei.yanyu.bean.WechatBean;
import com.xiaomei.yanyu.util.FileUtils;
import com.xiaomei.yanyu.util.Security;
import com.xiaomei.yanyu.util.UserUtil;

/**
 * Created by huzhi on 15-2-17.
 */
public class XiaoMeiApi {

    private HttpApi mHttpApi;
    private HttpUrlManager urlManager;

    public XiaoMeiApi(String clientVersion, Context mContext) {
        mHttpApi = new HttpApiWithSession(AbstractHttpApi.createHttpClient(),
                clientVersion);
        urlManager = new HttpUrlManager();
    }
    
    public HttpApi getHttpApi() {
        return mHttpApi;
    }

	// ========================================================================================
	// 一级页面(NET)
	// ========================================================================================
    
    /**首页*/
	public List<Section> getHomeListFromNet(String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getHomeListUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new SectionBuilder());
	}
	
	  /**首页新版本*/
    public List<HomeItem> getHomeListFromNet2()
            throws XiaoMeiCredentialsException, XiaoMeiIOException,
            XiaoMeiJSONException, XiaoMeiOtherException {
        BasicNameValuePair[] values = {
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getHomeListUrl2(),
                values[0],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        return mHttpApi.doHttpRequestObject(httpGet, new HomeBuilder());
    }
	
	/**商城首页*/
	public List<Mall> getMallHomeListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallHomeListUrl(),
				values[0],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new MallListBuilder());
	}
	
	/**商品列表*/
	public List<Goods> getGoodsListFromNet(String catId,String curpage,String perpage, String subCat, String originPlace, String priceOrder)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("cat_id", catId),
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("sub_cat", subCat),
				new BasicNameValuePair("origin_place", originPlace),
				new BasicNameValuePair("price_order", priceOrder)
		};
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallListUrl(),
				values[0],
				values[1],
				values[2],
				values[3],
				values[4],
				values[5],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
	}
	
	   /**获取单条商品信息*/
    public Goods getGoodsFromNet(String goodsId)
            throws XiaoMeiCredentialsException, XiaoMeiIOException,
            XiaoMeiJSONException, XiaoMeiOtherException {
        BasicNameValuePair[] values = {
                new BasicNameValuePair("token", UserUtil.getUser().getToken()),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)),
                new BasicNameValuePair("goods_id", goodsId)
               
        };
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.goodsDetailUrl(),
                values[0],
                values[1],
                values[2],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        android.util.Log.d("aaa", "url = " +         httpGet.getURI().toString());
        return mHttpApi.doHttpRequestObject(httpGet, new SingleGoodsBuilder());
    }
	
	/**商品分类列表*/
	public List<GoodsOption> getGoodsOptionFromNet(String catId)
	        throws XiaoMeiCredentialsException, XiaoMeiIOException,
            XiaoMeiJSONException, XiaoMeiOtherException {
	    BasicNameValuePair[] values = {
	            new BasicNameValuePair("cat_id", catId)
	    };
	    HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getGoodsFilterUrl(),
	            values[0],
	            new BasicNameValuePair("fig", Security.get32MD5Str(values)));
	    return mHttpApi.doHttpRequestObject(httpGet, new GoodsOptionBuilder());
	}
	
	/**机构
	 * @param pERPAGE2 */
	public List<Merchant> getMerchantListFromNet(String country, String special, String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
		        new BasicNameValuePair(HttpUtil.QUERY_COUNTRY, country),
		        new BasicNameValuePair(HttpUtil.QUERY_SPECIAL, special),
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMerchantListUrl(), AbstractHttpApi.signValuePairs(values));
		return mHttpApi.doHttpRequestObject(httpGet, new MerchantBuilder());
	}
	
	/**圈子*/
	public List<RecommendShares> getRecommendSharesFromNet(String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getRecommendSharesUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new RecommendSharesBuilder());
	}
	
	/**圈子详情*/
	public RecommendSharesDetail getRecommendSharesDetailFromNet(String id)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		android.util.Log.d("111", "id = " + id);
		BasicNameValuePair[] values = {
				new BasicNameValuePair("id",id),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getRecommendSharesDetailUrl(),
				values[0],
				values[1],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		android.util.Log.d("111", "getRecommendSharesDetailFromNet");
		return mHttpApi.doHttpRequestObject(httpGet, new RecommendSharesDetailBuilder());
	}
	
	   /**广场*/
    public List<UserShare> getUserShareListFromNet(String curpage,String perpage)
            throws XiaoMeiCredentialsException, XiaoMeiIOException,
            XiaoMeiJSONException, XiaoMeiOtherException {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("curpage", curpage));
        list.add(new BasicNameValuePair("perpage", perpage));
        list.add(new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)));
        if (UserUtil.getUser() != null) {
            list.add(new BasicNameValuePair("token", UserUtil.getUser().getToken()));
        }
        NameValuePair[] values = list.toArray(new NameValuePair[list.size()]);
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.userShareList(), AbstractHttpApi.signValuePairs(values));
        return mHttpApi.doHttpRequestObject(httpGet, new UserShareListBuilder());
    }
    
	
	// ========================================================================================
	// 用户注册与登录(NET)
	// ========================================================================================
	
	/**
	 * 注册
	 */
	public NetResult userRegister(String userid,String passwd,String rdcode) 
			throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid) ,
				new BasicNameValuePair("passwd", passwd),
				new BasicNameValuePair("rdcode", rdcode),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getUserRegisterUrl(),
				values[0],
				values[1],
				values[2],
				values[3],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return  mHttpApi.doHttpRequestObject(httpPost, new UserRegisterBuilder());
	}
    
	/**
	 * 登录
	 */
	public User userLogin(String userid,String passwd) 
			throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid) ,
				new BasicNameValuePair("passwd", passwd),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getUserLoginUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return  mHttpApi.doHttpRequestObject(httpPost, new UserLoginBuilder());
	}
	
	/**
	 * 登出
	 */
	public NetResult loginOut(String token)
			throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getUserLoginOutUrl(),
				values[0],
				values[1],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	}
	
	/**
	 * 获取验证码
	 */
	public String getVerificationCode(String telno)
			throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
				BasicNameValuePair[] values = {new BasicNameValuePair("telno", telno) ,
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
				HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getVerificationCodeUrl(),
						values[0],
						values[1],
						new BasicNameValuePair("fig", Security.get32MD5Str(values)));
				String responseCode =  mHttpApi.doHttpRequestString(httpPost);
				return responseCode;
	}
	
	/**
	 *  找回密码
	 */
	public User findPassword(String userid,String passwd,String rdcode)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid) ,
				new BasicNameValuePair("passwd", passwd),
				new BasicNameValuePair("rdcode", rdcode),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getFindPwdUrl(),
				values[0],
				values[1],
				values[2],
				values[3],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return  mHttpApi.doHttpRequestObject(httpPost, new UserLoginBuilder());
	}
	
	// ========================================================================================
	// 用户信息
	// ========================================================================================
	/**
	 * 获取用户信息
	 */
	public void getUserInfo(String userid,String token){
	/*
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid),new BasicNameValuePair("token", token)} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getUserInfoUrl(),
				values[0],
				values[1],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		mHttpApi.doHttpRequestObject(httpPost, new UserInfoBuilder());
		*/
	}
	/**
	 * 更新用户信息
	 */
	public void updateUserInfo(String username,String mobile,String address,String idcard,String avatar,String token)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		android.util.Log.d("555",
				"token = " + token + ",username = " + username + ",mobile = " + mobile + ",avatar = " + avatar + ",address = " + address + ",idcard = " +idcard
				);
		BasicNameValuePair[] values = {new BasicNameValuePair("token", token),
				new BasicNameValuePair("username", username),
                new BasicNameValuePair("mobile", mobile),
                new BasicNameValuePair("avatar", avatar),
                new BasicNameValuePair("address", address),
                new BasicNameValuePair("idcard", idcard),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))
		};
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.updateUserInfoUrl(),
				values[0],
				values[1],
				values[2],
                values[3],
                values[4],
                values[5],
                values[6],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	    /*
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		if(!TextUtils.isEmpty(username))
			list.add(new BasicNameValuePair("username", username));
		if(!TextUtils.isEmpty(mobile))
			list.add(new BasicNameValuePair("mobile", mobile));
		if(!TextUtils.isEmpty(address))
			list.add(new BasicNameValuePair("address", address));
		if(!TextUtils.isEmpty(idcard))
			list.add(new BasicNameValuePair("idcard", idcard));
		if(!TextUtils.isEmpty(avatar))
			list.add(new BasicNameValuePair("avatar", avatar));
		list.add(new BasicNameValuePair("token", token));
		
		List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
		if(!TextUtils.isEmpty(username))
			data.add(new BasicNameValuePair("username", username));
		if(!TextUtils.isEmpty(mobile))
			data.add(new BasicNameValuePair("mobile", mobile));
		if(!TextUtils.isEmpty(address))
			data.add(new BasicNameValuePair("address", address));
		if(!TextUtils.isEmpty(idcard))
			data.add(new BasicNameValuePair("idcard", idcard));
		if(!TextUtils.isEmpty(avatar))
			data.add(new BasicNameValuePair("avatar", avatar));
		data.add(new BasicNameValuePair("token", token));
		
		BasicNameValuePair pairs = 		new BasicNameValuePair("fig", Security.get32MD5Str((BasicNameValuePair[])data.toArray()));
		list.add(pairs);
		
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.updateUserInfoUrl(), (BasicNameValuePair[])list.toArray());
		mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
		*/
	}
	
	/**
	 * 更新用户头像
	 * @throws Exception 
	 */
	public String uploadFile(String token,String filePath) throws Exception{
		BasicNameValuePair[] values = {
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))};
		Map<String,String> params = new HashMap<String, String>();
		params.put("token", token);
		params.put("uptime", String.valueOf(System.currentTimeMillis()/1000));
		params.put("fig",  Security.get32MD5Str(values));
		return new UploadFIleBuilder().build(new JSONObject(FileUtils.uploadSubmit(urlManager.upoadAvatarUrl(),params,new File(filePath))));
	}
	
	/**
	 * 列出用户收到的对应消息
	 */
	public List<UserMessage> showUserMsg(String curpage,String perpage)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
			BasicNameValuePair[] values = {
					new BasicNameValuePair("token", UserUtil.getUser().getToken()) ,
					new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)),
					new BasicNameValuePair("curpage", curpage),
					new BasicNameValuePair("perpage", perpage)
			} ; 
			HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getUserMsgUrl(),
					values[0],
					values[1],
					values[2],
					values[3],
					new BasicNameValuePair("fig", Security.get32MD5Str(values)));
			 return mHttpApi.doHttpRequestObject(httpGet, new UserMsgBuilder());
	}
	
	/**
	 * 列出用户收藏的消息
	 */
	public List<Goods> showUserFav(String curpage,String perpage)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
			BasicNameValuePair[] values = {
			        new BasicNameValuePair("userid", UserUtil.getUser().getUserInfo().getUserid()) ,
					new BasicNameValuePair("token", UserUtil.getUser().getToken()) ,
					new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)),
					new BasicNameValuePair("curpage", curpage),
					new BasicNameValuePair("perpage", perpage)
			} ; 
			HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getUserMyFavUrl(),
					values[0],
					values[1],
					values[2],
					values[3],
					values[4],
					new BasicNameValuePair("fig", Security.get32MD5Str(values)));
			 return mHttpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
	}
	
	// ========================================================================================
	// 订单getUserFav()
	// ========================================================================================
	/**
	 * 取消订单
	 */
	public NetResult cancelUserOrderUrl(String orderid)
	    throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
        BasicNameValuePair[] values = {new BasicNameValuePair("orderid", orderid) ,
                new BasicNameValuePair("token", UserUtil.getUser().getToken()),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.cancelUserOrderUrl(),
                values[0],
                values[1],
                values[2],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        return mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	}
	
	/**
	 * 新增订单接口
	 */
	public Order addUserOrder(String userid,String goodsId,String username,String mobile,String passport,String token,String action)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid),
				new BasicNameValuePair("goods_id", goodsId),
				new BasicNameValuePair("username", username),
				new BasicNameValuePair("mobile", mobile),
				new BasicNameValuePair("passport", passport),
				new BasicNameValuePair("action",action),
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.addUserOrderUrl(),
				values[0],
				values[1],
				values[2],
				values[3],
				values[4],
				values[5],
				values[6],
				values[7],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpPost, new AddUserOrderBuilder());
	}
	
	/**
	 * 新增订单接口
	 */
	public Order updateUserOrder(String orderId,String userid,String goodsId,String username,String mobile,String passport,String token,String action)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid),
				new BasicNameValuePair("orderid", orderId),
				new BasicNameValuePair("goods_id", goodsId),
				new BasicNameValuePair("username", username),
				new BasicNameValuePair("mobile", mobile),
				new BasicNameValuePair("passport", passport),
				new BasicNameValuePair("action",action),
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.addUserOrderUrl(),
				values[0],
				values[1],
				values[2],
				values[3],
				values[4],
				values[5],
				values[6],
				values[7],
				values[8],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpPost, new AddUserOrderBuilder());
	}
	
	/**
	 * 获取用户订单列表接口
	 */
	public List<Order> getUserOrderList(String userid,String token)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid),
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getUserOrderUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpPost, new ListOrderBuilder());
	}
	
	// ========================================================================================
	// 评论
	// ========================================================================================
	   /**
     * 获取用户列表列表
     */
	public List<CommentItem> showCommentList(String id,String type,String curpage,String perpage)
	    throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
	       BasicNameValuePair[] values = {
	    		    new BasicNameValuePair("id", id),
	                new BasicNameValuePair("type", type),
	                new BasicNameValuePair("curpage", curpage),
	                new BasicNameValuePair("perpage", perpage),
	                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
	        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.showCommentList(),
	                values[0],
	                values[1],
	                values[2],
	                values[3],
	                values[4],
	                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
	        return mHttpApi.doHttpRequestObject(httpGet, new ListCommentBuilder());
	}
	
	/**
	 * 针对商品提交评论
	 */
	public NetResult actionShareComment(String orderId,String goodsId,String comment,String markService,String markEffect,String markEnvironment,String token) 
			throws XiaoMeiCredentialsException,
			XiaoMeiIOException, XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("order_id", orderId),
				new BasicNameValuePair("goods_id", goodsId),
				new BasicNameValuePair("comment", comment),
				new BasicNameValuePair("mark_service", markService),
				new BasicNameValuePair("mark_effect", markEffect),
				new BasicNameValuePair("mark_environment", markEnvironment),
				new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System
						.currentTimeMillis() / 1000)) };
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.actionGoodsComment(),
				values[0], 
				values[1], 
				values[2], 
				values[3],
				values[4],
				values[5],
				values[6],
				values[7],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	}
	/**
     * 针对分享提交评论
     */
	public NetResult actionGoodsComment(String token,String goodsId,String typeid,String comment)
        throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {	    
	       BasicNameValuePair[] values = {new BasicNameValuePair("token", token),
	                new BasicNameValuePair("shareid", goodsId),
	                new BasicNameValuePair("type_id", typeid),
	                new BasicNameValuePair("comment", comment),
	                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
	        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.actionShareComment(),
	                values[0],
	                values[1],
	                values[2],
	                values[3],
	                values[4],
	                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
	        return mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	}
	/**
     * 针对分享的评论提交子评论
     */
	public NetResult actionShareSubcomment(String token, String shareId, String commentId, String targetId, String comment)
        throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {	    
	       BasicNameValuePair[] values = {new BasicNameValuePair("token", token),
	                new BasicNameValuePair("shareid", shareId),
	                new BasicNameValuePair("commentid", commentId),
	                new BasicNameValuePair("targetid", targetId),
	                new BasicNameValuePair("comment", comment),
	                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
	        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.actionShareSubcomment(),
	                values[0],
	                values[1],
	                values[2],
	                values[3],
	                values[4],
	                values[5],
	                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
	        return mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
	}
    // ========================================================================================
    // 第三方登录(NET)
    // ========================================================================================
	/**
	 * 12. 接口功能:   第三方登陆接口
    接口地址:   third/weixinlogin.php
    请求参数:   //微信第三方登陆
                openid  对应用户在第三方唯一id
                platform    对应第三方平台(weixin, qq, weibo)
                access_token    用来校验用户授权
                username    用户名
                avatar      用户头像
                sex      用户性别
                //腾讯QQ第三方登陆
                openid  对应用户在第三方唯一id
                platform    对应第三方平台(weixin, qq, weibo)
                pf          对应腾讯具体平台
                userip      具体用户ip,用来在腾讯进行用户登录状态校验
                username    用户名
                avatar      用户头像
                sex      用户性别
    请求验证:   无
    请求返回:   与普通登陆相同的用户基本信息      
	 */
    public User thirdLogin(String openid, String platform, String access_token,
            String username, String avatar, String sex) 
        throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
        BasicNameValuePair[] values = {
        		new BasicNameValuePair("openid", openid),
                new BasicNameValuePair("platform", platform),
                new BasicNameValuePair("access_token", access_token),
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("avatar", avatar),
                new BasicNameValuePair("sex", sex),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.thirdLogin(),
                values[0],
                values[1],
                values[2],
                values[3],
                values[4],
                values[5],
                values[6],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        return mHttpApi.doHttpRequestObject(httpPost, new UserLoginBuilder());
    }
    /**微信支付*/
    public WechatBean payWechat(String orderid,String token)
            throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
        BasicNameValuePair[] values = {
				new BasicNameValuePair("token", token),
                new BasicNameValuePair("orderid", orderid),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))}; 
        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.payWechat(),
                values[0],
                values[1],
                values[2],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        return mHttpApi.doHttpRequestObject(httpPost, new WechatBuilder());
    }
    /**用户反馈*/
    public boolean feedback(String content,String contact,String token)
            throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
    	android.util.Log.d("111", "feedback  content = "+ content + ",token = " + token);
        BasicNameValuePair[] values = {
				new BasicNameValuePair("token", token),
                new BasicNameValuePair("content", content),
                new BasicNameValuePair("contact", contact),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))}; 
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.feedbackUrl(),
                values[0],
                values[1],
                values[2],
                values[3],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        NetResult netResult =  mHttpApi.doHttpRequestObject(httpGet, new NetResultBuilder());
        if(netResult!=null && "0".equals(netResult.getCode())){
        	return true;
        }
        return false;
    }
    // ========================================================================================
    // 删除或者添加用户收藏
    // ========================================================================================
    /**
     * action: del add
     */
    public void actionFav(String action,String goodsid,String token)
    		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
        BasicNameValuePair[] values = {
        		new BasicNameValuePair("token", token),
				new BasicNameValuePair("action", action),
                new BasicNameValuePair("goodsid", goodsid),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))}; 
        HttpPost httpPost = mHttpApi.createHttpPost(urlManager.actionUserFavUrl(),
                values[0],
                values[1],
                values[2],
                values[3],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
    }
    /**促销消息*/
    public List<Goods> myPromotion(String curpage,String perpage,String token)
            throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
        BasicNameValuePair[] values = {
                new BasicNameValuePair("token", token),
                new BasicNameValuePair("curpage", curpage),
                new BasicNameValuePair("perpage", perpage),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.myPromotionUrl(),
                values[0],
                values[1],
                values[2],
                values[3],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        return mHttpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
    }
    
    /**是否收藏*/
    public boolean isFav(String goodsid,String userid,String token)
            throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {     
        BasicNameValuePair[] values = {
                new BasicNameValuePair("token", token),
                new BasicNameValuePair("goodsid", goodsid),
                new BasicNameValuePair("userid", userid),
                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.isFav(),
                values[0],
                values[1],
                values[2],
                values[3],
                new BasicNameValuePair("fig", Security.get32MD5Str(values)));
        NetResult netResult = mHttpApi.doHttpRequestObject(httpGet, new NetResultBuilder());
        return Boolean.valueOf(netResult.getMsg());
    }
}
