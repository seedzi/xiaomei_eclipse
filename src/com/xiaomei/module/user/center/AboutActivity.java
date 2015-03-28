package com.xiaomei.module.user.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomei.BaseActiviy;
import com.xiaomei.R;
import com.xiaomei.widget.TitleBar;

public class AboutActivity extends BaseActiviy{
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context,AboutActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitleBar;
	
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
		setUpItem(item1, R.drawable.icon_aboutservice, "服务条款");
		
		ViewGroup item2 = (ViewGroup) findViewById(R.id.item2);
		setUpItem(item2, R.drawable.icon_about_version_update, "版本更新");
		
		ViewGroup item3 = (ViewGroup) findViewById(R.id.item3);
		setUpItem(item3, R.drawable.icon_about_us, "关于小美");
		
		ViewGroup item4 = (ViewGroup) findViewById(R.id.item4);
		setUpItem(item4, R.drawable.icon_about_welcome, "欢迎页面");
	}
	
	private void setUpItem(ViewGroup itemGroup,int drawableResource,String title){
		ImageView icon = (ImageView) itemGroup.findViewById(R.id.icon);
		icon.setImageResource(drawableResource);
		TextView tV = (TextView) itemGroup.findViewById(R.id.title);
		tV.setText(title);
	}
	
}
