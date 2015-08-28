package com.cpp.readpoetry.util;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.cpp.readpoetry.R;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * crash异常log捕获
 * 捕获到的log会保存到sdcard文件里
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static final int MAX_UPLOAD_LOGFILE_COUNT = 5; // 每次最多上传5个crash log
    private static CrashHandler instance;
    private Context context;
    private CrashInfoInterface appInfoInterface;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler defaultHandler;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context, CrashInfoInterface appInfoInterface) {
        this.context = context;
        this.appInfoInterface = appInfoInterface;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        if (!handleException(arg1) && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(arg0, arg1);
        } else {
            // 等待1s 退出进程
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Intent intent = new Intent();
                Class cl = Class.forName("com.cpp.readpoetry.mockedActivity.mockedActivity.WelcomeActivity");
                intent.setClass(context, cl);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                int nPid = android.os.Process.myPid();
                android.os.Process.killProcess(nPid);
            }
        }
    }

    /**
     * 获取crash log文件, 每次最多取MAX_UPLOAD_LOGFILE_COUNT数目的文件
     */
    public List<String> getCrashLogFiles() {
        int uploadFileCount = 0;
        String crashDirs = AppMethods.getCacheDirs("crash");
        if (!TextUtils.isEmpty(crashDirs)) {
            try {
                List<String> fileList = new ArrayList<>();
                File cacheDirsFile = new File(crashDirs);
                File[] allFiles = cacheDirsFile.listFiles();
                if (allFiles != null) {
                    for (File logFile : allFiles) {
                        fileList.add(logFile.getAbsolutePath());
                        uploadFileCount++;
                        if (uploadFileCount >= MAX_UPLOAD_LOGFILE_COUNT) {
                            // 达到文件上传上限
                            break;
                        }
                    }
                }
                return fileList;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 处理异常
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        AppMethods.logCrashOnFile(AppInfo.getAppContext(), ex, AppMethods.getCacheDirs("crash"), appInfoInterface);

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, context.getResources().getString(R.string.crash_notice), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        return true;
    }
}
