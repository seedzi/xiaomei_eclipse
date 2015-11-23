package com.xiaomei.yanyu.adapter;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.util.UiUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CouponAdapter extends ArrayAdapter<Coupon> {

    public CouponAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView != null ? convertView
                : LayoutInflater.from(getContext()).inflate(R.layout.coupon_list_item, parent, false);
        Coupon item = getItem(position);
        UiUtil.findTextViewById(itemView, R.id.coupon_life).setText(item.life);
        UiUtil.findTextViewById(itemView, R.id.coupon_money).setText(item.money);
        UiUtil.findTextViewById(itemView, R.id.coupon_term).setText(item.term);
        return itemView;
    }

}
