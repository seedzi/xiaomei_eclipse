/*
 * Copyright (c) 2015, Xiaomi Inc. All rights reserved.
 */

package com.xiaomei.yanyu.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wangkun1 on 7/1/15.
 */
public class ThumbnailView extends ImageView implements BitmapHolder {
    /**
     * Resource ID of the image to be used as a placeholder until the network image is loaded.
     */
    private int mDefaultImageId;

    /**
     * Resource ID of the image to be used if the network response fails.
     */
    private int mErrorImageId;

    private boolean mDefaultThumbnailSet = true;

    public ThumbnailView(Context context) {
        super(context);
    }

    public ThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbnailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the default image resource ID to be used for this view until the attempt to load it
     * completes.
     */
    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    /**
     * Sets the error image resource ID to be used for this view in the event that the image
     * requested fails to load.
     */
    public void setErrorImageResId(int errorImage) {
        mErrorImageId = errorImage;
    }

    @Override
    public int getThumbnailWidth() {
        return getWidth();
    }

    @Override
    public int getThumbnailHeight() {
        return getHeight();
    }

    @Override
    public void setThumbnail(Bitmap result) {
        if (result == null) {
            return;
        }

        setImageBitmap(result);
        mDefaultThumbnailSet = false;
    }

    @Override
    public void setThumbnailToDefault() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        } else {
            setImageDrawable(null);
        }
        mDefaultThumbnailSet = true;
    }

    @Override
    public ContentResolver getResolver() {
        return getContext().getContentResolver();
    }

    @Override
    public boolean bitmapSetToDefault() {
        return mDefaultThumbnailSet;
    }

    @Override
    public void thumbnailLoadFailed() {
        if (mErrorImageId != 0) {
            setImageResource(mErrorImageId);
        } else {
            setImageDrawable(null);
        }
        mDefaultThumbnailSet = true;
    }
}
