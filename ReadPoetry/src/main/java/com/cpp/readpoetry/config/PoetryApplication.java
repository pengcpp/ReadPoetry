package com.cpp.readpoetry.config;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.cpp.readpoetry.activity.base.BaseFragmentActivity;
import com.cpp.readpoetry.util.AppInfo;
import com.cpp.readpoetry.util.CrashInfoInterface;
import com.cpp.readpoetry.util.Methods;

/**
 * Created by Three. on 2015/3/5.
 */
public class PoetryApplication extends Application {

    private static final String TAG = "PoetryApplication";

    private static Application mContext;
    private static Handler mApplicationHandler = null;
    private static Thread mUiThread = null;
    ;

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

        // 初始化全局编译配置信息
        AppConfig.init(this);


        // 初始化appInfo
        AppInfo.initApp(this, AppConfig.isDebug());

        // 开始异常捕获
//        AppInfo.startCrashHandler(createCrashInfoRecorder());

        // 初始化http框架
//        HttpManager.initContext(this);

        // 初始化服务器配置
//        ServiceProvider.init(this);

        //恢复用户信息
//        UserInfo.getInstance().loadUserInfo(this);
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


    /**
     * 在crash log中记录当前程序需要记录的信息
     *
     * @return
     */
    private CrashInfoInterface createCrashInfoRecorder() {
        return new CrashInfoInterface() {
            @Override
            public String createLogName() {
                return Methods.formatCrashlogName();
            }

            @Override
            public String recordAppInfo() {
                return "fragment=" + BaseFragmentActivity.topFragmentName;
            }
        };
    }
}
