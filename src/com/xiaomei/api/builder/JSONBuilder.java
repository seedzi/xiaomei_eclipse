package com.xiaomei.api.builder;
import com.xiaomei.api.exception.XiaoMeiJSONException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public interface JSONBuilder<T> {
	public abstract T build(String json) throws XiaoMeiJSONException;

	public abstract T build(JSONObject jsonObject) throws XiaoMeiJSONException;

    public abstract void buildList(String json, Collection<T> collection) throws XiaoMeiJSONException;

}