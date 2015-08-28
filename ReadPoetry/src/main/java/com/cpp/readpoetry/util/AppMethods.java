package com.cpp.readpoetry.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.cpp.readpoetry.config.AppConfig;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class AppMethods {

    private static final String TAG = "AppMethods";

    public static int computePixelsWithDensity(int dp) {
        return (int) (dp * AppInfo.density + 0.5);
    }

    public static int computePixelsTextSize(int sp) {
        return (int) (sp * AppInfo.density + 0.5);
    }

    /**
     * 是否满足最小api限制
     */
    public static boolean fitApiLevel() {
        try {
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion >= AppConfig.API_MIN_LEVEL) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean fitApiLevel(int level) {
        try {
            int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
            if (sdkVersion >= level) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 当前网络是否可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) AppInfo.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWorkInfo = manager.getActiveNetworkInfo();
        if (netWorkInfo == null || !netWorkInfo.isAvailable()) {
            if (!fitApiLevel(11)) {
                // 在一款2.3的手机上，明明wifi连接着，但getActiveNetworkInfo()返回空, 所以api小于11就不做判断
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查当前是否是wifi网络
     */
    public static boolean checkIsWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是UI线程
     */
    public static boolean isUIThread() {
        return AppInfo.getUIThread() == Thread.currentThread();
    }

    /**
     * 拷贝文件
     */
    public static boolean copyFile(String fromFilePath, String toFilePath,
                                   boolean deleteExist) {
        File toFile = new File(toFilePath);
        if (toFile.exists()) {
            if (deleteExist) {
                toFile.delete();
            } else {
                return true;
            }
        }
        try {
            File fromFile = new File(fromFilePath);
            if (!fromFile.exists()) {
                Log.e(TAG,
                        "saveImageCacheTo failed, fromFile don't exist, fromFilePath:"
                                + fromFilePath);
                return false;
            }
            if (!fromFile.canRead()) {
                Log.e(TAG,
                        "saveImageCacheTo failed, fromFile don't read, fromFilePath:"
                                + fromFilePath);
                return false;
            }

            java.io.FileInputStream fosfrom = new java.io.FileInputStream(
                    fromFilePath);
            FileOutputStream fosto = new FileOutputStream(
                    toFilePath);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            // 关闭数据流
            fosfrom.close();
            fosto.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将input流转为byte数组，自动关闭
     */
    public static byte[] toByteArray(InputStream input) {
        if (input == null) {
            return null;
        }
        ByteArrayOutputStream output = null;
        byte[] result = null;
        try {
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            result = output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            closeQuietly(input);
            closeQuietly(output);
        }
        return result;
    }

    /**
     * 关闭InputStream
     */
    public static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 关闭InputStream
     */
    public static void closeQuietly(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (Exception e) {
        }
    }

    /*
    *
    * 判断当前应用是不是在前台
    */
    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Returns a list of application processes that are running on the device
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            // importance:
            // The relative importance level that the system places
            // on this process.
            // May be one of IMPORTANCE_FOREGROUND, IMPORTANCE_VISIBLE,
            // IMPORTANCE_SERVICE, IMPORTANCE_BACKGROUND, or IMPORTANCE_EMPTY.
            // These constants are numbered so that "more important" values are
            // always smaller than "less important" values.
            // processName:
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(AppConfig.PKG_NAME)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMethods(View view) {
        InputMethodManager imm = (InputMethodManager) view
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    /**
     * 显示软键盘
     */
    public static void showSoftInputMethods(View view) {
        InputMethodManager imm = (InputMethodManager) view
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * debug模式下Toast提示
     */
    public static void showToastInDebug(final CharSequence text) {
        if (AppInfo.isDebuging()) {
            Runnable update = new Runnable() {
                public void run() {
                    Toast.makeText(AppInfo.getAppContext(), text, Toast.LENGTH_SHORT).show();
                }
            };
            AppInfo.getUIHandler().post(update);
        }
    }

    /**
     * 显示Toast
     */
    public static void showToast(final CharSequence text) {
        showToast(text, false, true);
    }

    public static void showToast(final CharSequence text, final boolean lengthLong, boolean show) {
        if (!show) {
            return;
        }

        Runnable update = new Runnable() {
            public void run() {
                AppInfo.getGlobalToast().setText(text);
                AppInfo.getGlobalToast().setDuration(lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                AppInfo.getGlobalToast().show();
            }
        };
        AppInfo.getUIHandler().post(update);
    }

    /**
     * 显示Toast
     */
    public static void showToast(final int resId) {
        showToast(resId, false, true);
    }

    public static void showToast(final int resId, final boolean lengthLong, boolean show) {
        if (show == false) {
            return;
        }

        Runnable update = new Runnable() {
            public void run() {
                AppInfo.getGlobalToast().setText(resId);
                AppInfo.getGlobalToast().setDuration(lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                AppInfo.getGlobalToast().show();
            }
        };
        AppInfo.getUIHandler().post(update);
    }

    /**
     * 获取crash log
     */
    public static String defaultCrashlogName() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyyMMdd-HHmmss");
        String timeStr = formatter.format(timestamp);
        String logName = "crash" + "-" + timeStr + ".txt";
        return logName;
    }

    /**
     * 保存crash log
     * 保存路径为/ExternalStorageDirectory/renren_log/crash-yyyy-MM-dd_HH-mm-ss.txt
     */
    public static void logCrashOnFile(Context context, Throwable ex, String dirPath, CrashInfoInterface infoInterface) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        // 系统信息
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                printWriter.append("\r\n" + pi.versionName);
                printWriter.append("\r\nversionCode=" + pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e("logCrashOnFile", "Error while collect package info", e);
        }

        // 记录activity栈信息
        Stack<Activity> activityStack = ActivityStack.getInstance().getStackCopy();
        if (activityStack != null) {
            String stackString = "";
            for (Activity activity : activityStack) {
                stackString += activity.getClass().getSimpleName();
                stackString += "   ->   ";
            }
            printWriter.append("\r\nactivity:" + stackString);
        }

        if (infoInterface != null) {
            String appInfo = infoInterface.recordAppInfo();
            if (!TextUtils.isEmpty(appInfo)) {
                printWriter.append("\r\n" + appInfo);
            }
        }

        // 记录crash时间
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());
        String timeStr = formatter.format(timestamp);
        printWriter.append("\r\ncrashTime=" + timeStr);
        // 以下两行无用的记录,是为了匹配crash log分析工具的格式
        printWriter.append("\r\nbuildTime=<unknown>");
        printWriter.append("\r\nfromid=<unknown>");
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                printWriter.append("\r\n" + field.getName() + ": "
                        + field.get(null));
            } catch (Exception e) {
                Log.e("logCrashOnFile", "Error while collect crash info", e);
            }
        }

        String result = info.toString();
        printWriter.close();
        try {
            String logName = "";
            if (infoInterface != null) {
                logName = infoInterface.createLogName();
            }
            if (TextUtils.isEmpty(logName)) {
                logName = defaultCrashlogName();
            }
            logOnFile(result, dirPath, logName);
        } catch (Exception e) {
            Log.e("logCrashOnFile", "an error occured while writing report file...", e);
        }
    }

    /**
     * 记录log到文件
     */
    public static void logOnFile(String log) {
        logOnFile(log, getCacheDirs("log"), "log.txt");
    }

    public static void logOnFile(String log, String dirPath, String fileName) {
        Log.e("logOnFile", log);
        if (!AppInfo.isDebuging()) {
            return;
        }

        FileOutputStream fos = null;
        try {
            File filePath = new File(dirPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.length() > 100 * 1000) {
                try {
                    file.delete();
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String a = "\r\n" + log;
            fos = new FileOutputStream(file, true);
            fos.write(a.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos = null;
            }
        }
    }

    /**
     * 该方法根据传入的文件名字在人人的应用的文件夹下面创建新的文件夹
     */
    public static String getCacheDirs(String dirName) {
        File externalCache = AppInfo.getAppContext().getExternalCacheDir();
        if (externalCache != null) {
            File cacheImg = new File(externalCache, dirName);
            if (!cacheImg.exists()) {
                cacheImg.mkdirs();
            }

            if (cacheImg.canRead() && cacheImg.canWrite()) {
                return cacheImg.getAbsolutePath();
            }
        }

        File innerCache = AppInfo.getAppContext().getCacheDir();
        if (innerCache != null) {
            File cacheImg = new File(innerCache, dirName);
            if (!cacheImg.exists()) {
                cacheImg.mkdirs();
            }

            if (cacheImg.canRead() && cacheImg.canWrite()) {
                return cacheImg.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 该方法根据传入的文件名字在人人的应用的文件夹下面创建新的文件夹
     */

    public static String getFileDirs(String dirName) {
        File externalCache = AppInfo.getAppContext().getExternalFilesDir(null);
        if (externalCache != null) {
            File cacheImg = new File(externalCache, dirName);
            if (!cacheImg.exists()) {
                cacheImg.mkdirs();
            }

            if (cacheImg.canRead() && cacheImg.canWrite()) {
                return cacheImg.getAbsolutePath();
            }
        }

        File innerCache = AppInfo.getAppContext().getFilesDir();
        if (innerCache != null) {
            File cacheImg = new File(innerCache, dirName);
            if (!cacheImg.exists()) {
                cacheImg.mkdirs();
            }

            if (cacheImg.canRead() && cacheImg.canWrite()) {
                return cacheImg.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 获取下载目录
     */
    public static String getAppDownloadDirs() {
        File downloadDirs = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            downloadDirs = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
        if (downloadDirs != null) {
            if (!downloadDirs.exists()) {
                downloadDirs.mkdirs();
            }
            if (downloadDirs.canRead() && downloadDirs.canWrite()) {
                return downloadDirs.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 在手机上打开文件
     */
    public static void installApk(File apkFile) {
        if (apkFile == null || !apkFile.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
       /* 可调用getMIMEType()来取得文件MimeType */
        String type = "application/vnd.android.package-archive";
       /* 设置intent的file与MimeType */
        intent.setDataAndType(Uri.fromFile(apkFile), type);
        AppInfo.getAppContext().startActivity(intent);
    }

    /**
     * 获取MAC地址
     */
    public static String getLocalMacAddress() {
        String mac = "000000";
        WifiManager wifi = (WifiManager) AppInfo.getAppContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                mac = info.getMacAddress();
            }
        }
        return mac;
    }

    /**
     * 外部存储是否可用（是否装有sd卡）
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 是否支持该intent的处理
     */
    public static boolean canHandleIntent(Intent intent) {
        final PackageManager packageManager = AppInfo.getAppContext().getPackageManager();
        List<ResolveInfo> supportList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return supportList != null && supportList.size() > 0;
    }

    /**
     * 判断是否在接或者拨打电话
     */
    public static boolean isPhoneInUse(Context context) {
        if (context != null) {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return manager != null && manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK;
        }
        return false;
    }

    /**
     * 判断当前电话是否空闲
     */
    public static boolean isTelephoneIDLE(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephonyManager.getCallState();
        return state == TelephonyManager.CALL_STATE_IDLE;
    }
}
