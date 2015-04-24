package com.xiaomei.api.builder;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaomei.bean.Order;
import com.xiaomei.bean.Order2;

import android.util.Log;

public class AddUserOrderBuilder extends AbstractJSONBuilder<Order2> {

	
	@Override
	protected Order2 builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		Order2 order = null;
		if(jsonObject.has("msg"))
			order = new Order2();
		JSONObject jsonObject1 = jsonObject.getJSONObject("msg");
		if(jsonObject1.has("modifydate"))
			order.setModifydate(jsonObject1.getString("modifydate"));
		if(jsonObject1.has("goods_pay"))
			order.setGoodsPay(jsonObject1.getString("goods_pay"));
		if(jsonObject1.has("status"))
			order.setStatus(jsonObject1.getString("status"));
		if(jsonObject1.has("createdate"))
			order.setCreatedate(jsonObject1.getString("createdate"));
		if(jsonObject1.has("userid"))
			order.setUserid(jsonObject1.getString("userid"));
		if(jsonObject1.has("goods_name"))
			order.setGoodsName(jsonObject1.getString("goods_name"));
		if(jsonObject1.has("paydate"))
			order.setPaydate(jsonObject1.getString("paydate"));
		if(jsonObject1.has("order_id"))
			order.setOrderId(jsonObject1.getString("order_id"));
		if(jsonObject1.has("goods_img"))
			order.setGoodsImg(jsonObject1.getString("goods_img"));
		if(jsonObject1.has("goods_amount"))
			order.setGoodsAmount(jsonObject1.getString("goods_amount"));
		if(jsonObject1.has("username"))
			order.setUsername(jsonObject1.getString("username"));
		if(jsonObject1.has("goods_price"))
			order.setGoodsPrice(jsonObject1.getString("goods_price"));
		if(jsonObject1.has("goods_status"))
			order.setGoodsStatus(jsonObject1.getString("goods_status"));
		if(jsonObject1.has("pay_type"))
			order.setPayType(jsonObject1.getString("pay_type"));
		if(jsonObject1.has("order_num"))
			order.setOrderNum(jsonObject1.getString("order_num"));
		if(jsonObject1.has("goods_id"))
			order.setGoodsId(jsonObject1.getString("goods_id"));
		if(jsonObject1.has("goods_code"))
			order.setGoodsCode(jsonObject1.getString("goods_code"));
		if(jsonObject1.has("userinfo")){
//			JSONObject jObj = jsonObject1.getJSONObject("userinfo");
			Order2.UserInfo userInfo = new Order2.UserInfo();
/*			if(jObj.has("mobile"))
				userInfo.setMobile(jObj.getString("mobile"));
			if(jObj.has("passport"))
				userInfo.setPassport(jObj.getString("passport"));*/
			order.setUserInfo(userInfo);
		}
		return order;
	}
/*
	{
	    "msg": {
	        "modifydate": 1428719263,
	        "goods_pay": false,
	        "status": 1,
	        "createdate": 1428719263,
	        "userid": "19062271",
	        "goods_name": false,
	        "paydate": 0,
	        "userinfo": "{\"mobile\":\"15010768102\",\"passport\":\"123\"}",
	        "order_id": 19062279,
	        "goods_img": false,
	        "goods_amount": false,
	        "id": 19062279,
	        "order_amount": false,
	        "username": "未定义",
	        "goods_price": false,
	        "goods_status": 0,
	        "pay_type": 0,
	        "order_num": "2015041110274333510",
	        "goods_id": "1015",
	        "goods_code": "1197"
	    },
	    "code": 0
	}\"15010768102\",\"passport\":\"123\"}",
	        "order_id": 19062279,
	        "goods_img": false,
	        "goods_amount": false,
	        "id": 19062279,
	        "order_amount": false,
	        "username": "未定义",
	        "goods_price": false,
	        "goods_status": 0,
	        "pay_type": 0,
	        "order_num": "2015041110274333510",
	        "goods_id": "1015",
	        "goods_code": "1197"
	    },
	    "code": 0
	}

*/
}
