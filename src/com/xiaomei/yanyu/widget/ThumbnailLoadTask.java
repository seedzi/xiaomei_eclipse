/*
 * Copyright (c) 2015, Xiaomi Inc. All rights reserved.
 */

package com.xiaomei.yanyu.widget;

import java.io.IOException;

import com.xiaomei.yanyu.util.LogTag;
import com.xiaomei.yanyu.util.LogUtils;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

/**
 * Performs the load of a thumbnail bitmap in a background
 * {@link android.os.AsyncTask}. Available for use with any view that implements
 * the {@link BitmapHolder} interface.
 */
public class ThumbnailLoadTask extends AsyncTask<Uri, Void, Bitmap> {
    private static final String LOG_TAG = LogTag.getLogTag();

    private final BitmapHolder mHolder;
    private final int mWidth;
    private final int mHeight;

    public ThumbnailLoadTask(BitmapHolder holder, int width, int height) {
        mHolder = holder;
        mWidth = width;
        mHeight = height;
    }

    public static void load(Uri uri, BitmapHolder holder) {
        load(null, uri, holder);
    }

    public static void load(Uri thumbnailUri, Uri contentUri, BitmapHolder holder) {
        final int width = holder.getThumbnailWidth();
        final int height = holder.getThumbnailHeight();
        if (width == 0 || height == 0) {
            holder.setThumbnailToDefault();
            return;
        }

        // begin loading a thumbnail if this is an image and either the thumbnail or the original
        // content is ready
        if ((thumbnailUri != null || contentUri != null) && holder.bitmapSetToDefault()) {
            final ThumbnailLoadTask task = new ThumbnailLoadTask(
                    holder, width, height);
            task.execute(thumbnailUri, contentUri);
        } else {
            holder.setThumbnailToDefault();
        }
    }

    @Override
    protected Bitmap doInBackground(Uri... params) {
        Bitmap result = loadBitmap(params[0]);
        if (result == null) {
            result = loadBitmap(params[1]);
        }

        return result;
    }

    private Bitmap loadBitmap(final Uri thumbnailUri) {
        if (thumbnailUri == null) {
            LogUtils.e(LOG_TAG, "Attempting to load bitmap for null uri");
            return null;
        }

        AssetFileDescriptor fd = null;
        try {
            fd = mHolder.getResolver().openAssetFileDescriptor(thumbnailUri, "r");
            if (isCancelled() || fd == null) {
                return null;
            }

            final BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            opts.inDensity = DisplayMetrics.DENSITY_LOW;

            BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, opts);
            if (isCancelled() || opts.outWidth == -1 || opts.outHeight == -1) {
                return null;
            }

            opts.inJustDecodeBounds = false;
            // Shrink both X and Y (but do not over-shrink)
            // and pick the least affected dimension to ensure the thumbnail is fillable
            // (i.e. ScaleType.CENTER_CROP)
            final int wDivider = Math.max(opts.outWidth / mWidth, 1);
            final int hDivider = Math.max(opts.outHeight / mHeight, 1);
            opts.inSampleSize = Math.min(wDivider, hDivider);

            LogUtils.d(LOG_TAG, "in background, src w/h=%d/%d dst w/h=%d/%d, divider=%d",
                    opts.outWidth, opts.outHeight, mWidth, mHeight, opts.inSampleSize);

            final Bitmap originalBitmap = BitmapFactory.decodeFileDescriptor(
                    fd.getFileDescriptor(), null, opts);
            if (originalBitmap != null) {
                final Matrix matrix = new Matrix();
                return Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(),
                        originalBitmap.getHeight(), matrix, true);
            }
            return originalBitmap;
        } catch (Throwable t) {
            LogUtils.i(LOG_TAG, "Unable to decode thumbnail %s: %s %s", thumbnailUri,
                    t.getClass(), t.getMessage());
        } finally {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    LogUtils.e(LOG_TAG, e, "");
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result == null) {
            LogUtils.d(LOG_TAG, "back in UI thread, decode failed or file does not exist");
            mHolder.thumbnailLoadFailed();
            return;
        }

        LogUtils.d(LOG_TAG, "back in UI thread, decode success, w/h=%d/%d", result.getWidth(),
                result.getHeight());
        mHolder.setThumbnail(result);
    }

}
