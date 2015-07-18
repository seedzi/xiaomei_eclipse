package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.CommentItem;
import com.xiaomei.yanyu.bean.ShareSubcomment;

public class ListCommentBuilder extends AbstractJSONBuilder<List<CommentItem>> {

    @Override
    protected List<CommentItem> builder(JSONObject jsonObject) throws JSONException {
        if(DebugRelease.isDebug)
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
        if(jsonArray==null || jsonArray.length()==0)
        	return null;
        Gson gson = new Gson();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsObj = jsonArray.getJSONObject(i);
            CommentItem commentItem = new CommentItem();
            if(jsObj.has("mark_service"))
                commentItem.setMarkService(jsObj.getString("mark_service"));
            if(jsObj.has("user_type"))
                commentItem.setUserType(jsObj.getString("user_type"));
            if(jsObj.has("createdate"))
                commentItem.setCreatedate(jsObj.getString("createdate"));
            if(jsObj.has("userid"))
                commentItem.setUserid(jsObj.getString("userid"));
            if(jsObj.has("is_delete"))
                commentItem.setIsDelete(jsObj.getString("is_delete"));
            if(jsObj.has("mark_environment"))
                commentItem.setMarkEnvironment(jsObj.getString("mark_environment"));
            if(jsObj.has("type"))
                commentItem.setType(jsObj.getString("type"));
            if(jsObj.has("avatar"))
                commentItem.setAvatar(jsObj.getString("avatar"));
            if(jsObj.has("id"))
                commentItem.setId(jsObj.getString("id"));
            if(jsObj.has("mark_effect"))
                commentItem.setMarkEffect(jsObj.getString("mark_effect"));
            if(jsObj.has("username"))
                commentItem.setUsername(jsObj.getString("username"));
            if(jsObj.has("typeid"))
                commentItem.setTypeid(jsObj.getString("typeid"));
            if(jsObj.has("reply_root"))
                commentItem.setReplyRoot(jsObj.getString("reply_root"));
            if(jsObj.has("reply"))
                commentItem.setReply(jsObj.getString("reply"));
            if(jsObj.has("content"))
                commentItem.setContent(jsObj.getString("content"));
            if (jsObj.has("subcomments")) {
                ShareSubcomment[] subcomments = gson.fromJson(jsObj.getString("subcomments"), ShareSubcomment[].class);
                commentItem.setSubcomments(subcomments);
            }
            list.add(commentItem);
        }
        return list;
    }

}
