package com.xiaomei.api;

import android.content.Context;

import com.xiaomei.api.http.AbstractHttpApi;
import com.xiaomei.api.http.HttpApi;
import com.xiaomei.api.http.HttpApiWithSession;
import com.xiaomei.api.http.HttpUrlManager;

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

}
