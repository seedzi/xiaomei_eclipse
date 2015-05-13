package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.Hospital;

public class HospitalBuilder extends AbstractJSONBuilder<List<Hospital>>{

	/*	
	{
	    "msg": {
	        "hospitals": [
	            {
	                "rate_avg": "98.5",
	                "tel": "+82-02-562-5800",
	                "addr": "韩国首尔江南区驿三洞822 －1江南A座9层",
	                "hosp_name": "COOKI整形外科",
	                "city": "3",
	                "rate_environment": "4",
	                "open_time": "周一到周四 10:00－19:00 周五 10:00－21:00 周六 10:00－16:00",
	                "id": "3",
	                "surrounding": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "area": "1",
	                "file": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/hosp3\/20150413095720_64494.jpg",
	                "hosp_type": "1",
	                "parent_id": "0",
	                "services": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "keywords": "0",
	                "hosp_des": "专业定制你的美",
	                "link_url": "www.cookips.co.kr",
	                "hosp_name_en": "jg1",
	                "rate_effect": "5",
	                "watermark": "0",
	                "payments": "[\"1\",\"2\",\"3\"]",
*/
	
	@Override
	protected List<Hospital> builder(JSONObject jsonObject)
			throws JSONException {
	    if(DebugRelease.isDebug)
	        Log.d("json", jsonObject.toString());
		List<Hospital> list = new ArrayList<Hospital>();
		JSONArray jsonArray = null;
		if(jsonObject.has("msg"))
			jsonObject = jsonObject.getJSONObject("msg");
		if(jsonObject.has("hospitals"))
			jsonArray = jsonObject.getJSONArray("hospitals");
		else
			return null;
		for( int i=0 ;i< jsonArray.length();i++){
			Hospital hospital = new Hospital();
			JSONObject jO = jsonArray.getJSONObject(i);
			if(jO.has("rate_avg"))
				hospital.setRateAvg(jO.getString("rate_avg"));
			if(jO.has("tel"))
				hospital.setTel(jO.getString("tel"));		
			if(jO.has("addr"))
				hospital.setAddr(jO.getString("addr"));		
			if(jO.has("hosp_name"))
				hospital.setHospName(jO.getString("hosp_name"));		
			if(jO.has("city"))
				hospital.setCity(jO.getString("city"));		
			if(jO.has("rate_environment"))
				hospital.setRateEnvironment(jO.getString("rate_environment"));		
			if(jO.has("open_time"))
				hospital.setOpenTime(jO.getString("open_time"));		
			if(jO.has("id"))
				hospital.setId(jO.getString("id"));		
			if(jO.has("surrounding"))
				hospital.setSurrounding(jO.getString("surrounding"));		
			if(jO.has("area"))
				hospital.setArea(jO.getString("area"));		
			if(jO.has("image_6"))
				hospital.setFile(jO.getString("image_6"));		
			if(jO.has("hosp_type"))
				hospital.setHospType(jO.getString("hosp_type"));		
			if(jO.has("parent_id"))
				hospital.setParentId(jO.getString("parent_id"));		
			if(jO.has("services"))
				hospital.setServices(jO.getString("services"));		
			if(jO.has("keywords"))
				hospital.setKeywords(jO.getString("keywords"));		
			if(jO.has("hosp_des"))
				hospital.setHospDes(jO.getString("hosp_des"));		
			if(jO.has("link_url"))
				hospital.setLinkUrl(jO.getString("link_url"));		
			if(jO.has("hosp_name_en"))
				hospital.setHospNameEn(jO.getString("hosp_name_en"));		
			if(jO.has("rate_effect"))
				hospital.setRateEffect(jO.getString("rate_effect"));		
			if(jO.has("watermark"))
				hospital.setWatermark(jO.getString("watermark"));		
			if(jO.has("payments"))
				hospital.setPayments(jO.getString("payments"));		
			if(jO.has("rate_service"))
				hospital.setRateService(jO.getString("rate_service"));		
			list.add(hospital);
		}
		return list;
	}
	
	/*	
	{
	    "msg": {
	        "hospitals": [
	            {
	                "rate_avg": "98.5",
	                "docs": "[{\"id\":\"10\",\"hosp_id\":\"3\",\"name\":\"\\u90d1\\u6210\\u6a21\",\"icon\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160412_83193.jpg\",\"job\":\"1\",\"job_type\":\"1\",\"work_time\":\"0\",\"edu\":\"0\",\"work_start\":\"\",\"good_at\":\"\\u9762\\u90e8\\u8f6e\\u5ed3 \\u80f8\\u90e8\\\/\\u4f53\\u578b\\u6574\\u5f62 \\u7ae5\\u989c\\u63d0\\u5347\",\"work_des\":\"\\u6574\\u5f62\\u5916\\u79d1\\u5b66\\u6388\\u4e88\\u535a\\u58eb\\u5b66\\u4f4d \\n\\u5927\\u97e9\\u6574\\u5f62\\u5916\\u79d1\\u5b66\\u4f1a\\u6b63\\u4f1a\\u5458 \\n\\u5927\\u97e9\\u7f8e\\u5bb9\\u6574\\u5f62\\u5916\\u79d1\\u5b66\\u4f1a\\u6b63\\u4f1a\\u5458 \"},{\"id\":\"11\",\"hosp_id\":\"3\",\"name\":\"\\u6734\\u5584\\u955c\",\"icon\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160521_49857.jpg\",\"job\":\"2\",\"job_type\":\"1\",\"work_time\":\"0\",\"edu\":\"0\",\"work_start\":\"\",\"good_at\":\"\\u624b\\u672f\\u9ebb\\u9189\",\"work_des\":\"\\u4e9a\\u6d32\\u5927\\u5b66\\u533b\\u79d1\\u5927\\u5b66\\u9662\\u7855\\u58eb \\n\\u5927\\u97e9\\u9ebb\\u9189\\u548c\\u75bc\\u75db\\u533b\\u5b66\\u4f1a\\u4f1a\\u5458 \"}]",
	                "tel": "+82-02-562-5800",
	                "addr": "韩国首尔江南区驿三洞822 －1江南A座9层",
	                "hosp_name": "COOKI整形外科",
	                "city": "3",
	                "rate_environment": "4",
	                "open_time": "周一到周四 10:00－19:00 周五 10:00－21:00 周六 10:00－16:00",
	                "id": "3",
	                "surrounding": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "area": "1",
	                "file": "http:\/\/bcs.duapp.com\/drxiaomei\/images\/hosp3\/20150413095720_64494.jpg",
	                "hosp_type": "1",
	                "parent_id": "0",
	                "services": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
	                "keywords": "0",
	                "hosp_des": "专业定制你的美",
	                "link_url": "www.cookips.co.kr",
	                "hosp_name_en": "jg1",
	                "rate_effect": "5",
	                "watermark": "0",
	                "payments": "[\"1\",\"2\",\"3\"]",
	                "images": "[{\"id\":\"46\",\"type\":\"hospital\",\"typeid\":\"3\",\"url\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160022_75514.jpg\",\"title\":\"COOKI\\u7ed9\\u6211\\u4eec\\u7559\\u4e0b\\u6df1\\u523b\\u5370\\u8c61: \\u6211\\u4eec\\u89c1\\u5230\\u7684\\u6bcf\\u5bb6\\u533b\\u9662\\u90fd\\u6709\\u5927\\u725b\\u6574\\u5f62\\u533b\\u751f\\uff0c\\u4f46\\u5e76\\u4e0d\\u662f\\u6bcf\\u4e00\\u5bb6\\u90fd\\u50cfCOOKI\\u4e00\\u6837\\u6709\\u5927\\u725b\\u9ebb\\u9189\\u533b\\u751f\\u3002\",\"link\":null},{\"id\":\"47\",\"type\":\"hospital\",\"typeid\":\"3\",\"url\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160202_37139.jpg\",\"title\":\"\\u53ef\\u89c1\\u8fd9\\u5bb6\\u533b\\u9662\\u5bf9\\u4e13\\u4e1a\\u7ba1\\u7406\\u7684\\u6267\\u7740\\u3002\\u4e0d\\u4ec5\\u4ece\\u5ba1\\u7f8e\\u89d2\\u5ea6\\u8ffd\\u6c42\\u5b8c\\u7f8e\\u6cbb\\u7597\\uff0c\\u8fd8\\u8981\\u5728\\u5404\\u4e2a\\u73af\\u8282\\u8ba9\\u987e\\u5ba2\\u5f97\\u5230\\u6700\\u597d\\u7684\\u670d\\u52a1\\u3002\",\"link\":null},{\"id\":\"48\",\"type\":\"hospital\",\"typeid\":\"3\",\"url\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160215_14701.jpg\",\"title\":\"COOKI\\u4e5f\\u662f\\u4e00\\u5bb6\\u5145\\u6ee1\\u6b63\\u80fd\\u91cf\\u7684\\u533b\\u9662\\uff0c\\u4ed6\\u4eec\\u5f3a\\u8c03\\uff0c\\u6574\\u5f62\\u5176\\u5b9e\\u662f\\u5bfb\\u627e\\u5185\\u5fc3\\u7684\\u89e3\\u7b54\\uff0c\\u901a\\u8fc7\\u627e\\u5230\\u81ea\\u4fe1\\u8ba9\\u81ea\\u5df1\\u5145\\u6ee1\\u9b45\\u529b\\u3002\",\"link\":null},{\"id\":\"49\",\"type\":\"hospital\",\"typeid\":\"3\",\"url\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160236_83813.jpg\",\"title\":\"\\u5728\\u5982\\u6b64\\u6e29\\u99a8\\u8212\\u9002\\u7684\\u533b\\u9662\\u73af\\u5883\\u548c\\u4eb2\\u5207\\u6d3b\\u6cfc\\u7684\\u533b\\u7597\\u56e2\\u961f\\uff0c\\u4e5f\\u8bb8\\u771f\\u7684\\u80fd\\u591f\\u627e\\u5230\\u6700\\u597d\\u7684\\u81ea\\u5df1\\uff0c\\u5145\\u6ee1\\u9633\\u5149\\u4e0e\\u81ea\\u4fe1\\u3002\",\"link\":null},{\"id\":\"50\",\"type\":\"hospital\",\"typeid\":\"3\",\"url\":\"http:\\\/\\\/bcs.duapp.com\\\/drxiaomei\\\/images\\\/hosp3\\\/20150413160314_85535.jpg\",\"title\":\"COOKI\\u6574\\u5f62\\u5916\\u79d1\\u4e00\\
*/

}
