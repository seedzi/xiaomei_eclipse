package com.xiaomei.yanyu.module.user.center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.widget.TitleBar;

public class UserMessageActivity extends AbstractActivity implements View.OnClickListener{

    public static void startActivity(Activity ac){
        Intent intent = new Intent(ac,UserMessageActivity.class);
        ac.startActivity(intent);
         ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
    }
    
    private TitleBar mTitleBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message_layout);
        setUpView();
    }
    
    private void setUpView(){
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.setTitle(getResources().getString(R.string.user_message));
        mTitleBar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        findViewById(R.id.message).setOnClickListener(this);
        findViewById(R.id.shopping).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        case R.id.message:
            MessageActivity.startActivity(this);
            break;
        case R.id.shopping:
            SalesMessageActivity.startActivity(this);
            break;
        default:
            break;
        }
    }
}
