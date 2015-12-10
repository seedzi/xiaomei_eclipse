package com.xiaomei.yanyu.activity;

import com.viewpagerindicator.PageIndicator;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.adapter.CouponAdapter;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OrderCouponActivity extends Activity {

    public static void startActivity(Activity ac, String couponId) {
        ac.startActivity(new Intent(ac, OrderCouponActivity.class).putExtra("couponId", couponId));
        ac.overridePendingTransition(R.anim.activity_slid_out_no_change,
                R.anim.activity_slid_in_from_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coupon);

        TitleActionBar titleBar = new TitleActionBar(getActionBar());
        titleBar.setTitle(R.string.activity_order_coupon);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new CouponPagerAdapter(getFragmentManager()));
        PageIndicator indicator = (PageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
    }

    private class CouponPagerAdapter extends FragmentPagerAdapter {

        public CouponPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getString(R.string.coupon) : getString(R.string.coupon_code);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new CouponFragment() : new CouponCodeFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    /**
     * @author sunbreak 优惠券页面
     */
    private class CouponFragment extends Fragment {

        private CouponAdapter mCouponAdapter;
        private ListView mListView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mCouponAdapter = new CouponAdapter(getActivity());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.list, container, false);
            mListView = (ListView)root.findViewById(android.R.id.list);
            mListView.setAdapter(mCouponAdapter);
            return root;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mCouponAdapter.add(new Coupon());
            mCouponAdapter.add(new Coupon());
            mCouponAdapter.add(new Coupon());
        }
    }

    /**
     * @author sunbreak 优惠码页面
     */
    private class CouponCodeFragment extends Fragment implements TextWatcher, OnClickListener {

        private TextView mInput;
        private Button mConfirm;
        private TextView mInfo;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.order_coupon_code, container, false);
            mInput = (TextView)root.findViewById(R.id.input);
            mConfirm = (Button)root.findViewById(R.id.confirm);
            mInfo = (TextView)root.findViewById(R.id.info);

            mInput.addTextChangedListener(this);
            mConfirm.setEnabled(!TextUtils.isEmpty(mInput.getText()));
            mConfirm.setOnClickListener(this);
            return root;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.confirm:
                    // TODO 检查优惠码可用
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mConfirm.setEnabled(!TextUtils.isEmpty(s));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
