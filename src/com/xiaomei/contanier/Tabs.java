package com.xiaomei.contanier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dx.util.Uint;
import com.xiaomei.R;
import com.xiaomei.util.ScreenUtils;

/**
 * Created by huzhi on 15-3-9.
 */
@SuppressLint("NewApi")
public class Tabs extends LinearLayout implements View.OnClickListener{


    private Context mContext;
    
    private TabsFragmentManager mMTabsFragmentManager;

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
    	mContext = context;
    	mMTabsFragmentManager = new TabsFragmentManager();
        String[] tabs_names = mContext.getResources().getStringArray(R.array.tabs_names);
        int index = 0;
        for(String tab_name : tabs_names){
            Tab tab = new Tab(mContext);
            tab.setTabName(tab_name);
            tab.setTabIndex(index);
            tab.setWidth(ScreenUtils.getScreenWidth(context)/tabs_names.length);
            tab.setHeight((int) context.getResources().getDimension(R.dimen.tabs_height_dp));
            tab.setGravity(Gravity.CENTER);
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.txt_small_size_sp));
            tab.setTextColor(Color.BLACK);
            tab.setTag(index);
            index ++;
            tab.setOnClickListener(this);
            addView(tab);
        }
        getChildAt(0).performClick();
    }

    @Override
    public void onClick(View v) {
    	int tag = (Integer) v.getTag();
    	mMTabsFragmentManager.commitFragment(tag, (FragmentActivity)mContext);
    }


    /**
     * Created by huzhi on 15-3-9.
     */
    public class Tab extends TextView {


        private String tab_name;

        private int tab_index;

        public Tab(Context context) {
            super(context);
        }

        public Tab(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Tab(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setTabName(String name){
            tab_name = name;
            setText(name);
        }

        public void setTabIndex(int index){
            tab_index = index;
        }
    }
}
