package com.xiaomei.yanyu.launch;



import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.SharePreferenceKey;
import com.xiaomei.yanyu.SharePreferenceWrap;
import com.xiaomei.yanyu.contanier.TabsActivity;
import com.xiaomei.yanyu.launch.control.LaunchControl;
import com.xiaomei.yanyu.widget.PagerAdapter;
import com.xiaomei.yanyu.widget.ViewPager;

public class LaunchActivity extends  AbstractActivity<LaunchControl>{

	private View splash;
	
	private com.xiaomei.yanyu.widget.ViewPager pager;
	
	private LayoutInflater mInflater;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mInflater = LayoutInflater.from(this);
        
        setContentView(R.layout.activity_launcher);
        useAnimation = false;
        
        splash = findViewById(R.id.splash);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter());
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	init();
            }
        },1500);
        
        
        android.util.Log.d("111", "3.2 isNumeric" + isNumeric("3.2"));
        
        android.util.Log.d("111", "355  isNumeric" + isNumeric("3555"));
    	
        
        android.util.Log.d("111", "35a5  isNumeric" + isNumeric("355a5"));
    }
    
    private void  init(){
    	SharePreferenceWrap sharePreferenceWrap = new SharePreferenceWrap(SharePreferenceKey.APP);
    	if(sharePreferenceWrap.getBoolean(SharePreferenceKey.APP_KEY_INSTALL_FIRST, true)){
        	Animation animationOut =AnimationUtils.loadAnimation(this, R.anim.activity_slid_out_from_right);//加载Xml文件中
        	splash.startAnimation(animationOut);
        	Animation animationIn =AnimationUtils.loadAnimation(this, R.anim.activity_slid_in_from_right);//加载Xml文件中
        	pager.startAnimation(animationIn);
        	animationIn.setAnimationListener(new AnimationListener() {
    			@Override
    			public void onAnimationStart(Animation animation) {
    			}
    			
    			@Override
    			public void onAnimationRepeat(Animation animation) {
    			}
    			
    			@Override
    			public void onAnimationEnd(Animation animation) {
    		    	splash.setVisibility(View.GONE);
    		    	pager.setVisibility(View.VISIBLE);
    			}
    		});
        	sharePreferenceWrap.putBoolean(SharePreferenceKey.APP_KEY_INSTALL_FIRST, false);
    	}else{
	   		TabsActivity.startActivity(LaunchActivity.this);
	  		finish();
    	}
    	

//        CommentsActivity.startActivity(this);
//    	LoginAndRegisterActivity.startActivity(LaunchActivity.this);
//    	TabsActivity.startActivity(LaunchActivity.this);
    	/*
    	 
    	   		TabsActivity.startActivity(LaunchActivity.this);
  		finish();
    	 
    	 
    	 */

  		/*
    	if(UserUtil.isUserValid()){
    		TabsActivity.startActivity(LaunchActivity.this);
    	}else{
    		LoginAndRegisterActivity.startActivity(LaunchActivity.this);
    	}
    	*/
//    	PhoneGapAc.startActivity(LaunchActivity.this);
    	
//    	finish();	
    	
//    	ZhifubaoPayManager.getInstance().setCurrentActivity(this);
//    	ZhifubaoPayManager.getInstance().pay();
    }
    
    
    
    
    class MyPagerAdapter extends PagerAdapter {
		

    	private int[] res = new int[]{R.drawable.guide_01,R.drawable.guide_02,R.drawable.guide_03,R.drawable.guide_04};
    	
		public MyPagerAdapter() {
		}

		@Override
		public void destroyItem(View collection, int position, Object arg2) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return res.length;
		}

		@Override
		public Object instantiateItem(View collection, int position) {
			View converView =  mInflater.inflate(R.layout.guide_item_layout, null);
			ViewGroup vG = (ViewGroup) converView.getParent();
			if(vG!=null)
				vG.removeView(converView);
			((ViewPager) collection).addView(converView, 0);
			ImageView icon = (ImageView) converView.findViewById(R.id.icon);
			icon.setImageResource(res[position]);
			if(position == 3){
				converView.findViewById(R.id.button).setVisibility(View.VISIBLE);
				converView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
		    	   		TabsActivity.startActivity(LaunchActivity.this);
		    	  		finish();
					}
				});
			}else{
				converView.findViewById(R.id.button).setVisibility(View.GONE);
			}
			return converView;
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}
		@Override
		public Parcelable saveState() {
			return null;
		}
		@Override
		public void startUpdate(View arg0) {
		}
	}

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i)) && '.' != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
