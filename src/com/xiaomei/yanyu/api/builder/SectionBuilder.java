package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.bean.Section;
import com.xiaomei.yanyu.bean.Section.Entity;

public class SectionBuilder extends AbstractJSONBuilder<List<Section>> {

	@Override
	protected List<Section> builder(JSONObject jsonObject) throws JSONException {
	    if(DebugRelease.isDebug)
	        Log.d("json", jsonObject.toString());
		jsonObject = jsonObject.getJSONObject("msg");
		List<Section> list = new ArrayList<Section>();
		JSONArray jsonArray = jsonObject.getJSONArray("sections");
		for( int i=0 ;i< jsonArray.length();i++){
			JSONObject jobject = jsonArray.getJSONObject(i);
			if(jobject.getString("key").equals("nav")){ //如果是导航
				Section section = new Section();
				section.setKey(jobject.getString("key"));
				section.setOrdering(jobject.getInt("ordering"));
				section.setTitle(jobject.getString("title"));
				if(jobject.has("viewcount"))
					section.setViewCount(jobject.getString("viewcount"));
				JSONArray jA  =  jobject.getJSONArray("data");
				List<Entity> entityList = new ArrayList<Section.Entity>();
				for( int j=0 ;j< jA.length();j++){
					JSONObject jO =  jA.getJSONObject(j);
					try {
						Entity entity = new EntityBuilder().build(jO);
						entityList.add(entity);
					} catch (XiaoMeiJSONException e) {
						throw new JSONException(e.getMessage());
					}
				}
				section.setList(entityList);
				list.add(section);
			} else{ 
				JSONArray jA  =  jobject.getJSONArray("data");
				for( int j=0 ;j< jA.length();j++){
					Section section = new Section();
					section.setKey(jobject.getString("key"));
					section.setOrdering(jobject.getInt("ordering"));
					section.setTitle(jobject.getString("title"));
					if(jobject.has("viewcount"))
						section.setViewCount(jobject.getString("viewcount"));
					if(jobject.has("title"))
						section.setTitle(jobject.getString("title"));
					if(jobject.has("des"))
						section.setDes(jobject.getString("des"));
					List<Entity> entityList = new ArrayList<Section.Entity>();
					JSONObject jO =  jA.getJSONObject(j);
					if(jO.has("type"))
						section.setType(jO.getString("type"));
					if(jO.has("list"))
						section.setList_String(jO.getString("list"));
					try {
						Entity entity = new EntityBuilder().build(jO);
						entityList.add(entity);
						section.setList(entityList);
						list.add(section);
					} catch (XiaoMeiJSONException e) {
						throw new JSONException(e.getMessage());
					}
				}
			}
		}
		return list;
	}

}
