
package com.xiaomei.yanyu.api.http;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.api.builder.JSONBuilder;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.util.StringUtil;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class HttpApiWithSession extends AbstractHttpApi {

    public HttpApiWithSession(DefaultHttpClient httpClient, String clientVersion) {
        super(httpClient, clientVersion);
        httpClient.addRequestInterceptor(preemptiveAuth, 0);
    }

    private HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

        /**
         * 拦截器可以做�?��其他的东�?请求拦截�?Request Intercepter)
         */
        @Override
        public void process(final HttpRequest request, final HttpContext context)
                throws HttpException, IOException {

            // AuthState authState =
            // (AuthState)context.getAttribute(ClientContext.TARGET_AUTH_STATE);
            // CredentialsProvider credsProvider = (CredentialsProvider)context
            // .getAttribute(ClientContext.CREDS_PROVIDER);
            // HttpHost targetHost =
            // (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            //
            // // If not auth scheme has been initialized yet
            // if (authState.getAuthScheme() == null) {
            // AuthScope authScope = new AuthScope(targetHost.getHostName(),
            // targetHost.getPort());
            // // Obtain credentials matching the target host
            // org.apache.http.auth.Credentials creds =
            // credsProvider.getCredentials(authScope);
            // // If found, generate BasicScheme preemptively
            // if (creds != null) {
            // authState.setAuthScheme(new BasicScheme());
            // authState.setCredentials(creds);
            // }
            // }

        }

    };

    @Override
    public String doHttpRequestString(HttpRequestBase httpRequest)
            throws XiaoMeiCredentialsException, XiaoMeiOtherException,
            XiaoMeiIOException {
        InputStream in = executeHttp(httpRequest);
        try {
//            return StringUtil.convertStreamToString(in);
        	String s = StringUtil.convertStreamToString(in);
        	if(DebugRelease.isDebug)
        	    android.util.Log.d("111", "sss = " + s);
        	return s;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new XiaoMeiIOException(e);
                }
            }
        }
    }

    @Override
    public InputStream doHttpRequestInputStream(HttpRequestBase httpRequest)
            throws XiaoMeiCredentialsException, XiaoMeiOtherException,
            XiaoMeiIOException {
        return super.executeHttp(httpRequest);
    }

    @Override
    public <T> void doHttpRequestList(HttpRequestBase httpRequest, JSONBuilder<T> builder, Collection<T> collection)
            throws XiaoMeiCredentialsException, XiaoMeiIOException, XiaoMeiJSONException, XiaoMeiOtherException {
        builder.buildList(doHttpRequestString(httpRequest), collection);
    }

    @Override
    public <T> T doHttpRequestObject(HttpRequestBase httpRequest, JSONBuilder<T> builder)
            throws XiaoMeiCredentialsException, XiaoMeiIOException, XiaoMeiJSONException, XiaoMeiOtherException {
        return builder.build(doHttpRequestString(httpRequest));
    }


    //	@Override
//	public <T> ArrayList<T> doHttpRequestList(HttpRequestBase httpRequest,
//			Parser<T> parser) throws LibraryCredentialsException,
//			LibraryParseException, LibraryMobileException, IOException {
//		// TODO Auto-generated method stub
//		InputStream in = doHttpRequestInputStream(httpRequest);
//		try {
//			return parser.parseList(AbstractParser.createXmlPullParser(in));
//		} finally {
//			in.close();
//		}
//	}
//
//	@Override
//	public <T> T doHttpRequestObject(HttpRequestBase httpRequest,
//			Parser<T> parser) throws LibraryCredentialsException,
//			LibraryParseException, LibraryMobileException, IOException {
//		// TODO Auto-generated method stub
//		InputStream in = doHttpRequestInputStream(httpRequest);
//		try {
//			return parser.parse(AbstractParser.createXmlPullParser(in));
//		} finally {
//			in.close();
//		}
//	}
}
