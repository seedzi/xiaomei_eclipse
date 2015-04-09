package com.xiaomei.module.user.center;

import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xiaomei.R;
import com.xiaomei.bean.User;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.CircleImageView;
import com.xiaomei.widget.TitleBar;
import com.yuekuapp.BaseActivity;

public class UserInfoActivity extends BaseActivity<UserCenterControl> implements View.OnClickListener{

	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserInfoActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlebar;
	
	private CircleImageView mUserIcon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_layout);
		initView();
		initData();
	}
	
	private void initView(){
		mTitlebar = (TitleBar) findViewById(R.id.titlebar);
		mTitlebar.setTitle("用戶信息");
		mTitlebar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.loginout).setOnClickListener(this);
		mUserIcon = (CircleImageView) findViewById(R.id.user_icon);
		mUserIcon.setOnClickListener(this);
	}
	
	private void initData(){
		if(UserUtil.getUser()!=null)
			mControl.getUserInfo(User.getFromShareP());
		else
			Toast.makeText(getApplicationContext(), "没有用户", 0).show();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.loginout:
			UserUtil.clearUser();
			break;
		case R.id.user_icon:
	         Intent intent = new Intent();  
             /* 开启Pictures画面Type设定为image */  
             intent.setType("image/*");  
             /* 使用Intent.ACTION_GET_CONTENT这个Action */  
             intent.setAction(Intent.ACTION_GET_CONTENT);   
             /* 取得相片后返回本画面 */  
             startActivityForResult(intent, 1);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();  
            ContentResolver cr = this.getContentResolver();  
            try {  
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
                /* 将Bitmap设定到ImageView */  
                mUserIcon.setImageBitmap(bitmap);  
            } catch (FileNotFoundException e) {  
            }  
        }  
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	
}
