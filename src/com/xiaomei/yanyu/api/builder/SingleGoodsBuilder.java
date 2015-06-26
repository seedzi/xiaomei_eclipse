package com.xiaomei.yanyu.api.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xiaomei.yanyu.bean.Goods;

public class SingleGoodsBuilder extends AbstractJSONBuilder<Goods> {

    @Override
    protected Goods builder(JSONObject jobj) throws JSONException {
        Log.d("json", jobj.toString());
        Goods goods = new Goods();
        if(jobj.has("msg"))
            jobj = jobj.getJSONObject("msg");
        else
            return null;
        if(jobj.has("id"))
            goods.setId(jobj.getString("id"));
        if(jobj.has("hosp_id"))
            goods.setHospId(jobj.getString("hosp_id"));
        if(jobj.has("title"))
            goods.setTitle(jobj.getString("title"));
        if(jobj.has("des"))
            goods.setDes(jobj.getString("des"));
        if(jobj.has("price_market"))
            goods.setPriceMarket(jobj.getString("price_market"));
        if(jobj.has("price_xm"))
            goods.setPriceXm(jobj.getString("price_xm"));
        if(jobj.has("price_front"))
            goods.setPriceFront(jobj.getString("price_front"));
        if(jobj.has("is_front"))
            goods.setIsFront(jobj.getString("is_front"));
        if(jobj.has("file_url"))
            goods.setFileUrl(jobj.getString("file_url"));
        if(jobj.has("hosp_name"))
            goods.setHospName(jobj.getString("hosp_name"));
        if(jobj.has("sales"))
            goods.setSales(jobj.getString("sales"));
        if(jobj.has("city_name"))
            goods.setCityName(jobj.getString("city_name"));
        if(jobj.has("goods_mark")){
            List<Goods.Mark> marks = new ArrayList<Goods.Mark>();
            JSONArray array = jobj.getJSONArray("goods_mark");
            for(int j=0;j<array.length();j++){
                Goods.Mark mark = new Goods.Mark();
                JSONObject jo = array.getJSONObject(j);
                mark.setColor(jo.getString("color"));
                mark.setLabel(jo.getString("label"));
                marks.add(mark);
            }
            goods.setMarks(marks);
        }
        return goods;
    }

}
