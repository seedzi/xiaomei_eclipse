package com.xiaomei.yanyu.contanier;

import java.util.ArrayList;
import java.util.List;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.ScreenUtils;
import com.xiaomei.yanyu.widget.Tab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by huzhi on 15-3-9.
 */
@SuppressLint("NewApi")
public class Tabs extends LinearLayout implements View.OnClickListener{


    private Context mContext;
    
    private TabsFragmentManager mMTabsFragmentManager;
    
    int[] tabs_drawable_res = {R.drawable.tabs_home_gary, 
			R.drawable.tabs_fenlei,
			R.drawable.tabs_merchant_gary,
			R.drawable.tabs_circle_gary,
			R.drawable.tabs_me_gary};
    
    int[] tabs_drawable_res_checked = {R.drawable.tabs_home, 
			R.drawable.tabs_fenlei_press,
			R.drawable.tabs_merchant,
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
            tab.setGravity(Gravity.CENTER);
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
    	        tab.setTextColor(getResources().getColor(R.color.color_gray));
    	        index ++;
    		}
	        Drawable topDrawable =  getResources().getDrawable(tabs_drawable_res_checked[tag]);
	        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
	        ((Tab)v).setCompoundDrawables(null,topDrawable , null, null);
	        ((Tab)v).setTextColor(getResources().getColor(R.color.color_button_normal));
    	}
    	mMTabsFragmentManager.commitFragment(tag, (Activity) mContext);
    }

}
