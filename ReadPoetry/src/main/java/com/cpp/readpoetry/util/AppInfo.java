package com.cpp.readpoetry.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.util.List;

/**
 * application信息保存类, 需要在第一时间初始化该类
 */
public class AppInfo {

    private static AppInfo instance = null;

    private Application app; // 当前app实例
    private Thread uiThread; // 当前app启动的ui线程
    private boolean appInBackground = true; // app处于后台
    private boolean debuging = false; // 当前是debug模式
    private Handler uiHandler = null; // 全局ui线程handler
    private Toast globalToast = null; // 全局的toast实例

    public static String IMEI = ""; // 设备标示IMEI
    public static String mac = ""; // mac地址
    public static String operator = ""; // 移动网络操作码
    public static String versionName = ""; // 版本号
    public static String screen = ""; // 屏幕信息
    public static float density;// 屏幕密度（0.75 / 1.0 / 1.5）
    public static int screenResolution; // 屏幕分辨率
    public static int screenWidthForPortrait; // 屏幕宽度(竖屏模式下)
    public static int screenHeightForPortrait; // 屏幕高度(竖屏模式下)
    public static boolean isLogInByPattern = true;   //记录用户是否设置了手势解锁设成true产品需求

    /**
     * 初始化函数, 必须在application.onCreate()的第一时间进行初始化操作
     */
    public synchronized static void initApp(Application app, boolean debug) {
        if (instance == null) {
            instance = new AppInfo(app, debug);
            instance.initDeviceInfo();
        }
    }

    /**
     * 启动crash捕获
     */
    public static void startCrashHandler(CrashInfoInterface infoInterface) {
        CrashHandler.getInstance().init(instance.app, infoInterface);
    }

    /**
     * 获取crash log文件列表
     */
    public static List<String> getCrashFiles() {
        return CrashHandler.getInstance().getCrashLogFiles();
    }

    /**
     * 初始化屏幕信息, 必须在activity.onCreate()第一时间进行初始化
     * 此函数真实只会执行一次, 所有在每个Activity.onCreate()都调用此函数不会有多大开销
     */

    private AppInfo(Application app, boolean debug) {
        this.app = app;
        this.debuging = debug;
        this.uiThread = Thread.currentThread();
        this.uiHandler = new Handler();
        this.globalToast = Toast.makeText(app, "", Toast.LENGTH_SHORT);
    }

    /**
     * @return application context
     */
    public static Context getAppContext() {
        return instance.app;
    }

    /**
     * 当前app是否处于后台
     *
     * @return
     */
    public static boolean isAppInBackground() {
        return instance.appInBackground;
    }

    /**
     * 设置当前app的前后台状态
     *
     * @param backgroundEnable
     */
    public static void setAppInBackground(boolean backgroundEnable) {
        instance.appInBackground = backgroundEnable;
    }

    /**
     * 是否是调试
     *
     * @return
     */
    public static boolean isDebuging() {
        return instance.debuging;
    }

    /**
     * @return ui thread
     */
    public static Thread getUIThread() {
        return instance.uiThread;
    }

    /**
     * @return ui handler
     */
    public static Handler getUIHandler() {
        return instance.uiHandler;
    }

    /**
     * @return global toast
     */
    public static Toast getGlobalToast() {
        return instance.globalToast;
    }

    /**
     * 初始化一些设备信息
     */
    private void initDeviceInfo() {
        TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();
        mac = AppMethods.getLocalMacAddress();
        if (TextUtils.isEmpty(IMEI)) {
            IMEI = mac;
        }
        operator = tm.getSimOperator();
        try {
            PackageManager packageManager = app.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(app.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化屏幕信息, 必须在activity.onCreate()第一时间进行初始化
     * 此函数真实只会执行一次, 所有在每个Activity.onCreate()都调用此函数不会有多大开销
     *
     */
    public static void initScreenInfo(Activity activity) {
        if (density != 0) {
            return;
        }
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5 / 2.0）
        screen = "" + metric.widthPixels + "*" + metric.heightPixels;
        screenResolution = metric.widthPixels * metric.heightPixels;
        if (metric.heightPixels >= metric.widthPixels) {
            screenWidthForPortrait = metric.widthPixels;
            screenHeightForPortrait = metric.heightPixels;
        } else {
            screenWidthForPortrait = metric.heightPixels;
            screenHeightForPortrait = metric.widthPixels;
        }
    }

    /**
     * @return application context
     */
    public static Context getContext() {
        return instance.app;
    }
}
