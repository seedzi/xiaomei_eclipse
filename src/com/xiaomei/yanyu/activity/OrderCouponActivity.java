package com.xiaomei.yanyu.activity;

import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.adapter.CouponAdapter;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.view.LayoutPagerAdapter;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrderCouponActivity extends Activity {

    public static void startActivity(Activity ac, String couponId) {
        ac.startActivity(new Intent(ac, OrderCouponActivity.class).putExtra("couponId", couponId));
        ac.overridePendingTransition(R.anim.activity_slid_out_no_change,
                R.anim.activity_slid_in_from_right);
    }

    public CouponAdapter mCouponAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coupon);

        mCouponAdapter = new CouponAdapter(this);
        mCouponAdapter.add(new Coupon());
        mCouponAdapter.add(new Coupon());
        mCouponAdapter.add(new Coupon());

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_order_coupon);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CouponPagerAdapter());
        PageIndicator indicator = (PageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    private class CouponPagerAdapter extends LayoutPagerAdapter {

        public CouponPagerAdapter() {
            super(2);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();
            View itemView = LayoutInflater.from(context).inflate(R.layout.list,
                    container, false);
            ListView listView = (ListView)itemView;
            listView.setAdapter(position == 0 ? mCouponAdapter : null);
            container.addView(listView);
            return itemView;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getString(R.string.coupon) : getString(R.string.coupon_code);
        }
        
    }
}
