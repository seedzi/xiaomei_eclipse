package com.xiaomei.yanyu.bean;

import java.util.List;

public class UserShare {

	private String username;
	
	private String avatar;
	
	private String time;
	
	private String content;
	
	private List<String> imgs;
	
	private List<Comment> commtents;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public List<Comment> getCommtents() {
		return commtents;
	}

	public void setCommtents(List<Comment> commtents) {
		this.commtents = commtents;
	}



	public static class Comment{
		
		private String username;
		
		private String avatar;
		
		private String content;
		
		private String time;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
		
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
