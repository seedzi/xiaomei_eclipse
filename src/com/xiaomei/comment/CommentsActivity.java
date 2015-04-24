package com.xiaomei.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseActivity;

public class CommentsActivity extends BaseActivity {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,CommentsActivity.class);
        context.startActivity(intent);
    }
    
    private TitleBar mTitleBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_layout);
        setUpViews();
    }
    
    
    private void setUpViews(){
        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.setTitle("详情");
        mTitleBar.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
