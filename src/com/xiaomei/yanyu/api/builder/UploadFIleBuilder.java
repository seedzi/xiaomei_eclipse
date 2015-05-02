package com.xiaomei.yanyu.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadFIleBuilder extends AbstractJSONBuilder<String> {

    @Override
    protected String builder(JSONObject jsonObject) throws JSONException {
        android.util.Log.d("json", "jsonObject = " + jsonObject.toString());
        jsonObject =  jsonObject.getJSONObject("msg");
        String upload_file_url = jsonObject.getString("avatar");    
        return upload_file_url;
    }

}
