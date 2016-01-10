package com.xiaomei.yanyu.adapter;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;

public class ActionCouponAdapter extends CouponAdapter {

    public ActionCouponAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        UiUtil.findById(itemView, R.id.coupon_item_layout)
                .setBackgroundResource(R.drawable.action_coupon_list_item_bg);
        ColorStateList actionTextColor = getContext().getResources()
                .getColorStateList(R.color.action_coupon_item_text);
        UiUtil.findTextViewById(itemView, R.id.coupon_display).setTextColor(actionTextColor);
        UiUtil.findTextViewById(itemView, R.id.coupon_life).setTextColor(actionTextColor);
        UiUtil.findTextViewById(itemView, R.id.coupon_money).setTextColor(actionTextColor);
        UiUtil.findTextViewById(itemView, R.id.ic_concurrency).setTextColor(actionTextColor);
        UiUtil.findTextViewById(itemView, R.id.coupon_status).setVisibility(View.GONE);
        return itemView;
    }

    @Override
    protected int getItemViewLayout() {
        return R.layout.action_coupon_list_item;
    }
}
