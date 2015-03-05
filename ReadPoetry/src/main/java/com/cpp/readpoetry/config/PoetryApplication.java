package com.cpp.readpoetry.config;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Three. on 2015/3/5.
 */
public class PoetryApplication extends Application{

    private static final String TAG = "PoetryApplication";

    private static Application mContext;
    private static Handler mApplicationHandler = null;
    private static Thread mUiThread = null;;

    public static Application getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        if (mApplicationHandler == null) {
            mApplicationHandler = new Handler(Looper.getMainLooper());
        }
        if (mUiThread == null) {
            mUiThread = Thread.currentThread();
        }

        AppConfig.init(this);
    }

    public static Handler getApplicationHandler() {
        if (mApplicationHandler == null) {
            Looper.prepare();
            mApplicationHandler = new Handler(Looper.getMainLooper());
            Looper.loop();
        }
        return mApplicationHandler;
    }

    public static Thread getUIThread() {
        return mUiThread;
    }

}
