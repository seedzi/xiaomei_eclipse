package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.bean.Order;

public class ListOrderBuilder extends AbstractJSONBuilder<List<Order>> {
	/*
	{
	    "data_detail": {
	        "goods_info": {
	            "order_amount": "6000",
	            "goods_name": "纤纤玉腿的秘密-美国进口Botox",
	            "goods_img": "http://bcs.duapp.com/drxiaomei/images/goods_common/20150409103832_25154.jpg"
	        },
	        "order_info": [
	            {
	                "value": "201504111313185450",
	                "title": "订单号"
	            },
	            {
	                "value": "1428729198",
	                "title": "下单日期"
	            },
	            {
	                "value": "两个月内均可体验",
	                "title": "服务日期"
	            },
	            {
	                "value": "未定义",
	                "title": "客户姓名"
	            },
	            {
	                "value": "15010768102",
	                "title": "客户电话"
	            },
	            {
	                "value": "123",
	                "title": "护照号"
	            }
	        ],
	        "hosp_info": {
	            "hosp_name": "德碧亶the Bidan整形外科",
	            "addr": "韩国首尔市江南区新沙洞642-26",
	            "tel": "+82-2-543-4364"
	        }
	    },
	    "data_list": {
	        "id": "19062291",
	        "order_amount": "6000",
	        "username": "未定义",
	        "status": "1",
	        "order_num": "201504111313185450",
	        "goods_id": "1015",
	        "createdate": "1428729198",
	        "goods_name": "纤纤玉腿的秘密-美国进口Botox",
	        "hosp_name": "德碧亶the Bidan整形外科",
	        "goods_img": "http://bcs.duapp.com/drxiaomei/images/goods_common/20150409103832_25154.jpg",
	        "city": "首尔"
	    }
	}
	*/
	@Override
	protected List<Order> builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		List<Order> list = new ArrayList<Order>();
		if(jsonObject.has("msg")){
			JSONArray jsonArray = jsonObject.getJSONArray("msg");
			for(int i=0 ;i<jsonArray.length();i++){
				JSONObject jObj = jsonArray.getJSONObject(i);
				Order order = new Order();
				if(jObj.has("data_list")){
					/**data_list*/
					JSONObject jDataListObj = jObj.getJSONObject("data_list");
					Order.DataList orderDataList = new Order.DataList();
					if(jDataListObj.has("id"))
						orderDataList.setId(jDataListObj.getString("id"));
					if(jDataListObj.has("order_amount"))
						orderDataList.setOrderAmount(jDataListObj.getString("order_amount"));
					if(jDataListObj.has("goods_pay"))
						orderDataList.setGoodsPay(jDataListObj.getString("goods_pay"));
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
				list.add(order);
			}
		}
		return list;
	}
	/*
	04-11 14:11:03.015: D/json(19758): {"msg":[{"modifydate":"1428729198","goods_pay":"6000","status":"1","userid":"19062271","createdate":"1428729198","goods_name":"纤纤玉腿的秘密-美国进口Botox","paydate":"0","userinfo":"{\"mobile\":\"15010768102\",\"passport\":\"123\"}","order_id":"19062291","goods_img":"http:\/\/bcs.duapp.com\/drxiaomei\/images\/goods_common\/20150409103832_25154.jpg","goods_amount":"6000","id":"19062291","order_amount":"6000","username":"未定义","goods_price":"6000","goods_status":"0","pay_type":"0","order_num":"201504111313185450","goods_id":"1015","goods_code":"1811"}],"code":0}
*/
}
