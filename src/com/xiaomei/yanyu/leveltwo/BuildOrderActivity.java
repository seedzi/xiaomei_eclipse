package com.xiaomei.yanyu.leveltwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.TitleBar;

public class BuildOrderActivity extends AbstractActivity<UserCenterControl> {

    public static void startActivity(Activity ac,String goodsId){
        Intent intent = new Intent(ac,BuildOrderActivity.class);
        intent.putExtra("goods_id", goodsId);
        ac.startActivity(intent);
        ac.overridePendingTransition(R.anim.activity_slid_out_no_change, R.anim.activity_slid_in_from_right);
    }

    private String goodsId; //产品id
    private TitleBar mTitlebar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_order_layout);
        setupView();
        initData();
    }

    private void setupView(){
        mTitlebar = (TitleBar) findViewById(R.id.titlebar);
        mTitlebar.setTitle("生成订单");
        
        goodsId = getIntent().getStringExtra("goods_id");
    }
    
    private void initData(){
        mControl.getGoodsFromNetAsyn(goodsId);
    }

    // =========================================== CallBack =====================================================
    public void getGoodsFromNetAsynCallback(){
        
    }
    
    public void getGoodsFromNetAsynExceptionCallback(){
        
    }
}
