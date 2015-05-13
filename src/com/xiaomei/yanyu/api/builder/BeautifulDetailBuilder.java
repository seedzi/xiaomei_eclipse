package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.api.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.BeautifulRingDetail;

public class BeautifulDetailBuilder extends AbstractJSONBuilder<BeautifulRingDetail> {

	@Override
	protected BeautifulRingDetail builder(JSONObject jsonObject)
			throws JSONException {
	    if(DebugRelease.isDebug)
	        android.util.Log.d("json", jsonObject.toString());
		jsonObject = jsonObject.getJSONObject("msg");
		BeautifulRingDetail beautifulRingDetail = new BeautifulRingDetail();
		if(jsonObject.has("id"))
			beautifulRingDetail.setId(jsonObject.getString("id"));
		if(jsonObject.has("goods_id"))
			beautifulRingDetail.setGoodsId(jsonObject.getString("goods_id"));
		if(jsonObject.has("hosp_id"))
			beautifulRingDetail.setHospId(jsonObject.getString("hosp_id"));
		if(jsonObject.has("userid"))
			beautifulRingDetail.setUserid(jsonObject.getString("userid"));
		if(jsonObject.has("username"))
			beautifulRingDetail.setUsername(jsonObject.getString("username"));
		if(jsonObject.has("avatar"))
			beautifulRingDetail.setAvatar(jsonObject.getString("avatar"));
		if(jsonObject.has("share_title"))
			beautifulRingDetail.setShareTitle(jsonObject.getString("share_title"));
		if(jsonObject.has("share_des"))
			beautifulRingDetail.setShareDes(jsonObject.getString("share_des"));
		if(jsonObject.has("sort_order"))
			beautifulRingDetail.setSortOrder(jsonObject.getString("sort_order"));
		if(jsonObject.has("is_open"))
			beautifulRingDetail.setIsOpen(jsonObject.getString("is_open"));
		if(jsonObject.has("share_mark"))
			beautifulRingDetail.setShareMake(jsonObject.getString("share_mark"));
		if(jsonObject.has("share_file"))
			beautifulRingDetail.setShareFile(jsonObject.getString("share_file"));
		if(jsonObject.has("num_favors"))
			beautifulRingDetail.setNumFavors(jsonObject.getString("num_favors"));
		if(jsonObject.has("num_comments"))
			beautifulRingDetail.setNumComments(jsonObject.getString("num_comments"));
		if(jsonObject.has("createdate"))
			beautifulRingDetail.setCreatedate(jsonObject.getString("createdate"));
		if(jsonObject.has("image_6"))
			beautifulRingDetail.setImage(jsonObject.getString("image_6"));
		if(jsonObject.has("images")){
			List<BeautifulRingDetail.Item> list = new ArrayList<BeautifulRingDetail.Item>();
			JSONArray jsonArray = jsonObject.getJSONArray("images");
			for(int i=0;i<jsonArray.length();i++){
				BeautifulRingDetail.Item item = new BeautifulRingDetail.Item();
				JSONObject job = jsonArray.getJSONObject(i);
				if(job.has("id"))
					item.setId(job.getString("id"));
				if(job.has("type"))
					item.setType(job.getString("type"));
				if(job.has("typeid"))
					item.setTypeid(job.getString("typeid"));
				if(job.has("image_6"))
					item.setUrl(job.getString("image_6"));
				if(job.has("title"))
					item.setTilte(job.getString("title"));
				list.add(item);
			}
			beautifulRingDetail.setItems(list);
		}
		return beautifulRingDetail;
	}

	
	/*
	{
	    "msg": {
	        "share_type": "1",
	        "share_mark": "0",
	        "userid": "0",
	        "createdate": "1430622104",
	        "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165807_21061.jpg",
	        "avatar": "0",
	        "id": "19000005",
	        "share_des": "清潭洞位于大名鼎鼎的江南区，是首尔首屈一指的富人区，同时也是著名的商业中心。这里不仅汇集了很多顶级店铺、时尚潮店、还是许多上流社会精英、富豪和影视明星的置地首选。在韩国，清潭洞仿佛象征着财富和权力，散发着高贵而典雅的气息，吸引着众多追随者慕名前来。",
	        "num_comments": "0",
	        "username": "0",
	        "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165801_74142.jpg",
	        "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165754_57218.jpg",
	        "images": [
	            {
	                "id": "147",
	                "title": "地铁清潭站附近的Galleria百货店里名品众多，并且因为高级的质感和独特的设计，十分受到消费者的喜爱，高贵且不堪平庸的你，又怎么会错过呢~",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165829_40790.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165824_22677.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165835_77814.jpg",
	                "sort_order": "0",
	                "type": "share"
	            },
	            {
	                "id": "148",
	                "title": "GUCCI在清潭洞设有在韩国最大的旗舰店，更是保证了与国际同步更新，让你随时掌握时尚的脉搏。",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165854_80445.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165846_96369.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165900_27179.jpg",
	                "sort_order": "0",
	                "type": "share"
	            },
	            {
	                "id": "149",
	                "title": "深受卡梅隆·迪亚兹、妮可·里奇等大牌明星追捧的菲利林3.1 也在林荫树街开设了店铺，菲利林3.1是众多新兴时尚品牌中的成功典范。",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165919_50559.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165913_96323.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165925_96124.jpg",
	                "sort_order": "0",
	                "type": "share"
	            },
	            {
	                "id": "150",
	                "title": "源于1952年的潮牌老店--纪梵希在韩国的旗舰店也选址在了清潭洞.该店铺不仅位于清潭洞的黄金地段更是请来了意大利设计师PLUARCH精心打造。",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165944_96498.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165938_58356.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502165951_40474.jpg",
	                "sort_order": "0",
	                "type": "share"
	            },
	            {
	                "id": "151",
	                "title": "源于1952年的潮牌老店--纪梵希在韩国的旗舰店也选址在了清潭洞.该店铺不仅位于清潭洞的黄金",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170010_93948.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170003_76512.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170016_53118.jpg",
	                "sort_order": "0",
	                "type": "share"
	            },
	            {
	                "id": "152",
	                "title": "若乘坐首尔地铁，可以从7号线清潭站8号和9号出口出来，沿着三成路走5分钟，就可抵达清潭洞名品街。",
	                "image_6": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170036_62884.jpg",
	                "link": null,
	                "image_5": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170030_11330.jpg",
	                "typeid": "19000005",
	                "image_plus": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/share19000005\/20150502170042_78825.jpg",
	                "sort_order": "0",
	                "type": "share"
	            }
	        ],
	        "num_favors": "0",
	        "share_title": "韩国购�
*/
}
