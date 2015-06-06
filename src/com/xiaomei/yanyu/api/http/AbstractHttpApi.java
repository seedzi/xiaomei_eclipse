package com.xiaomei.yanyu.api.http;

/**
 * Copyright 2012 sun peng shuai
 */

import android.util.Log;

import com.umeng.socialize.net.utils.BaseNCodec;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractHttpApi implements HttpApi {

    private static final String DEFAULT_CLIENT_VERSION = "com.yuekuapp.media";
    private static final String CLIENT_VERSION_HEADER = "User-Agent";
    private static final int TIMEOUT = 10;

    private final DefaultHttpClient mHttpClient;
    private final String mClientVersion;

    protected AbstractHttpApi(DefaultHttpClient httpClient, String clientVersion) {
        mHttpClient = httpClient;
        if (clientVersion != null) {
            mClientVersion = clientVersion;
        } else {
            mClientVersion = DEFAULT_CLIENT_VERSION;
        }
    }


    public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs) {
    	HttpGet httpGet = null;
    	if(nameValuePairs==null){
            httpGet = new HttpGet(url);	
    	}else{
            String query = URLEncodedUtils.format(stripNulls(nameValuePairs),
                    HTTP.UTF_8);
            if(url.contains("?")){
                httpGet = new HttpGet(url + "&" + query);	
            }else{
                httpGet = new HttpGet(url + "?" + query);	
            }
    	}
        httpGet.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        return httpGet;
    }

    public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs) throws XiaoMeiIOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(CLIENT_VERSION_HEADER, mClientVersion);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(
                    stripNulls(nameValuePairs), HTTP.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            throw new XiaoMeiIOException(
                    "Unable to encode http parameters.");
        }
        return httpPost;
    }

    /**
     * Create a thread-safe client. This client does not do redirecting, to
     * allow us to capture correct "error" codes.
     *
     * @return HttpClient
     */
    public static DefaultHttpClient createHttpClient() {
        // Sets up the http part of the service.
        final SchemeRegistry supportedSchemes = new SchemeRegistry();

        // Register the "http" protocol scheme, it is required
        // by the default operator to look up socket factories.
        final SocketFactory sf = PlainSocketFactory.getSocketFactory();
        supportedSchemes.register(new Scheme("http", sf, 80));

        final HttpParams httpParams = createHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);

        final ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                httpParams, supportedSchemes);
        return new DefaultHttpClient(ccm, httpParams);
    }


    protected InputStream executeHttp(HttpRequestBase httpRequest)
            throws XiaoMeiIOException, XiaoMeiCredentialsException, XiaoMeiOtherException {
        if(DebugRelease.isDebug)
            Log.d("URL", "url="+httpRequest.getURI().toString());
//        httpRequest.addHeader("host", "api.drxiaomei.com");
        HttpResponse response = executeHttpRequest(httpRequest);

        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200:
                try{
                    return response.getEntity().getContent();
                }catch (Exception e){
                    throw new XiaoMeiIOException(e);
                }
            case 401:
                try{
                    response.getEntity().consumeContent();
                }catch (Exception e){
                    throw new XiaoMeiIOException(e);
                }
                throw new XiaoMeiCredentialsException("statusCode:" + statusCode + "  statusLine:" + response.getStatusLine()
                        .toString());

            case 404:
                try{
                    response.getEntity().consumeContent();
                }catch (Exception e){
                    throw new XiaoMeiIOException(e);
                }
                throw new XiaoMeiOtherException("statusCode: " + statusCode + "  statusLine:" + response.getStatusLine()
                        .toString());

            case 500:
                try{
                    response.getEntity().consumeContent();
                }catch (Exception e){
                    throw new XiaoMeiIOException(e);
                }
                throw new XiaoMeiOtherException("statusCode:" + statusCode + "  statusLine:" + response.getStatusLine()
                        .toString());
            default:
                try{
                    response.getEntity().consumeContent();
                }catch (Exception e){
                    throw new XiaoMeiIOException(e);
                }
                throw new XiaoMeiOtherException("Error connecting to Library Server: "
                        + statusCode + ". Try again later.");
        }
    }

    /**
     * execute() an httpRequest catching exceptions and returning null instead.
     *
     * @param httpRequest
     * @return
     */
    protected HttpResponse executeHttpRequest(HttpRequestBase httpRequest)
            throws XiaoMeiIOException {
        try {
            mHttpClient.getConnectionManager().closeExpiredConnections();
            return mHttpClient.execute(httpRequest);
        } catch (IOException e) {
            httpRequest.abort();
            throw new XiaoMeiIOException(e);
        }
    }

    private List<NameValuePair> stripNulls(NameValuePair... nameValuePairs) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for(NameValuePair param:nameValuePairs){
            if (param.getValue() != null) {
                params.add(param);
            }
        }
        return params;
    }

    /**
     * Create the default HTTP protocol parameters.
     */
    private static HttpParams createHttpParams() {
        final HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
        paramsBean.setVersion(HttpVersion.HTTP_1_1);

        paramsBean.setContentCharset(HTTP.UTF_8);
        // paramsBean.s
        ConnManagerParams.setTimeout(params, 1000);
        // self added ---
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSoTimeout(params, TIMEOUT * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        return params;
    }
    
    public HttpResponse doHttpRequestResponse(
            HttpRequestBase httpRequest) throws XiaoMeiCredentialsException,
            XiaoMeiIOException, XiaoMeiOtherException{
        if(DebugRelease.isDebug)
            Log.d("URL", "url="+httpRequest.getURI().toString());
        HttpResponse response = executeHttpRequest(httpRequest);
        return response;
    }
}
