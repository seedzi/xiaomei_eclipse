/*
 * Copyright (C) 2015 Xiaomi Inc. All rights reserved.
 */

package com.xiaomei.yanyu.widget;

import java.util.ArrayList;
import java.util.List;

import com.xiaomei.yanyu.R;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wangkun1 on 6/30/15.
 */
public class AttachmentContainer extends LinearLayout implements View.OnClickListener {

    private List<Uri> mAttachmentUris = new ArrayList<Uri>();

    public AttachmentContainer(Context context) {
        super(context);
    }

    public AttachmentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AttachmentContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addAttachment(final Uri uri) {
        View attachView = LayoutInflater.from(getContext()).inflate(R.layout.screenshot_tile, this, false);
        addView(attachView, getChildCount() - 1);
        mAttachmentUris.add(uri);

        final ThumbnailView thumbnail = (ThumbnailView) attachView.findViewById(R.id.screenshot_thumbnail);
        // Post till added view layout, so that getWith & getHeight not null
        thumbnail.post(new Runnable() {
            @Override
            public void run() {
                ThumbnailLoadTask.load(uri, thumbnail);
            }
        });

        attachView.findViewById(R.id.delete).setOnClickListener(this);
    }

    public ArrayList<Uri> getAttachmentUris() {
        return new ArrayList<Uri>(mAttachmentUris);
    }

    @Override
    public void onClick(View v) {
        View attachView = (View) v.getParent();
        int i = indexOfChild(attachView);
        mAttachmentUris.remove(i);
        removeView(attachView);
    }
}
