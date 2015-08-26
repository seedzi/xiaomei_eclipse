package com.xiaomei.yanyu.leveltwo;

import com.xiaomei.yanyu.AsyncRequestService;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.AttachmentContainer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ComposeUserShareActivity extends Activity implements OnClickListener {
    
    private static final int REQUEST_PICK_IMAGE = 0;

    private static final int MAX_MESSAGE_LENGTH = 300;
    
    private TextView mTitle;
    private View mHome;
    private View mPost;
    private TextView mMessage;
    private TextView mMessageLength;
    private AttachmentContainer mImageContainer;
    private View mAttachImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautiful_ring_post_layout);
        
        ActionBar actionBar = getActionBar();
        int mask = android.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.app.ActionBar.DISPLAY_SHOW_HOME | android.app.ActionBar.DISPLAY_SHOW_TITLE;
        actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM, mask);
        actionBar.setCustomView(R.layout.title_bar);
        View actionbarView = actionBar.getCustomView();
        mTitle = (TextView) actionbarView.findViewById(android.R.id.title);
        mHome = actionbarView.findViewById(android.R.id.home);
        mHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                UiUtil.hideSoftInputFromActivity(activity);
                activity.onBackPressed();
            }
        });
        mPost = actionbarView.findViewById(R.id.new_post);
        mPost.setOnClickListener(this);
        
        mMessage = (TextView) findViewById(R.id.message);
        mMessage.addTextChangedListener(new TextWatcher() {
            int start;
            int count;
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s != null ? s.length() : 0;
                if (length > MAX_MESSAGE_LENGTH) {
                    this.start = start;
                    this.count = count;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = s != null ? s.length() : 0;
                int sta = start;
                int end = start + count;
                start = count = -1;
                if (length > MAX_MESSAGE_LENGTH && sta > 0 && end > sta) {
                    int extra =  length - MAX_MESSAGE_LENGTH;
                    if (sta > 0 && end > sta) {
                        s.replace(sta, end, s.subSequence(sta, end - extra));
                    }
                    showMessageLength(MAX_MESSAGE_LENGTH);
                } else {
                    showMessageLength(length);
                }
            }
        });
        mMessageLength = (TextView) findViewById(R.id.message_length);
        CharSequence message = mMessage.getText();
        showMessageLength(message != null ? message.length() : 0);
        mImageContainer = (AttachmentContainer) findViewById(R.id.attachment_container);
        mAttachImage = mImageContainer.findViewById(R.id.attach_image);
        mAttachImage.setOnClickListener(this);
        
        setTitle(R.string.title_activity_post);
    }

    private void showMessageLength(int length) {
        mMessageLength.setText(String.format("%d/%d", length, MAX_MESSAGE_LENGTH));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitle.setText(titleId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PICK_IMAGE:
                if (resultCode == RESULT_OK && data != null) {
                    mImageContainer.addAttachment(data.getData());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_post:
                if (ensureField()) {
                    AsyncRequestService.startNewPost(this, mMessage.getText().toString(), mImageContainer.getAttachmentUris());
                    onBackPressed();
                }
                break;
            case R.id.attach_image:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
                break;
        }
    }

    private boolean ensureField() {
        if (TextUtils.isEmpty(mMessage.getText())) {
            UiUtil.showToast(this, getString(R.string.warning_field_empty, getString(R.string.ring_content_label)));
            return false;
        }
        return true;
    }
}
