package com.cpp.readpoetry.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.config.PoetryApplication;
import com.cpp.readpoetry.config.Variables;

/**
 * Created by Three. on 2015/3/5.
 */
public class SettingManager {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private static class SingletonCreator {
        private static final SettingManager instance = new SettingManager();
    }

    public static SettingManager getInstance() {
        return SingletonCreator.instance;
    }

    private SettingManager() {
        init(PoetryApplication.getContext());
    }

    public void init(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
        int mode = (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) ? Context.MODE_MULTI_PROCESS : Context.MODE_PRIVATE;
        mSharedPreferences = mContext.getSharedPreferences("setting", mode);
        mEditor = mSharedPreferences.edit();
    }



    // 是否接收后台消息推送
    public void setNotifyReceiveBackground(boolean isReceive) {
        mEditor.putBoolean(mContext.getString(R.string.bt_notify_receive), isReceive).commit();
    }

    public boolean isNotifyReceiveBackground() {
        return mSharedPreferences.getBoolean(mContext.getString(R.string.bt_notify_receive), true);
    }

    // 设置登录状态
    public void setLoginState(boolean login) {
        mEditor.putBoolean(Variables.user_id + mContext.getString(R.string.login_state), login).commit();
    }

    public boolean isLogin() {
        return mSharedPreferences.getBoolean(Variables.user_id + mContext.getString(R.string.login_state), false);
    }

    // 版本号 当前版本versionCode
    public void setCurrentVersion(long versionCode) {
        mEditor.putLong(mContext.getString(R.string.current_version), versionCode).commit();
    }

    public long getCurrentVersion() {
        return mSharedPreferences.getLong(mContext.getString(R.string.current_version), 0);
    }

}
