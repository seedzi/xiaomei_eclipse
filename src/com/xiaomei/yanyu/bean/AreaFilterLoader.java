package com.xiaomei.yanyu.bean;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.BizResult;
import com.xiaomei.yanyu.api.HttpUrlManager;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.api.http.HttpApi;
import com.xiaomei.yanyu.bean.Area.Filter;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class AreaFilterLoader extends AsyncTaskLoader<Object> {

    private Filter[] filters;

    public AreaFilterLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        HttpApi httpApi = XiaoMeiApplication.getInstance().getApi().getHttpApi();
        HttpGet httpGet = httpApi.createHttpGet(HttpUrlManager.AREA_FILTER_LIST);
        try {
            BizResult result = httpApi.doHttpRequestResult(httpGet);
            JsonObject jsonObject = result.getMessage().getAsJsonObject();
            Gson gson = new Gson();
            Filter countryFilter = gson.fromJson(jsonObject.get(Filter.COUNTRY), Filter.class);
            Filter goodstypeFilter = gson.fromJson(jsonObject.get(Filter.SPECIAL), Filter.class);
            filters = new Filter[] {
                    countryFilter, goodstypeFilter
            };
            return filters;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XiaoMeiOtherException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        if (filters != null) {
            deliverResult(filters);
            return;
        }

        forceLoad();
    }
}
