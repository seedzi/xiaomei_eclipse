package com.xiaomei.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.contanier.TabsActivity;
import com.xiaomei.launch.control.LaunchControl;

import java.util.logging.LogRecord;

public class LaunchActivity extends BaseActiviy<LaunchControl> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TabsActivity.class);
                startActivity(intent);
            }
        },1000);
    }

}
