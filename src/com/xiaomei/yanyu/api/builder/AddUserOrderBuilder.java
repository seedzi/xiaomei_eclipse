package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.bean.Order;

public class AddUserOrderBuilder extends AbstractJSONBuilder<Order> {

	@Override
	protected Order builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		Order order = null;
		if(jsonObject.has("msg")){
			JSONObject jObj = jsonObject.getJSONObject("msg");
			order = new Order();
			if(jObj.has("data_list")){
				/**data_list*/
				JSONObject jDataListObj = jObj.getJSONObject("data_list");
				Order.DataList orderDataList = new Order.DataList();
				if(jDataListObj.has("id"))
					orderDataList.setId(jDataListObj.getString("id"));
				if(jDataListObj.has("order_amount"))
					orderDataList.setOrderAmount(jDataListObj.getString("order_amount"));
				if(jDataListObj.has("username"))
					orderDataList.setUsername(jDataListObj.getString("username"));
				if(jDataListObj.has("status"))
					orderDataList.setStatus(jDataListObj.getString("status"));
				if(jDataListObj.has("order_num"))
					orderDataList.setOrderNum(jDataListObj.getString("order_num"));
				if(jDataListObj.has("goods_id"))
					orderDataList.setGoodsId(jDataListObj.getString("goods_id"));
				if(jDataListObj.has("createdate"))
					orderDataList.setCreatedate(jDataListObj.getString("createdate"));
				if(jDataListObj.has("goods_name"))
					orderDataList.setGoodsName(jDataListObj.getString("goods_name"));
				if(jDataListObj.has("hosp_name"))
					orderDataList.setHospName(jDataListObj.getString("hosp_name"));
				if(jDataListObj.has("goods_img"))
					orderDataList.setGoodsImg(jDataListObj.getString("goods_img"));
				if(jDataListObj.has("city"))
					orderDataList.setCity(jDataListObj.getString("city"));
				order.setDataList(orderDataList);
			}
			/**data_list*/
			if(jObj.has("data_detail")){
				JSONObject jdataDetailObj = jObj.getJSONObject("data_detail");
				Order.DataDetail orderDataDetail = new Order.DataDetail();
				if(jdataDetailObj.has("goods_info")){
					Order.DataDetail.GoodsInfo goodsInfo = new Order.DataDetail.GoodsInfo();
					JSONObject jGoodsInfoObj = jdataDetailObj.getJSONObject("goods_info");
					if(jGoodsInfoObj.has("order_amount"))
						goodsInfo.setGoodsImg(jGoodsInfoObj.getString("order_amount"));
					if(jGoodsInfoObj.has("goods_name"))
						goodsInfo.setGoodsName(jGoodsInfoObj.getString("goods_name"));
					if(jGoodsInfoObj.has("goods_img"))
						goodsInfo.setGoodsImg(jGoodsInfoObj.getString("goods_img"));
					orderDataDetail.setGoodsInfo(goodsInfo);
				}
				if(jdataDetailObj.has("hosp_info")){
					Order.DataDetail.HospInfo  hospInfo = new Order.DataDetail.HospInfo();
					JSONObject jHospInfoObj = jdataDetailObj.getJSONObject("hosp_info");
					if(jHospInfoObj.has("hosp_name"))
						hospInfo.setHospName(jHospInfoObj.getString("hosp_name"));
					if(jHospInfoObj.has("addr"))
						hospInfo.setAddr(jHospInfoObj.getString("addr"));
					if(jHospInfoObj.has("tel"))
						hospInfo.setTel(jHospInfoObj.getString("tel"));
					orderDataDetail.setHospInfo(hospInfo);
				}
				if(jdataDetailObj.has("order_info")){
					JSONArray jOrderInfoArray = jdataDetailObj.getJSONArray("order_info");
					List<Order.DataDetail.OrderInfo> orderInfos = new ArrayList<Order.DataDetail.OrderInfo>();
					for(int j= 0;j<jOrderInfoArray.length();j++){
						Order.DataDetail.OrderInfo info = new Order.DataDetail.OrderInfo();
						JSONObject jOrderInfo = jOrderInfoArray.getJSONObject(j);
						if(jOrderInfo.has("value"))
							info.setValue(jOrderInfo.getString("value"));
						if(jOrderInfo.has("title"))
							info.setTitle(jOrderInfo.getString("title"));
						orderInfos.add(info);
					}
					orderDataDetail.setOrderInfos(orderInfos);
				}
				order.setDataDetail(orderDataDetail);
			}
			android.util.Log.d("order", "order = " + order);
		}
		return order;
	}

	/*
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
			JSONObject jObj = jsonObject1.getJSONObject("userinfo");
			Order2.UserInfo userInfo = new Order2.UserInfo();
			if(jObj.has("mobile"))
				userInfo.setMobile(jObj.getString("mobile"));
			if(jObj.has("passport"))
				userInfo.setPassport(jObj.getString("passport"));
			order.setUserInfo(userInfo);
		}
		return order;
	}*/
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
