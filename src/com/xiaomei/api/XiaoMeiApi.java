package com.xiaomei.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

import com.xiaomei.api.builder.BeautifulRingBuilder;
import com.xiaomei.api.builder.HospitalBuilder;
import com.xiaomei.api.builder.NetResultBuilder;
import com.xiaomei.api.builder.SectionBuilder;
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
import com.xiaomei.bean.Hospital;
import com.xiaomei.bean.LoginResult;
import com.xiaomei.bean.NetResult;
import com.xiaomei.bean.Section;
import com.xiaomei.util.Security;

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
	public NetResult userLogin(String userid,String passwd) 
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
	 * 获取验证码
	 */
	public void getVerificationCode(String telno)
			throws XiaoMeiCredentialsException,XiaoMeiIOException,XiaoMeiJSONException ,XiaoMeiOtherException {
				BasicNameValuePair[] values = {new BasicNameValuePair("telno", telno) ,
				new BasicNameValuePair("uptime", String.valueOf(System.currentTimeMillis()/1000))} ; 
				HttpPost httpPost = mHttpApi.createHttpPost(urlManager.getVerificationCodeUrl(),
						values[0],
						values[1],
						new BasicNameValuePair("fig", Security.get32MD5Str(values)));
				String responseCode =  mHttpApi.doHttpRequestString(httpPost);
				Log.d("111", "responseCode = " + responseCode);
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
}
