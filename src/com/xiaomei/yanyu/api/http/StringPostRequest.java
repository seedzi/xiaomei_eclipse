package com.xiaomei.yanyu.api.http;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class StringPostRequest extends StringRequest {

    private Map<String, String> mParams;

    public StringPostRequest(String url, Map<String, String> params, Listener<String> listener,
            ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
        this.mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = super.getParams();
        if (params == null) {
            return mParams;
        }
        params.putAll(mParams);
        return params;
    }
}
