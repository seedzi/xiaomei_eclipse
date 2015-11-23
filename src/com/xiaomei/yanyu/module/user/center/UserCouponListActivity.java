package com.xiaomei.yanyu.module.user.center;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.adapter.CouponAdapter;
import com.xiaomei.yanyu.bean.Coupon;
import com.xiaomei.yanyu.widget.TitleActionBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class UserCouponListActivity extends Activity implements OnClickListener {

    public static void startActivity(Activity ac) {
        Intent intent = new Intent(ac, UserCouponListActivity.class);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_in_from_right,
                R.anim.activity_slid_out_no_change);
    }

    private CouponAdapter mAdapter;

    private TitleActionBar mTitleBar;

    private PullToRefreshListView mPullView;

    private View mCheckLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_coupon_list);

        mTitleBar = new TitleActionBar(getActionBar());
        mTitleBar.setTitle(R.string.user_coupon);
        mTitleBar.setTextAction(R.string.check_coupon);
        mTitleBar.setOnActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleBar.setActionVisibility(View.GONE);
                mCheckLayout.setVisibility(View.VISIBLE);
            }
        });

        mPullView = (PullToRefreshListView)findViewById(R.id.list);
        ListView listView = mPullView.getRefreshableView();
        mAdapter = new CouponAdapter(this);
        listView.setAdapter(mAdapter);
        View emptyView = findViewById(R.id.empty);
        listView.setEmptyView(emptyView);

        mCheckLayout = findViewById(R.id.check_coupon_layout);
        View checkButton = mCheckLayout.findViewById(R.id.check_coupon);
        checkButton.setOnClickListener(this);

        mAdapter.add(new Coupon());
        mAdapter.add(new Coupon());
        mAdapter.add(new Coupon());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_coupon:
                mTitleBar.setActionVisibility(View.VISIBLE);
                mCheckLayout.setVisibility(View.GONE);
                // TODO 兑换优惠券
                break;
        }
    }
}
