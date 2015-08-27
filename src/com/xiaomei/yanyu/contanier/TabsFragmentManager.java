package com.xiaomei.yanyu.contanier;

import java.util.HashMap;
import java.util.Map;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.levelone.SharesFragment;
import com.xiaomei.yanyu.levelone.HomeFragment;
import com.xiaomei.yanyu.levelone.MallFragment;
import com.xiaomei.yanyu.levelone.MerchantFragment;
import com.xiaomei.yanyu.levelone.UserFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by huzhi on 15-3-9.
 */
public class TabsFragmentManager {
	
	private Map<String ,Fragment> fragments = new HashMap<String ,Fragment>();

    public TabsFragmentManager(){};

    public Fragment getFragment(int position){
        Fragment fragment = null;
        switch (position) {
		case 0:
			fragment = new HomeFragment();

			break;
		case 1:
			fragment = new MallFragment();
			break;
		case 2:
			fragment = new MerchantFragment();
			break;
		case 3:
			fragment = new SharesFragment();
			break;
		case 4:
			fragment = new UserFragment();
			break;
		default:
			break;
		}
        Log.d("111", "name = " + fragment.getClass().getSimpleName());
		fragments.put(position+"", fragment);
        return fragment;
    }

    @SuppressLint("NewApi")
	public void commitFragment(int position, Activity ac){
        FragmentManager fragmentManager = ac.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
//        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        if(fragments.get(position+"")==null){
            fragment = getFragment(position);
        }else {
        	fragment = fragments.get(position + "");
        }
        fragmentTransaction.replace(R.id.container_body_layout,fragment,String.valueOf(position));
        fragmentTransaction.show(fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
