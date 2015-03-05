package com.cpp.readpoetry.util;

import android.util.Log;
import com.cpp.readpoetry.config.GlobalConfig;

/**
 * Log Info
 * <p/>
 * VERBOSE DEBUG INFO WARN ERROR
 */
public class Logger {

    public static boolean logMode = GlobalConfig.DEBUG_MODE;

    public static String TAG = GlobalConfig.GLOBAL_TAG;

    public static void v(String tag, String msg) {
        if (!logMode) {
            return;
        }
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (!logMode) {
            return;
        }
        Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!logMode) {
            return;
        }
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!logMode) {
            return;
        }
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!logMode) {
            return;
        }
        Log.e(tag, msg);
    }

    public static void logInfo(Object tag, String msg) {
        if (!logMode) {
            return;
        }

        if (tag != null) {
            if (tag instanceof String) {
                Log.i(TAG, tag + ": " + msg);
            } else {
                Log.i(TAG, tag.getClass().getSimpleName() + ": " + msg);
            }
        } else {
            Log.i(TAG, msg);
        }
    }

    public static void logInfo(String msg) {
        logInfo(null, msg);
    }

    public static void logError(Object tag, String msg) {
        if (!logMode) {
            return;
        }

        if (tag != null) {
            if (tag instanceof String) {
                Log.e(TAG, tag + ": " + msg);
            } else {
                Log.e(TAG, tag.getClass().getSimpleName() + ": " + msg);
            }
        } else {
            Log.e(TAG, msg);
        }
    }

    public static void logWarn(Object tag, String msg) {
        if (!logMode) {
            return;
        }

        if (tag != null) {
            if (tag instanceof String) {
                Log.v(TAG, tag + ": " + msg);
            } else {
                Log.v(TAG, tag.getClass().getSimpleName() + ": " + msg);
            }
        } else {
            Log.v(TAG, msg);
        }
    }
}
