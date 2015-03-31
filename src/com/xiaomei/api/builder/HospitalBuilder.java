package com.xiaomei.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.bean.Hospital;

public class HospitalBuilder extends AbstractJSONBuilder<List<Hospital>>{

	@Override
	protected List<Hospital> builder(JSONObject jsonObject)
			throws JSONException {
		Log.d("json", jsonObject.toString());
		List<Hospital> list = new ArrayList<Hospital>();
		JSONArray jsonArray = jsonObject.getJSONArray("hospitals");
		for( int i=0 ;i< jsonArray.length();i++){
			Hospital hospital = new Hospital();
			if(jsonObject.has("id"))
				hospital.setId(jsonObject.getString("id"));
			if(jsonObject.has("parent_id"))
				hospital.setParentId(jsonObject.getString("parent_id"));		
			if(jsonObject.has("hosp_name"))
				hospital.setHospName(jsonObject.getString("hosp_name"));		
			if(jsonObject.has("hosp_name_en"))
				hospital.setHospNameEn(jsonObject.getString("hosp_name_en"));		
			if(jsonObject.has("hosp_type"))
				hospital.setHospType(jsonObject.getString("hosp_type"));		
			if(jsonObject.has("keywords"))
				hospital.setKeywords(jsonObject.getString("keywords"));		
			if(jsonObject.has("hosp_des"))
				hospital.setHospDes(jsonObject.getString("hosp_des"));		
			if(jsonObject.has("sort_order"))
				hospital.setSortOrder(jsonObject.getString("sort_order"));		
			if(jsonObject.has("hosp_mark"))
				hospital.setHospMark(jsonObject.getString("hosp_mark"));		
			if(jsonObject.has("file"))
				hospital.setFile(jsonObject.getString("file"));		
			if(jsonObject.has("link_url"))
				hospital.setLinkUrl(jsonObject.getString("link_url"));		
			if(jsonObject.has("open_time"))
				hospital.setOpenTime(jsonObject.getString("open_time"));		
			if(jsonObject.has("addr"))
				hospital.setAddr(jsonObject.getString("addr"));		
			if(jsonObject.has("tel"))
				hospital.setTel(jsonObject.getString("tel"));		
			if(jsonObject.has("city"))
				hospital.setCity(jsonObject.getString("city"));		
			if(jsonObject.has("area"))
				hospital.setArea(jsonObject.getString("area"));		
			if(jsonObject.has("services"))
				hospital.setServices(jsonObject.getString("services"));		
			if(jsonObject.has("surrounding"))
				hospital.setSurrounding(jsonObject.getString("surrounding"));		
			if(jsonObject.has("payments"))
				hospital.setPayments(jsonObject.getString("payments"));		
			list.add(hospital);
		}
		return list;
	}
	
	/*
	"id": "16",
    "parent_id": "0",
    "hosp_name": "韩国科莱秀(KLASSE)整形外科医院 ",
    "hosp_name_en": "k231",
    "hosp_type": "3",
    "keywords": "",
    "hosp_des": "科莱秀整形医院是韩国最高学府首尔大学医科大学和韩国医院三星首尔医院是同门医疗机构，代表院长金政敏是首尔大学医科专业毕业，同时拥有最具权威的医疗团队组成的。医院以正直和信赖为运营主旨，为患者提供1对1的专业商谈，让手术结果达到患者满意的程度。本院开设有面部轮廓、唇部/人中、干细胞隆胸、臂部、提升等多种手术项目，我们有足够的经验，可以向患者保证最好最满意的礼物。科莱秀整形医院从手术前检查，手术，术后管理一切以顾客为中心。提供便利，最满意的一条龙服务而努力。同时，科莱秀整形医院精确分析患者的状态，进行安全正确",
    "sort_order": "0",
    "hosp_mark": "",
    "file": "http://bcs.duapp.com/drxiaomei/images/jg16/20150305140549_86663.jpg",
    "link_url": "www.yk5151.com (韩文) / http://www.yk5151.net (中文)",
    "open_time": "周一至周六 10:00AM - 5:00PM",
    "addr": "10 Da-dong, Jung-gu, Seoul, 韩国",
    "tel": "82-2-542-5151",
    "city": "3",
    "area": "2",
    "services": "[\"1\",\"2\",\"3\",\"4\",\"5\"]",
    "surrounding": "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\"]",
    "payments": "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\"]"*/

}
