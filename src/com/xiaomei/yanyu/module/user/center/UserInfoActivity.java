package com.xiaomei.yanyu.module.user.center;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.bean.User;
import com.xiaomei.yanyu.leveltwo.GoodsDetailActivity;
import com.xiaomei.yanyu.module.user.LoginAndRegisterActivity;
import com.xiaomei.yanyu.module.user.center.control.UserCenterControl;
import com.xiaomei.yanyu.util.UserUtil;
import com.xiaomei.yanyu.widget.CircleImageView;
import com.xiaomei.yanyu.widget.TitleBar;
import com.xiaomei.yanyu.widget.VipGradeView;

public class UserInfoActivity extends AbstractActivity<UserCenterControl> implements View.OnClickListener{

	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,UserInfoActivity.class);
		ac.startActivity(intent);
		 ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}
	
	private TitleBar mTitlebar;
	
	private CircleImageView mUserIcon;
	
	private ProgressDialog mProgressDialog;
	
	private String iconPath;
	
	/**昵称*/
	private EditText nickNameEd;
	/**联系方式*/
	private EditText linkEd;
	/**身份证号*/
	private EditText shengFenZhengHaoEd;
	/**地址*/
	private EditText locationEd;
	/**用户头像*/
	private CircleImageView  userIcon;
	/**用户名*/
	private TextView userNameTv;
	/**vip说明*/
	private TextView vipTxtTv;
	/**Vip等级*/
	private VipGradeView vipGrade;
	
	private TextView userTypeDesc;
	/**累计消费*/	
	private TextView costTv;
	
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
			    if(!showDialog4UserUpload()){
			        finish();
			    }
			}
		});
		findViewById(R.id.loginout).setOnClickListener(this);
		mUserIcon = (CircleImageView) findViewById(R.id.user_icon);
		mUserIcon.setOnClickListener(this);
		
		nickNameEd = (EditText) findViewById(R.id.nick_name);
		linkEd = (EditText) findViewById(R.id.link);
		shengFenZhengHaoEd = (EditText) findViewById(R.id.sheng_fen_zheng_hao);
		locationEd = (EditText) findViewById(R.id.location);
		userIcon = (CircleImageView) findViewById(R.id.user_icon);
		userNameTv = (TextView) findViewById(R.id.user_name);
		vipTxtTv = (TextView) findViewById(R.id.vip_txt);
		vipGrade = (VipGradeView) findViewById(R.id.vip_grade);
		userTypeDesc = (TextView) findViewById(R.id.user_type_desc);
		costTv = (TextView) findViewById(R.id.cost);
		findViewById(R.id.huiyuan_guizhe).setOnClickListener(this);
	}
	
	private void initData(){
	    try {
	        User.UserInfo userInfo = UserUtil.getUser().getUserInfo();
	        android.util.Log.d("111", "userInfo = " + userInfo);
	        nickNameEd.setText(userInfo.getUsername());
	        linkEd.setText(userInfo.getMobile());
	        userNameTv.setText(userInfo.getUsername());
	        if(!TextUtils.isEmpty(userInfo.getAvatar()))
	            ImageLoader.getInstance().displayImage(userInfo.getAvatar(), userIcon);
	        vipTxtTv.setText(userInfo.getUserTypeDesc());
	        vipGrade.setGrade(Integer.valueOf(userInfo.getUserType())-1);
	        userTypeDesc.setText(userInfo.getUserTypeDesc());
	        costTv.setText("累计消费：" + userInfo.getCost());
	        shengFenZhengHaoEd.setText(userInfo.getIdcard());
	        locationEd.setText(userInfo.getAddress());
	        switch (Integer.valueOf(userInfo.getUserType())) {
			case 1:
				vipTxtTv.setText("普通会员");
				break;
			case 2:
				vipTxtTv.setText("银牌会员");
				break;
			case 3:
				vipTxtTv.setText("金牌会员");
				break;
			case 4:
				vipTxtTv.setText("砖石会员");
				break;
			default:
				break;
			}
        } catch (Exception e) {
        }
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.loginout:
            AlertDialog.Builder builder = new Builder(this);
            builder.setMessage("您确认要登出吗?");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    loginOut();
                }
            });
            builder.setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

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
		case R.id.huiyuan_guizhe:
			GoodsDetailActivity.startActivityWithTitle(this, "http://z.drxiaomei.com/m/rule.html","会员规则");
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
	
	// =========================================== ProgressDialog ==========================================
	
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
	
	// =========================================== CallBack =================================================
	public void loginOutAsynCallBack(){
	    UserUtil.clearUser();
		dismissDialog();
//		AbstractActivity.clearActivity();
		finish();
		LoginAndRegisterActivity.startActivity(this,true);
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
	
	public void uploadUserInfoCallBack(){
	    Toast.makeText(this, "用户信息更新成功", 0).show();
        dismissDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },500);
	}
	
	public void uploadUserInfoExceptionCallBack(){
	    Toast.makeText(this, "用户信息更新失败", 0).show();
        dismissDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },500);
	}
	
	@Override
	public void onBackPressed() {

	    if(showDialog4UserUpload()){
	    }else{
	        super.onBackPressed();
	    }
	}
	
    // =========================================== user upload =================================================
	/**
     * 检查用户信息是否修改
     */
	private boolean isUserMessgaeChanged(){
	    try {
	        User.UserInfo userInfo = UserUtil.getUser().getUserInfo();
	        if(!nickNameEd.getText().toString().trim().equals(userInfo.getUsername()))//姓名
	            return true;
	        if(!linkEd.getText().toString().trim().equals(userInfo.getMobile()))  //联系方式
	            return true;
	        if(!shengFenZhengHaoEd.getText().toString().trim().equals(userInfo.getIdcard()))//身份证 
	            return true;
	        if(!locationEd.getText().toString().trim().equals(userInfo.getAddress())) //地址
	            return true;
	        if(!TextUtils.isEmpty(mControl.getModel().getUploadFileUrl())) //头像
	            return true;
	        return false;
        } catch (Exception e) {
            return false;
        }
	}
	/**
	 * 如果用户信息修改了 弹出对话框
	 */
	private String server_username;
	private String server_link;
	private String server_location;
	private String server_shengFenZheng;
    private String server_iconUrl;
	private boolean showDialog4UserUpload(){
	    if(!isUserMessgaeChanged())
	        return false;
	    AlertDialog.Builder builder = new Builder(this);
	    builder.setMessage("您确认修改信息吗?");  
	    builder.setTitle("提示");  
	    
	    server_username = nickNameEd.getText().toString();
        if(TextUtils.isEmpty(server_username))
            server_username = UserUtil.getUser().getUserInfo().getUsername();
        
        server_link = linkEd.getText().toString();
        if(TextUtils.isEmpty(server_link))
            server_link = UserUtil.getUser().getUserInfo().getMobile();
        
        server_location = locationEd.getText().toString();
        if(TextUtils.isEmpty(server_location))
            server_location = UserUtil.getUser().getUserInfo().getAddress();
        
        server_shengFenZheng = shengFenZhengHaoEd.getText().toString();
        if(TextUtils.isEmpty(server_shengFenZheng))
            server_shengFenZheng = UserUtil.getUser().getUserInfo().getIdcard();
        
        server_iconUrl = mControl.getModel().getUploadFileUrl();
        if(TextUtils.isEmpty(server_iconUrl))
            server_iconUrl = UserUtil.getUser().getUserInfo().getAvatar();
        
	    builder.setPositiveButton("确认", new OnClickListener() {   
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            mControl.uploadUserInfo(
	                    server_username,
	                    server_link,
	                    server_location,
	                    server_shengFenZheng,
	                    server_iconUrl);
	            dialog.dismiss();    
	            showProgressDialog("用户信息上传中...");
	         }
	    });  
	    builder.setNegativeButton("取消", new OnClickListener() {   @Override
    	     public void onClick(DialogInterface dialog, int which) {
    	          dialog.dismiss();
    	          finish();
    	     }
	    });  
	    builder.create().show();
	    return true;
	}

	
}
