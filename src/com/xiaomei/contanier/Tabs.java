package com.xiaomei.contanier;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dx.util.Uint;
import com.xiaomei.R;
import com.xiaomei.util.ScreenUtils;
import com.xiaomei.widget.Tab;

/**
 * Created by huzhi on 15-3-9.
 */
@SuppressLint("NewApi")
public class Tabs extends LinearLayout implements View.OnClickListener{


    private Context mContext;
    
    private TabsFragmentManager mMTabsFragmentManager;
    
    int[] tabs_drawable_res = {R.drawable.tabs_home_gary, 
			R.drawable.tabs_shopping_gary,
			R.drawable.tabs_mechanism_gary,
			R.drawable.tabs_circle_gary,
			R.drawable.tabs_me_gary};
    
    int[] tabs_drawable_res_checked = {R.drawable.tabs_home, 
			R.drawable.tabs_shopping,
			R.drawable.tabs_mechanism,
			R.drawable.tabs_circle,
			R.drawable.tabs_me};
    
    List<Tab>tabViews = new ArrayList<Tab>();
    
    String[] tabs_names;

    public Tabs(Context context) {
        super(context);
        init(context);
    }

    public Tabs(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Tabs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ResourceAsColor")
	private void init(Context context){
    	LayoutInflater inflater = LayoutInflater.from(context);
    	mContext = context;
    	mMTabsFragmentManager = new TabsFragmentManager();
    	tabs_names = mContext.getResources().getStringArray(R.array.tabs_names);
        int index = 0;
        tabViews.clear();
        for(String tab_name : tabs_names){
            Tab tab = (Tab) inflater.inflate(R.layout.item_tab_layout, null);
            tab.setTabName(tab_name);
            tab.setTabIndex(index);
            Drawable topDrawable =  getResources().getDrawable(tabs_drawable_res[index]);
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
            tab.setCompoundDrawables(null,topDrawable , null, null);
            tab.setWidth(ScreenUtils.getScreenWidth(context)/tabs_names.length);
            tab.setTag(index);
            index ++;
            tab.setOnClickListener(this);
            addView(tab);
            tabViews.add(tab);
        }
        getChildAt(0).performClick();
    }

    @Override
    public void onClick(View v) {
    	int tag = (Integer) v.getTag();
    	if(v instanceof Tab){
        	int index = 0;
    		for(Tab tab :tabViews){
    	        Drawable topDrawable =  getResources().getDrawable(tabs_drawable_res[index]);
    	        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
    	        tab.setCompoundDrawables(null,topDrawable , null, null);
    	        index ++;
    		}
	        Drawable topDrawable =  getResources().getDrawable(tabs_drawable_res_checked[tag]);
	        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
	        ((Tab)v).setCompoundDrawables(null,topDrawable , null, null);
    	}
    	mMTabsFragmentManager.commitFragment(tag, (FragmentActivity)mContext);
    }

}
