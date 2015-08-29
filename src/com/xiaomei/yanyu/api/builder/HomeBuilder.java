package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.bean.HomeItem;

public class HomeBuilder extends AbstractJSONBuilder<List<HomeItem>>{

    @Override
    protected List<HomeItem> builder(JSONObject jsonObject) throws JSONException {
        List<HomeItem> list = new ArrayList<HomeItem>();
        if(jsonObject.has("msg")){
            JSONArray jsonArray = jsonObject.getJSONArray("msg");
            for(int i = 0;i<jsonArray.length();i++){
               JSONObject jsObj = jsonArray.getJSONObject(i);
               HomeItem homeItem = new HomeItem();
               if(jsObj.has("type")){
                   homeItem.setType(jsObj.optString("type"));
               }
               if(jsObj.has("value")){
                   JSONArray childJsArray = jsObj.getJSONArray("value");
                   List<HomeItem.Item> childList = new ArrayList<HomeItem.Item>();
                   for(int j = 0;j<childJsArray.length();j++){
                       HomeItem.Item itme = new HomeItem.Item();
                       JSONObject childJsObj = childJsArray.getJSONObject(j);
                       itme.title = childJsObj.optString("title");
                       childList.add(itme);
                   }
                   homeItem.setmList(childList);
               }
            }
        }
        return list;
    }

}
