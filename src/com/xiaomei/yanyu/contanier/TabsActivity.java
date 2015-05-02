package com.xiaomei.yanyu.contanier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.KeyCharacterMap.KeyData;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.XiaoMeiApplication;
import com.xiaomei.yanyu.api.exception.XiaoMeiCredentialsException;
import com.xiaomei.yanyu.api.exception.XiaoMeiIOException;
import com.xiaomei.yanyu.api.exception.XiaoMeiJSONException;
import com.xiaomei.yanyu.api.exception.XiaoMeiOtherException;
import com.xiaomei.yanyu.comment.CommentListActivity;
import com.xiaomei.yanyu.util.UserUtil;

/**
 * Created by huzhi on 15-2-17.
 */
public class TabsActivity extends  AbstractActivity{


    public static void startActivity(Context context){
        Intent intent = new Intent(context,TabsActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_layout);
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    XiaoMeiApplication.getInstance().getApi().showCommentList(UserUtil.getUser().getToken(), "1015", "goods", "1", "10");
                } catch (XiaoMeiCredentialsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (XiaoMeiIOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (XiaoMeiJSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (XiaoMeiOtherException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();*/
        // test
        
        /*
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					XiaoMeiApplication.getInstance().getApi().showUserMsg();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}).start();
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					XiaoMeiApplication.getInstance().getApi().updateUserInfo(null, UserUtil.getUser().getToken());
				} catch (XiaoMeiCredentialsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiIOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiJSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XiaoMeiOtherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();*/
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	if(keyCode == KeyEvent.KEYCODE_BACK){
//    		finish();
//    		return true;
//    	}
//    		
//    	return super.onKeyDown(keyCode, event);
//    }
    
    @Override
    public void onBackPressed() {
    	android.util.Log.d("111", "onBackPressed");
    	super.onBackPressed();
    }
    
}
