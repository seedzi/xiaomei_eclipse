package com.xiaomei.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.bean.Order;

public class ListOrderBuilder extends AbstractJSONBuilder<List<Order>> {

	@Override
	protected List<Order> builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		List<Order> list = new ArrayList<Order>();
		if(jsonObject.has("msg")){
			JSONArray jsonArray = jsonObject.getJSONArray("msg");
			for(int i=0 ;i<jsonArray.length();i++){
				JSONObject jObj = jsonArray.getJSONObject(i);
				Order order = new Order();
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
					order.setOrderid(jsonObject.getString("order_id"));
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
					JSONObject jObject = jsonObject.getJSONObject("userinfo");
					Order.UserInfo userInfo = new Order.UserInfo();
					if(jObject.has("mobile"))
						userInfo.setMobile(jObject.getString("mobile"));
					if(jObject.has("passport"))
						userInfo.setPassport(jObject.getString("passport"));
					order.setUserInfo(userInfo);
				}
				list.add(order);
			}
		}
		return list;
	}
	/*
	04-11 14:11:03.015: D/json(19758): {"msg":[{"modifydate":"1428729198","goods_pay":"6000","status":"1","userid":"19062271","createdate":"1428729198","goods_name":"纤纤玉腿的秘密-美国进口Botox","paydate":"0","userinfo":"{\"mobile\":\"15010768102\",\"passport\":\"123\"}","order_id":"19062291","goods_img":"http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods_common\/20150409103832_25154.jpg","goods_amount":"6000","id":"19062291","order_amount":"6000","username":"未定义","goods_price":"6000","goods_status":"0","pay_type":"0","order_num":"201504111313185450","goods_id":"1015","goods_code":"1811"}],"code":0}
*/
}
