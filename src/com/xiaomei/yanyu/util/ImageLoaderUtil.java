package com.xiaomei.yanyu.util;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageLoaderUtil {

    private ImageLoaderUtil() {}

    public static DisplayImageOptions getDisplayOptions(int imageRes) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(imageRes)
                .showImageForEmptyUri(imageRes)
                .showImageOnFail(imageRes)
                .build();
    }
}
