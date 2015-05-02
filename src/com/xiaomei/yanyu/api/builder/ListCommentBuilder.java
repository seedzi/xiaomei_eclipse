package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.bean.CommentItem;

public class ListCommentBuilder extends AbstractJSONBuilder<List<CommentItem>> {

    @Override
    protected List<CommentItem> builder(JSONObject jsonObject) throws JSONException {
        android.util.Log.d("json", jsonObject.toString());
        List<CommentItem> list = null;
        JSONArray jsonArray = null;
        if(jsonObject.has("msg")){
        	jsonObject = jsonObject.getJSONObject("msg");
            jsonArray = jsonObject.getJSONArray("list");
            list = new ArrayList<CommentItem>();
        }else{
            return null;
        }
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsObj = jsonArray.getJSONObject(i);
            CommentItem commentItem = new CommentItem();
            if(jsObj.has("mark_service"))
                commentItem.setMarkService(jsObj.getString("mark_service"));
            if(jsObj.has("user_type"))
                commentItem.setMarkService(jsObj.getString("user_type"));
            if(jsObj.has("createdate"))
                commentItem.setCreatedate(jsObj.getString("createdate"));
            if(jsObj.has("userid"))
                commentItem.setMarkService(jsObj.getString("userid"));
            if(jsObj.has("mark_service"))
                commentItem.setMarkService(jsObj.getString("mark_service"));
            if(jsObj.has("is_delete"))
                commentItem.setMarkService(jsObj.getString("is_delete"));
            if(jsObj.has("mark_environment"))
                commentItem.setMarkService(jsObj.getString("mark_environment"));
            if(jsObj.has("type"))
                commentItem.setMarkService(jsObj.getString("type"));
            if(jsObj.has("avatar"))
                commentItem.setMarkService(jsObj.getString("avatar"));
            if(jsObj.has("content"))
                commentItem.setMarkService(jsObj.getString("content"));
            if(jsObj.has("id"))
                commentItem.setMarkService(jsObj.getString("id"));
            if(jsObj.has("mark_effect"))
                commentItem.setMarkService(jsObj.getString("mark_effect"));
            if(jsObj.has("username"))
                commentItem.setMarkService(jsObj.getString("username"));
            if(jsObj.has("typeid"))
                commentItem.setMarkService(jsObj.getString("typeid"));
            if(jsObj.has("reply_root"))
                commentItem.setMarkService(jsObj.getString("reply_root"));
            if(jsObj.has("reply"))
                commentItem.setMarkService(jsObj.getString("reply"));
            if(jsObj.has("content"))
                commentItem.setContent(jsObj.getString("content"));
            list.add(commentItem);
        }
        return list;
    }

}
