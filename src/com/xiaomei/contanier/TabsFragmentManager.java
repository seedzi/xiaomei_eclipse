package com.xiaomei.contanier;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;

import com.xiaomei.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huzhi on 15-3-9.
 */
public class TabsFragmentManager {

    private List<Fragment> fragments = new ArrayList<Fragment>();

    TabsFragmentManager(){
    }

    public Fragment getFragment(int position){
        Fragment fragment = null;
        return fragment;
    }

    public void commitFragment(int position,FragmentActivity ac){
        FragmentManager fragmentManager = ac.getFragmentManager();
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
