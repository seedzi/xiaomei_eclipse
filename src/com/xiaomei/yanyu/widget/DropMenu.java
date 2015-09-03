package com.xiaomei.yanyu.widget;

import u.aly.ad;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.xiaomei.yanyu.R;

public class DropMenu extends FrameLayout implements View.OnClickListener {

    private ListView mListView;
    private PopupWindow mPopupWindow;
    private DataSetObserver mDataSetObserver;
    private TextView mTitle;
    protected int mSelectedPosition;
    private OnItemClickListener mItemClickListener;

    public DropMenu(Context context) {
        super(context);
        init();
    }
    
    public DropMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public DropMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnClickListener(this);
        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.drop_menu_pop_view, null);
        popupView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setBackgroundDrawable(getColorDrawable(android.R.color.transparent));
        mListView = (ListView) popupView.findViewById(android.R.id.list);
        mPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                mTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
                mTitle.setActivated(false);
            }
        });
        
        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.drop_menu, this, false);
        mTitle = (TextView) titleView.findViewById(android.R.id.title);
        addView(titleView);
        
        mDataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                if (mListView.getCount() == 0) {
                    return;
                }

                mSelectedPosition = 0;
                ListAdapter adapter = mListView.getAdapter();
                View selectedView = adapter.getView(mSelectedPosition, null, mListView);
                if (mItemClickListener != null) {
                    long itemId = adapter.getItemId(mSelectedPosition);
                    mListView.performItemClick(selectedView, mSelectedPosition, itemId);
                } else {
                    mTitle.setText(getTitle(selectedView));
                }
            }
            @Override
            public void onInvalidated() {
                mTitle.setText(null);
            }
        };
    }
    
    private Drawable getColorDrawable(int coloRes) {
        return new ColorDrawable(getResources().getColor(coloRes));
    }
    
    public void setAdapter(ListAdapter adapter) {
        ListAdapter oldAdapter = getAdapter();
        if (oldAdapter != null && mDataSetObserver != null) {
            oldAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        if (adapter != null) {
            adapter.registerDataSetObserver(mDataSetObserver);
        }
        mListView.setAdapter(adapter);
    }
    
    public ListAdapter getAdapter() {
        return mListView.getAdapter();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mItemClickListener = new onDropItemSelectedListener(listener);
        mListView.setOnItemClickListener(mItemClickListener);
    }

    public void onClick(View v) {
        toggleDropMenu();
    }

    private void toggleDropMenu() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up, 0);
            mTitle.setActivated(true);
            mPopupWindow.showAsDropDown(this);
        }
    }
    
    private void setSelected(View view, boolean selected) {
        if (view != null) {
            view.setSelected(selected);
            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(selected);
            }
        }
    }
    
    private CharSequence getTitle(View view) {
        return ((TextView) view.findViewById(android.R.id.text1)).getText();
    }
    
    private class onDropItemSelectedListener implements OnItemClickListener {

        private final OnItemSelectedListener mListener;

        public onDropItemSelectedListener(OnItemSelectedListener listener) {
            mListener = listener;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View selectedView = parent.getChildAt(mSelectedPosition);
            if (view != selectedView) {
                setSelected(selectedView, false);
            }
            mSelectedPosition = position;
            setSelected(view, true);
            mTitle.setText(getTitle(view));
            mPopupWindow.dismiss();
            mListener.onItemSelected(parent, view, position, id);
        }
    }
}
