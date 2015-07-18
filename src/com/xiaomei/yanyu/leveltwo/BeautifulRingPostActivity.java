package com.xiaomei.yanyu.leveltwo;

import com.xiaomei.yanyu.AsyncRequestService;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.util.UiUtil;
import com.xiaomei.yanyu.widget.AttachmentContainer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class BeautifulRingPostActivity extends Activity implements OnClickListener {
    
    private static final int REQUEST_PICK_IMAGE = 0;
    
    private TextView mTitle;
    private View mHome;
    private View mPost;
    private TextView mMessage;
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
                BeautifulRingPostActivity.this.onBackPressed();
            }
        });
        mPost = actionbarView.findViewById(R.id.new_post);
        mPost.setOnClickListener(this);
        
        mMessage = (TextView) findViewById(R.id.message);
        mImageContainer = (AttachmentContainer) findViewById(R.id.attachment_container);
        mAttachImage = mImageContainer.findViewById(R.id.attach_image);
        mAttachImage.setOnClickListener(this);
        
        setTitle(R.string.title_activity_post);
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
