package com.xiaomei.yanyu.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Pair;

public class GoodsOption {

    private String type;
    
    private List<Pair<String, String>> items;

    public String getType() {
        return type;
    }

    public void setName(String type) {
        this.type = type;
    }

    public List<Pair<String,String>> getItems() {
        return items;
    }

    public void setItems(List<Pair<String, String>> items) {
        this.items = items;
    }

    public List<String> getItemKeys() {
        ArrayList<String> keys = new ArrayList<String>();
        for(int i = 0; i < items.size(); i++) {
            keys.add(items.get(i).first);
        }
        return keys;
    }
}
