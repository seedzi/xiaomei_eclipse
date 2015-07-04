package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.socialize.utils.Log;
import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.UserShare;

public class UserShareListBuilder extends AbstractJSONBuilder<List<UserShare>> {

    @Override
    protected List<UserShare> builder(JSONObject jsonObject) throws JSONException {
    	if(DebugRelease.isDebug)
    		Log.d("json", jsonObject.toString());
    	List<UserShare> list = new ArrayList<UserShare>();
    	UserShare userShare = null;
    	JSONArray jsonArray = null;
    	if(jsonObject.has("msg"))
    		jsonObject = jsonObject.getJSONObject("msg");
    	else
    		return list;
    	if(jsonObject.has("shares"))
    		jsonArray = jsonObject.getJSONArray("shares");
    	else
    		return list;
    	
    	for(int i=0;i<jsonArray.length();i++){
    		JSONObject jObj = jsonArray.getJSONObject(i);
    		userShare = new UserShare();
    		if(jObj.has("username"))
    			userShare.setUsername(jObj.getString("username"));
    		if(jObj.has("avatar"))
    			userShare.setAvatar(jObj.getString("avatar"));
    		if(jObj.has("share_des"))
    			userShare.setContent(jObj.getString("share_des"));
    		if(jObj.has("createdate"))
    			userShare.setTime(jObj.getString("createdate"));
    		// photo
    		if(jObj.has("images")){
    			List<String> imgs = new ArrayList<String>();
    			JSONArray imgsArray = jObj.getJSONArray("images");
    			for(int j=0;j<imgsArray.length();j++){
    				JSONObject imgObj = imgsArray.getJSONObject(j);
    				if(imgObj.has("image_plus"))
    					imgs.add(imgObj.getString("image_plus"));
    			}
    			userShare.setImgs(imgs);
    		}
    		// comments
     		if(jObj.has("comments")){
    			List<UserShare.Comment> comments = new ArrayList<UserShare.Comment>();
    			JSONObject commentsObj = jObj.getJSONObject("comments");
    			JSONArray commentsArray = null;
				if(commentsObj.has("list")){
					commentsArray = commentsObj.getJSONArray("list");
	    			for(int k=0;k<commentsArray.length();k++){
	    				JSONObject commentObj = commentsArray.getJSONObject(k);
						UserShare.Comment comment = new UserShare.Comment();
						if(commentObj.has("username"))
							comment.setUsername(commentObj.getString("username"));
						if(commentObj.has("content"))
							comment.setContent(commentObj.getString("content"));
						if(commentObj.has("avatar"))
							comment.setAvatar(commentObj.getString("avatar"));
						if(commentObj.has("createdate"))
							comment.setTime(commentObj.getString("createdate"));
						comments.add(comment);
	    			}
	    			userShare.setCommtents(comments);
				}
    		}
     		list.add(userShare);
    	}
        return list;
    }

    /*
    {
        "msg": {
            "shares": [
                {
                    "share_type": "1",
                    "link": "http:\/\/z.drxiaomei.com\/share.php?shareid=19003073",
                    "share_mark": "0",
                    "userid": "19063547",
                    "createdate": "1435987022",
                    "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "avatar": "0",
                    "id": "19003073",
                    "share_des": "是的，效果不错啊，美白完了同学都不认识我了",
                    "num_comments": "0",
                    "username": "张飞荣",
                    "num_view": 41,
                    "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "images": [
                        {
                            "id": "584",
                            "title": "是的，效果不错啊，美白完了同学都不认识我了",
                            "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "image_4": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "link": null,
                            "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "typeid": "19003073",
                            "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "sort_order": "0",
                            "type": "share_user"
                        },
                        {
                            "id": "585",
                            "title": "是的，效果不错啊，美白完了同学都不认识我了",
                            "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "image_4": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "link": null,
                            "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "typeid": "19003073",
                            "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "sort_order": "0",
                            "type": "share_user"
                        },
                        {
                            "id": "586",
                            "title": "是的，效果不错啊，美白完了同学都不认识我了",
                            "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043010_27156.jpg",
                            "image_4": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043010_27156.jpg",
                            "link": null,
                            "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043010_27156.jpg",
                            "typeid": "19003073",
                            "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043010_27156.jpg",
                            "sort_order": "0",
                            "type": "share_user"
                        }
                    ],
                    "num_favors": "0",
                    "share_title": "是的，效果不错啊，美白完了同学都不认识我了",
                    "comments": {
                        "total": 1,
                        "list": [
                            {
                                "content": "恩是的",
                                "id": 19003337,
                                "username": "张飞荣",
                                "subcomments": [
                                    {
                                        "to_avatar": "",
                                        "commentid": "19003337",
                                        "id": 1,
                                        "to_id": "19063547",
                                        "from_name": "张飞荣",
                                        "from_id": "19063547",
                                        "createdate": 1435991539,
                                        "from_avatar": "",
                                        "shareid": "19003073",
                                        "comment": "哪有",
                                        "to_name": "张飞荣"
                                    }
                                ],
                                "typeid": "19003073",
                                "createdate": 1435991395,
                                "userid": "19063547",
                                "type": "share",
                                "avatar": ""
                            }
                        ]
                    }
                },
                {
                    "share_type": "1",
                    "link": "http:\/\/z.drxiaomei.com\/share.php?shareid=19003072",
                    "share_mark": "0",
                    "userid": "19063547",
                    "createdate": "1435986905",
                    "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "avatar": "0",
                    "id": "19003072",
                    "share_des": "啊，效果不错啊，美白完了同学都不认识我了",
                    "num_comments": "0",
                    "username": "张飞荣",
                    "num_view": 42,
                    "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                    "images": [
                        {
                            "id": "581",
                            "title": "啊，效果不错啊，美白完了同学都不认识我了",
                            "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "image_4": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "link": null,
                            "image_5": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "typeid": "19003072",
                            "image_plus": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043116_82505.jpg",
                            "sort_order": "0",
                            "type": "share_user"
                        },
                        {
                            "id": "582",
                            "title": "啊，效果不错啊，美白完了同学都不认识我了",
                            "image_6": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "image_4": "http:\/\/static.drxiaomei.com\/images\/share19003064\/20150702043043_40843.jpg",
                            "li
*/
}
