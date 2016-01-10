package com.xiaomei.yanyu.widget;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ValuePreference extends RelativeLayout {

    private TextView mTitle;

    private EditText mValue;

    public ValuePreference(Context context) {
        super(context);
    }

    public ValuePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValuePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mTitle = (TextView)findViewById(R.id.title);
        mValue = (EditText)findViewById(R.id.value);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setValue(CharSequence value) {
        mValue.setText(value);
    }

    public void setEditable(boolean editable) {
        mValue.setEnabled(editable);
    }

    public Editable getValue() {
        return mValue.getText();
    }

    public void setHint(CharSequence hint) {
        mValue.setHint(hint);
    }

    public void setHint(int resid) {
        mValue.setHint(resid);
    }
}
