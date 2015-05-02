package com.xiaomei.yanyu.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.builder.AddUserOrderBuilder;
import com.xiaomei.yanyu.api.builder.BeautifulDetailBuilder;
import com.xiaomei.yanyu.api.builder.BeautifulRingBuilder;
import com.xiaomei.yanyu.api.builder.GoodsBuilder;
import com.xiaomei.yanyu.api.builder.HospitalBuilder;
import com.xiaomei.yanyu.api.builder.ListCommentBuilder;
import com.xiaomei.yanyu.api.builder.ListOrderBuilder;
import com.xiaomei.yanyu.api.builder.LoginOutBuilder;
import com.xiaomei.yanyu.api.builder.MallListBuilder;
import com.xiaomei.yanyu.api.builder.NetResultBuilder;
import com.xiaomei.yanyu.api.builder.OrderCommentBuilder;
import com.xiaomei.yanyu.api.builder.SectionBuilder;
import com.xiaomei.yanyu.api.builder.UploadFIleBuilder;
import com.xiaomei.yanyu.api.builder.UserLoginBuilder;
import com.xiaomei.yanyu.api.builder.UserRegisterBuilder;
import com.xiaomei.yanyu.api.builder.WechatBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.AbstractHttpApi;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.api.http.HttpApiWithSession;
import com.xiaomei.yanyu.bean.BeautifulRing;
import com.xiaomei.yanyu.bean.BeautifulRingDetail;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.Hospital;
import com.xiaomei.yanyu.bean.LoginResult;
import com.xiaomei.yanyu.bean.Mall;
import com.xiaomei.yanyu.bean.NetResult;
import com.xiaomei.yanyu.bean.Order;
import com.xiaomei.yanyu.bean.Order2;
import com.xiaomei.yanyu.bean.Section;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.bean.User.UserInfo;
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
	
	/**商城*/
	public List getMallListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new HospitalBuilder());
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
	public List<Goods> getGoodsListFromNet(String catId,String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("cat_id", catId),
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage)} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallListUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
	}
	
	/**机构*/
	public List<Hospital> getMechanismListFromNet(String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMechanismListUrl(),
				values[0],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new HospitalBuilder());
	}
	
	/**圈子*/
	public List<BeautifulRing> getBeatifulRingListFromNet(String curpage,String perpage)
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		BasicNameValuePair[] values = {
				new BasicNameValuePair("curpage", curpage),
				new BasicNameValuePair("perpage", perpage),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getRingListUrl(),
				values[0],
				values[1],
				values[2],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		return mHttpApi.doHttpRequestObject(httpGet, new BeautifulRingBuilder());
	}
	
	/**圈子详情*/
	public BeautifulRingDetail getBeatifulRingDetailFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getRingDetailUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new BeautifulDetailBuilder());
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
	public NetResult findPassword(String userid,String passwd,String rdcode)
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
		return  mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
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
	public void updateUserInfo(String username,String mobile,String local,String shenFenZheng,String iconPath,String token)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("token", token),
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000)),
				new BasicNameValuePair("username", username),
                new BasicNameValuePair("mobile", mobile),
                new BasicNameValuePair("avatar", iconPath)
//                new BasicNameValuePair("local", local),
//                new BasicNameValuePair("shenFenZheng", shenFenZheng),
             
		};
		HttpPost httpPost = mHttpApi.createHttpPost(urlManager.updateUserInfoUrl(),
				values[0],
				values[1],
				values[2],
                values[3],
                values[4],
//                values[5],
//                values[6],
				new BasicNameValuePair("fig", Security.get32MD5Str(values)));
		mHttpApi.doHttpRequestObject(httpPost, new NetResultBuilder());
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
	public void showUserMsg()
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
			BasicNameValuePair[] values = {new BasicNameValuePair("token", UserUtil.getUser().getToken()) ,
/*					new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))*/} ; 
			HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getUserMsgUrl(),
					values[0],
//					values[1],
					new BasicNameValuePair("fig", Security.get32MD5Str(values)));
			 mHttpApi.doHttpRequestObject(httpGet, new NetResultBuilder());
	}
	
	// ========================================================================================
	// 订单
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
	public List<CommentItem> showCommentList(String token,String id,String type,String curpage,String perpage)
	    throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
	       BasicNameValuePair[] values = {
	    		    new BasicNameValuePair("id", id),
	                new BasicNameValuePair("type", type),
	                new BasicNameValuePair("curpage", curpage),
	                new BasicNameValuePair("perpage", perpage),
	                new BasicNameValuePair("token", token),
	                new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
	        HttpGet httpGet = mHttpApi.createHttpGet(urlManager.showCommentList(),
	                values[0],
	                values[1],
	                values[2],
	                values[3],
	                values[4],
	                values[5],
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
	public void actionGoodsComment(String token,String goodsId,String typeid,String comment)
        throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {	    
	       BasicNameValuePair[] values = {new BasicNameValuePair("token", token),
	                new BasicNameValuePair("goods_id", goodsId),
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
	        mHttpApi.doHttpRequestObject(httpPost, new ListOrderBuilder());
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
}
