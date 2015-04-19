package com.xiaomei.api;

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

import com.xiaomei.XiaoMeiApplication;
import com.xiaomei.api.builder.AddUserOrderBuilder;
import com.xiaomei.api.builder.BeautifulDetailBuilder;
import com.xiaomei.api.builder.BeautifulRingBuilder;
import com.xiaomei.api.builder.GoodsBuilder;
import com.xiaomei.api.builder.HospitalBuilder;
import com.xiaomei.api.builder.ListOrderBuilder;
import com.xiaomei.api.builder.LoginOutBuilder;
import com.xiaomei.api.builder.NetResultBuilder;
import com.xiaomei.api.builder.SectionBuilder;
import com.xiaomei.api.builder.UploadFIleBuilder;
import com.xiaomei.api.builder.UserLoginBuilder;
import com.xiaomei.api.builder.UserRegisterBuilder;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.api.http.AbstractHttpApi;
import com.xiaomei.api.http.HttpApi;
import com.xiaomei.api.http.HttpApiWithSession;
import com.xiaomei.bean.BeautifulRing;
import com.xiaomei.bean.BeautifulRingDetail;
import com.xiaomei.bean.Goods;
import com.xiaomei.bean.Hospital;
import com.xiaomei.bean.LoginResult;
import com.xiaomei.bean.NetResult;
import com.xiaomei.bean.Order;
import com.xiaomei.bean.Order2;
import com.xiaomei.bean.Section;
import com.xiaomei.bean.User;
import com.xiaomei.bean.User.UserInfo;
import com.xiaomei.util.FileUtils;
import com.xiaomei.util.Security;
import com.xiaomei.util.UserUtil;

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
	public List<Section> getHomeListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getHomeListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new SectionBuilder());
	}
	
	/**商城*/
	public List getMallListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new HospitalBuilder());
	}
	
	/**商品列表*/
	public List<Goods> getGoodsListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMallListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new GoodsBuilder());
	}
	
	/**机构*/
	public List<Hospital> getMechanismListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getMechanismListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new HospitalBuilder());
	}
	
	/**圈子*/
	public List<BeautifulRing> getBeatifulRingListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getRingListUrl(),null);
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
	 * 新增订单接口
	 */
	public Order2 addUserOrder(String userid,String goodsId,String username,String mobile,String passport,String token)
		throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
		BasicNameValuePair[] values = {new BasicNameValuePair("userid", userid),
				new BasicNameValuePair("goods_id", goodsId),
				new BasicNameValuePair("username", username),
				new BasicNameValuePair("mobile", mobile),
				new BasicNameValuePair("passport", passport),
				new BasicNameValuePair("action", "add"),
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
	// 产品
	// ========================================================================================


}
