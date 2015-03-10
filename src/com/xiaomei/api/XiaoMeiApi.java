package com.xiaomei.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.content.Context;

import com.xiaomei.api.builder.SectionBuilder;
import com.xiaomei.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.api.exception.XiaoMeiIOException;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.api.exception.XiaoMeiOtherException;
import com.xiaomei.api.http.AbstractHttpApi;
import com.xiaomei.api.http.HttpApi;
import com.xiaomei.api.http.HttpApiWithSession;
import com.xiaomei.bean.Section;

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

	public List<Section> getHomeListFromNet()
			throws XiaoMeiCredentialsException, XiaoMeiIOException,
			XiaoMeiJSONException, XiaoMeiOtherException {
		HttpGet httpGet = mHttpApi.createHttpGet(urlManager.getHomeListUrl(),null);
		return mHttpApi.doHttpRequestObject(httpGet, new SectionBuilder());
	}
    
}
