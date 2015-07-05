/*
 * Copyright (c) 2015, Xiaomi Inc. All rights reserved.
 */

package com.xiaomei.yanyu.widget;

import android.content.ContentResolver;
import android.graphics.Bitmap;

/**
 * Interface to enable reuse of {@link ThumbnailLoadTask} with various
 * different views.
 */
public interface BitmapHolder {
    public int getThumbnailWidth();

    public int getThumbnailHeight();

    public void setThumbnail(Bitmap result);

    public void setThumbnailToDefault();

    public ContentResolver getResolver();

    public boolean bitmapSetToDefault();

    public void thumbnailLoadFailed();
}
