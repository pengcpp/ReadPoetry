package com.cpp.readpoetry.util;


import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Methods {


    /*
    *
    */
    public static boolean isAppOnForeground(Context context) {
        String pkgName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Returns a list of application processes that are running on the device
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // importance:
            // The relative importance level that the system places
            // on this process.
            // May be one of IMPORTANCE_FOREGROUND, IMPORTANCE_VISIBLE,
            // IMPORTANCE_SERVICE, IMPORTANCE_BACKGROUND, or IMPORTANCE_EMPTY.
            // These constants are numbered so that "more important" values are
            // always smaller than "less important" values.
            // processName:
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(pkgName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    /**
     * 显示Toast
     *
     * @param text
     */
    public static void showToast(final CharSequence text) {
        showToast(text, false, true);
    }

    /**
     * @param text
     * @param lengthLong 是否较长时间显示
     */
    public static void showToast(final CharSequence text, final boolean lengthLong) {
        if (text != null) {
            showToast(text, lengthLong, true);
        }
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
     * 直接应用资源Id显示Toast
     *
     * @param resId
     */
    public static void showToast(final int resId) {
        showToast(resId, false, true);
    }

    public static void showToast(int resId, boolean lengthLong) {
        String text = AppInfo.getAppContext().getString(resId);
        if (TextUtils.isEmpty(text))
            showToast(text, lengthLong);
    }

    /**
     * @param resId
     * @param lengthLong
     * @param show
     */
    public static void showToast(final int resId, final boolean lengthLong, boolean show) {
        if (!show) {
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
     * 格式化crash log名称
     *
     * @return
     */
    public static String formatCrashlogName() {
        long timestamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyyMMdd-HHmmss", Locale.getDefault());
        String timeStr = formatter.format(timestamp);
        // crash_12345_20140702-173537
        return "crash_" + timeStr + "_readpoety.txt";
    }


    /*
     * double数字转换成0.00的形式
     */
    public static String doubleFormat(double count) {
        if (count >= 0 && count < 0.01)
            return "0.00";
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(count);
    }

    /**
     * 货币数量千分位分隔符添加
     *
     * @param count 货币数量
     * @return String
     */
    public static String currencyFormat(double count) {
        if (count >= 0 && count < 0.001)
            return "0.00";
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###,##0.00");
        return decimalFormat.format(count);
    }

}
