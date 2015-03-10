package com.xiaomei.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.api.exception.XiaoMeiJSONException;
import com.xiaomei.bean.Section;
import com.xiaomei.bean.Section.Entity;

public class SectionBuilder extends AbstractJSONBuilder<List<Section>> {

	@Override
	protected List<Section> builder(JSONObject jsonObject) throws JSONException {
		Log.d("json", jsonObject.toString());
		List<Section> list = new ArrayList<Section>();
		JSONArray jsonArray = jsonObject.getJSONArray("sections");
		for( int i=0 ;i< jsonArray.length();i++){
			JSONObject jobject = jsonArray.getJSONObject(i);
			Section section = new Section();
			section.setKey(jobject.getString("key"));
			section.setOrdering(jobject.getInt("ordering"));
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
		}
		return list;
	}

}
