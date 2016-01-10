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
        Context context = getContext();
        View itemView = convertView != null ? convertView
                : LayoutInflater.from(context).inflate(getItemViewLayout(), parent, false);
        Coupon item = getItem(position);
        boolean enabled = item.status == Coupon.STATUS_ENABLED;
        UiUtil.setViewEnabled(itemView, enabled);
        UiUtil.findTextViewById(itemView, R.id.coupon_display).setText(item.display);
        UiUtil.findTextViewById(itemView, R.id.coupon_life)
                .setText(context.getString(R.string.coupon_life, item.beg, item.expire));
        UiUtil.findTextViewById(itemView, R.id.coupon_money).setText(item.discount);
        UiUtil.findTextViewById(itemView, R.id.coupon_term)
                .setText(context.getString(R.string.coupon_term, item.base, item.discount));
        UiUtil.findTextViewById(itemView, R.id.coupon_status).setText(item.getStatusDisplayRes());
        return itemView;
    }

    protected int getItemViewLayout() {
        return R.layout.coupon_list_item;
    }
}
