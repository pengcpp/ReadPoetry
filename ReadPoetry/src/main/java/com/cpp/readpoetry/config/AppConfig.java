package com.cpp.readpoetry.config;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.cpp.readpoetry.BuildConfig;

/**
 * Created by Three. on 2015/3/5.
 */
public class AppConfig {

    public static final int API_MIN_LEVEL = 8;

    public final static String PKG_NAME = "com.cpp.readpoetry";

    private static Application appContext;

    /**
     * 是否是Debug模式（debug包）
     */
    public static Boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 版本号（字符串）
     */
    private static String versionName = "";

    /**
     * 版本号（数值型）
     */
    private static long versionCode;

    static void init(Application application) {
        appContext = application;
        initConfigData();
    }

    private static void initConfigData() {
        try{
            PackageInfo pi = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        }catch(PackageManager.NameNotFoundException e){
        }
    }

    public static String getVersionName() {
        return versionName;
    }

    public static long getVersionCode() {
        return versionCode;
    }



}
