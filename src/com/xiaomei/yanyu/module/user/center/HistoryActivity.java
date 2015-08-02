package com.xiaomei.yanyu.module.user.center;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.AbstractActivity;
import com.xiaomei.yanyu.bean.HistroyItem;
import com.xiaomei.yanyu.widget.TitleBar;

public class HistoryActivity extends AbstractActivity {
	
	public static void startActivity(Activity ac){
		Intent intent = new Intent(ac,HistoryActivity.class);
		ac.startActivity(intent);
		ac.overridePendingTransition(R.anim.activity_slid_in_from_right, R.anim.activity_slid_out_no_change);
	}

	private TitleBar mTitleBar;
	
	private ListView mListView;
	
	private HistoryAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_layout);
		setUpView();
	}
	
	private void setUpView(){
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setTitle(getResources().getString(R.string.user_history));
		mTitleBar.setBackListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.list);
		mAdapter = new HistoryAdapter(this);
		mListView.setAdapter(mAdapter);
	}
	
    private class HistoryAdapter extends ArrayAdapter<HistroyItem> {

        public HistoryAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView != null ? convertView
                    : LayoutInflater.from(getContext()).inflate(R.layout.item_history_layout,
                            parent, false);
            return itemView;
        }
    }
}
