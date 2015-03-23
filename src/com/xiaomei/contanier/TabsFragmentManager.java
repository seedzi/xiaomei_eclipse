package com.xiaomei.contanier;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xiaomei.R;
import com.xiaomei.levelone.BeautifulRingFragment;
import com.xiaomei.levelone.HomeFragment;
import com.xiaomei.levelone.MallFragment;
import com.xiaomei.levelone.MechanismFragment;
import com.xiaomei.levelone.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huzhi on 15-3-9.
 */
public class TabsFragmentManager {

    private List<Fragment> fragments = new ArrayList<Fragment>();

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
			fragment = new MechanismFragment();
			break;
		case 3:
			fragment = new BeautifulRingFragment();
			break;
		case 4:
			fragment = new UserFragment();
			break;
		default:
			break;
		}
        fragments.add(fragment);
        return fragment;
    }

    @SuppressLint("NewApi")
	public void commitFragment(int position,FragmentActivity ac){
        FragmentManager fragmentManager = ac.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(position));
        if(fragment==null || !fragments.contains(fragment)){
            fragment = getFragment(position);
        }
        fragmentTransaction.replace(R.id.container_body_layout,fragment,String.valueOf(position));
        fragmentTransaction.show(fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
