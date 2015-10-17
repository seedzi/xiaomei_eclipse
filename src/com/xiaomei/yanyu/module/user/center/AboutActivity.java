package com.xiaomei.yanyu.module.user.center;

import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.util.AppUtil;
import com.xiaomei.yanyu.widget.TitleBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AbstractActivity implements View.OnClickListener{
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,AboutActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitleBar;
	
	private TextView mVersionTv;
	
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_layout);
		setUpView();
	}

	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_about));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		ViewGroup item1 = (ViewGroup) findViewById(R.id.item1);
		item1.setOnClickListener(this);
		setUpItem(item1, R.drawable.icon_aboutservice, "服务条款");
		
		ViewGroup item2 = (ViewGroup) findViewById(R.id.item2);
		setUpItem(item2, R.drawable.icon_about_version_update, "版本更新");
		
		ViewGroup item3 = (ViewGroup) findViewById(R.id.item3);
	    item3.setOnClickListener(this);
		setUpItem(item3, R.drawable.icon_about_us, "关于颜语");
		
		ViewGroup item4 = (ViewGroup) findViewById(R.id.item4);
		setUpItem(item4, R.drawable.icon_about_welcome, "欢迎页面");
		
		ViewGroup item5 = (ViewGroup) findViewById(R.id.item5);
		item5.setOnClickListener(this);
		setUpItem(item5, R.drawable.ouer_dianhua, "400-667-0190");
		
		mVersionTv  = (TextView) findViewById(R.id.version);
		mVersionTv.setText("版本" + AppUtil.getVersion(this));
	}
	
	private void setUpItem(ViewGroup itemGroup,int drawableResource,String title){
		ImageView icon = (ImageView) itemGroup.findViewById(R.id.icon);
		icon.setImageResource(drawableResource);
		TextView tV = (TextView) itemGroup.findViewById(R.id.title);
		tV.setText(title);
	}

    @Override
    public void onClick(View v) {
        String url = "";
        int id = v.getId();
        switch (id) {
        case R.id.item3:
            url = "http://z.drxiaomei.com/m/about.html";
            GoodsDetailActivity.startActivity(this, url,null,null);
            break;
        case R.id.item1:
            url = "http://z.drxiaomei.com/m/agreement.html";
            GoodsDetailActivity.startActivity(this, url,null,null);
            break;
        case R.id.item5:
        	showProgressDialog();
            break;
        default:
            break;
        }
    }

	private void showProgressDialog(){
        AlertDialog.Builder builder = new Builder(this);
//        builder.setMessage("400-667-0190");
        builder.setTitle("400-667-0190");
        builder.setPositiveButton("呼叫", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006670190")); 
            	startActivity(intent);  
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
	}
    
}
