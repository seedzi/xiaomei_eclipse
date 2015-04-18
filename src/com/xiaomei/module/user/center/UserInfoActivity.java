package com.xiaomei.module.user.center;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.xiaomei.AbstractActivity;
import com.xiaomei.R;
import com.xiaomei.bean.User;
import com.xiaomei.module.user.LoginAndRegisterActivity;
import com.xiaomei.module.user.center.control.UserCenterControl;
import com.xiaomei.util.UserUtil;
import com.xiaomei.widget.CircleImageView;
import com.xiaomei.widget.TitleBar;

public class UserInfoActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{

	public static void startActivity(Context context){
		Intent intent = new Intent(context,UserInfoActivity.class);
		context.startActivity(intent);
	}
	
	private TitleBar mTitlebar;
	
	private CircleImageView mUserIcon;
	
	private ProgressDialog mProgressDialog;
	
	private String iconPath;
	
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
			loginOut();
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
            Cursor cursor = cr.query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
            cursor.moveToFirst();  
            String path = cursor.getString(column_index);  
            iconPath = path;
            uploadIcon(path);
        }  
		super.onActivityResult(requestCode, resultCode, data);
	}
	// ===========================================  api ===================================================
	
	private void loginOut(){
		showProgressDialog("登出中...");
		mControl.loginOutAsyn();
	}
	
	private void uploadIcon(String filePath){
//		showProgressDialog("上传图片中...");
		mControl.uploadIcon(filePath);
	}
	
	// =========================================== ProgressDialog ===================================================
	
	private void showProgressDialog(String message){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("提示");
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	
	private void dismissDialog(){
		if(mProgressDialog!=null && mProgressDialog.isShowing())
			mProgressDialog.dismiss();
	}
	
	// =========================================== CallBack ===================================================
	public void loginOutAsynCallBack(){
	    UserUtil.clearUser();
		dismissDialog();
		AbstractActivity.clearActivity();
		LoginAndRegisterActivity.startActivity(this);
	}
	
	public void loginOutAsynExceptionCallBack(){
		dismissDialog();
	}
	
	public void uploadIconCallBack(){
		dismissDialog();
		if(iconPath!=null){
	        Bitmap bitmap = BitmapFactory.decodeFile(iconPath);
			mUserIcon.setImageBitmap(bitmap);
		}
	}
	
	public void uploadIconExceptionCallBack(){
		dismissDialog();
	}
	
	
}
