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
               if(jsObj.has("recite")){
            	   HomeItem.Recite recite = new HomeItem.Recite();
            	   JSONObject jsreciteObj = jsObj.getJSONObject("recite");
            	   recite.jump = jsreciteObj.optString("jump");
            	   recite.img = jsreciteObj.optString("img_plus");
            	   homeItem.setRecite(recite);
               }
               if(jsObj.has("value")){
                   JSONArray childJsArray = jsObj.getJSONArray("value");
                   List<HomeItem.Item> childList = new ArrayList<HomeItem.Item>();
                   for(int j = 0;j<childJsArray.length();j++){
                       HomeItem.Item itme = new HomeItem.Item();
                       JSONObject childJsObj = childJsArray.getJSONObject(j);
                       itme.title = childJsObj.optString("title");
                       itme.img = childJsObj.optString("img_plus");
                       itme.image = childJsObj.optString("image_6");
                       itme.city = childJsObj.optString("city");
                       itme.name = childJsObj.optString("name");
                       itme.comments = childJsObj.optString("comments");
                       itme.cityId = childJsObj.optString("cityid");
                       itme.url = childJsObj.optString("url");
                       itme.count = childJsObj.optString("count");
                       itme.jump = childJsObj.optString("jump");
                       itme.goodsId = childJsObj.optString("goods_id");
                       itme.shareId = childJsObj.optString("share_id");
                       itme.user = childJsObj.optString("user");
                       itme.id = childJsObj.optString("id");
                       itme.list = childJsObj.optString("list");
                       itme.type =  childJsObj.optString("type");
                       itme.viewcount =  childJsObj.optString("viewcount");
                       itme.avatar = childJsObj.optString("avatar");
                       itme.top_img = childJsObj.optString("top_img_6");
                       itme.desc = childJsObj.optString("desc");
                       childList.add(itme);
                      
                   }
                   homeItem.setmList(childList);
               }
               if(i==2){
                   android.util.Log.d("222", jsObj.toString());
               }
               list.add(homeItem);
            }
        }
        return list;
    }

}
