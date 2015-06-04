package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.Pair;

import com.xiaomei.yanyu.DebugRelease;
import com.xiaomei.yanyu.bean.Goods;
import com.xiaomei.yanyu.bean.GoodsOption;

public class GoodsOptionBuilder extends AbstractJSONBuilder<List<GoodsOption>> {
    
    private static Comparator<Pair<String, String>> sComparator = new Comparator<Pair<String, String>>() {

        @Override
        public int compare(Pair<String, String> lhs, Pair<String, String> rhs) {
            return lhs.second.compareTo(rhs.second);
        }
        
    };

	@Override
	protected List<GoodsOption> builder(JSONObject jsonObject) throws JSONException {
		List<GoodsOption> list = new ArrayList<GoodsOption>();
		if(DebugRelease.isDebug)
		    Log.d("json", jsonObject.toString());
		jsonObject = jsonObject.getJSONObject("msg");
		
		Iterator<String> iterator = jsonObject.keys();
		while(iterator.hasNext()) {
		    String type = iterator.next();
		    JSONObject content = jsonObject.getJSONObject(type);
		    
		    List<Pair<String, String>> items = new ArrayList<Pair<String, String>>();
		    JSONArray array = content.getJSONArray("key");
		    for(int i = 0; i < array.length(); i++) {
		        JSONObject json = array.getJSONObject(i);
                items.add(new Pair(json.getString("label"), json.getString("key")));
		    }
//	        Collections.sort(items, sComparator);
	        GoodsOption option = new GoodsOption();
	        option.setName(type);
	        option.setItems(items);
	        list.add(option);
		}
		return list;
	}
}
