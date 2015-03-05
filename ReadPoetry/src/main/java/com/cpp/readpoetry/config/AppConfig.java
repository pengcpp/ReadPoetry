package com.cpp.readpoetry.config;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Three. on 2015/3/5.
 */
public class AppConfig {

    private static Application appContext;

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
