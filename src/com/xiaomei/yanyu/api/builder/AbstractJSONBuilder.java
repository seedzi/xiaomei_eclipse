package com.xiaomei.yanyu.api.builder;

import android.util.Log;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public abstract class AbstractJSONBuilder<T> implements JSONBuilder<T> {

	private static final String TAG = AbstractJSONBuilder.class.getSimpleName();
    protected abstract T builder(JSONObject jsonObject) throws JSONException;

    @Override
    public final T build(String json) throws XiaoMeiJSONException {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return build(jsonObject);
        } catch (JSONException e) {
            throw new XiaoMeiJSONException(e);
        }
    }

    @Override
    public final T build(JSONObject jsonObject) throws XiaoMeiJSONException{
          try{
              return builder(jsonObject);
          } catch (JSONException e) {
              throw new XiaoMeiJSONException(e);
          }
    }

    /**
     * inner builder util
     *
     * @param json
     * @return
     * @throws XiaoMeiJSONException
     */
    public final void buildList(String json, Collection<T> collection) throws XiaoMeiJSONException {
        try {
            if(DebugRelease.isDebug)
                Log.d(TAG, "JSON="+json);
        	if(json==null||json.trim().equals("")){
        		return;
        	}
            JSONArray jsonArray = new JSONArray(json);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                collection.add(build(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            throw new XiaoMeiJSONException(e);
        }

    }
}
