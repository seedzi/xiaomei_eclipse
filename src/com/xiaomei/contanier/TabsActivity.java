package com.xiaomei.contanier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;

/**
 * Created by huzhi on 15-2-17.
 */
public class TabsActivity extends  BaseActiviy{


    public static void startActivity(Context context){
        Intent intent = new Intent(context,TabsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_layout);
    }

}
