package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaomei.yanyu.bean.UserShare;
import com.xiaomei.yanyu.bean.UserShare.ShareImage;;

public class UserShareListBuilder extends AbstractJSONBuilder<List<UserShare>> {

    @Override
    protected List<UserShare> builder(JSONObject jsonObject) throws JSONException {
        String json = jsonObject.getJSONObject("msg").toString();
        ArrayList<UserShare> list = new ArrayList<UserShare>();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(json).getAsJsonObject().getAsJsonArray("shares");
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject element = jsonArray.get(i).getAsJsonObject();
            UserShare userShares = gson.fromJson(element, UserShare.class);
            JsonElement images = element.get("images");
            // images may be BOOLEAN false. Bad design...
            userShares.setShareImages(images.isJsonArray() ? gson.fromJson(images, ShareImage[].class) : null);
            if(!userShares.getId().equals("false")){
            	list.add(userShares);
            }
        }
        return list;
    }

}
