/*
 * Copyright (c) 2015, Xiaomi Inc. All rights reserved.
 */

package com.xiaomei.yanyu.util;

public class LogTag {
    private static String sLogTag = "Yanyu";

    /**
     * Get the log tag to apply to logging.
     */
    public static String getLogTag() {
        return sLogTag;
    }

    /**
     * Sets the app-wide log tag to be used in most log messages, and for enabling logging
     * verbosity. This should be called at most once, during app start-up.
     */
    public static void setLogTag(final String logTag) {
        sLogTag = logTag;
    }
}
