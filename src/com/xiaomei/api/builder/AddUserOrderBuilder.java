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
		if(jsonObject.has("modifydate"))
			order.setModifydate(jsonObject.getString("modifydate"));
		if(jsonObject.has("goods_pay"))
			order.setGoodsPay(jsonObject.getString("goods_pay"));
		if(jsonObject.has("status"))
			order.setStatus(jsonObject.getString("status"));
		if(jsonObject.has("createdate"))
			order.setCreatedate(jsonObject.getString("createdate"));
		if(jsonObject.has("userid"))
			order.setUserid(jsonObject.getString("userid"));
		if(jsonObject.has("goods_name"))
			order.setGoodsName(jsonObject.getString("goods_name"));
		if(jsonObject.has("paydate"))
			order.setPaydate(jsonObject.getString("paydate"));
		if(jsonObject.has("order_id"))
			order.setOrderId(jsonObject.getString("order_id"));
		if(jsonObject.has("goods_img"))
			order.setGoodsImg(jsonObject.getString("goods_img"));
		if(jsonObject.has("goods_amount"))
			order.setGoodsAmount(jsonObject.getString("goods_amount"));
		if(jsonObject.has("username"))
			order.setUsername(jsonObject.getString("username"));
		if(jsonObject.has("goods_price"))
			order.setGoodsPrice(jsonObject.getString("goods_price"));
		if(jsonObject.has("goods_status"))
			order.setGoodsStatus(jsonObject.getString("goods_status"));
		if(jsonObject.has("pay_type"))
			order.setPayType(jsonObject.getString("pay_type"));
		if(jsonObject.has("order_num"))
			order.setOrderNum(jsonObject.getString("order_num"));
		if(jsonObject.has("goods_id"))
			order.setGoodsId(jsonObject.getString("goods_id"));
		if(jsonObject.has("goods_code"))
			order.setGoodsCode(jsonObject.getString("goods_code"));
		if(jsonObject.has("userinfo")){
			JSONObject jObj = jsonObject.getJSONObject("userinfo");
			Order2.UserInfo userInfo = new Order2.UserInfo();
			if(jObj.has("mobile"))
				userInfo.setMobile(jObj.getString("mobile"));
			if(jObj.has("passport"))
				userInfo.setPassport(jObj.getString("passport"));
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
