package com.xiaomei.yanyu.view;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Area;
import com.xiaomei.yanyu.util.UiUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FilterAdapter extends ArrayAdapter<Area.FilterItem> {

    public FilterAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.top_filter_drop_item, parent, false);
        UiUtil.findTextViewById(itemView, android.R.id.text1).setText(getItem(position).getLabel());
        return itemView;
    }
}
