package com.xiaomei.contanier;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaomei.R;

/**
 * Created by huzhi on 15-3-9.
 */
public class Tabs extends LinearLayout implements View.OnClickListener{


    private Context mContext;

    public Tabs(Context context) {
        super(context);
        init();
    }

    public Tabs(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Tabs(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        String[] tabs_names = mContext.getResources().getStringArray(R.array.tabs_names);
        int index = 0;
        for(String tab_name : tabs_names){
            Tab tab = new Tab(mContext);
            tab.setTabName(tab_name);
            tab.setTabIndex(index);
            index ++;
            tab.setOnClickListener(this);
            addView(tab);
        }
    }

    @Override
    public void onClick(View v) {

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
