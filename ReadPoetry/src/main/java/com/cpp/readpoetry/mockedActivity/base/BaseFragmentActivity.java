package com.cpp.readpoetry.mockedActivity.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import com.cpp.readpoetry.mockedActivity.TerminalActivity;
import com.cpp.readpoetry.util.ActivityStack;
import com.cpp.readpoetry.util.AppInfo;
import com.cpp.readpoetry.util.Methods;

/**
 * FragmentActivity基础类
 */
public class BaseFragmentActivity extends FragmentActivity {

    private static final String TAG = "BaseFragmentActivity";

    // 顶层fragment名
    public static String topFragmentName = "";

    /**
     * 返回键监听
     * true: 标示返回键被消化了, 不需要Activity再进行处理;
     * false: 表示Activity自己去处理
     */
    public interface IOnBackPressedListener {
        boolean onBackPressed();
    }

    /**
     * keyDown监听
     */
    public interface IOnKeyDownListener {
        boolean onKeyDown(int keyCode, KeyEvent event);
    }

    private IOnBackPressedListener onBackPressedListener = null;
    private IOnKeyDownListener onKeyDownListener = null;

    // 查询当前app是否处于后台
    Runnable checkAppBackgroundTask = new Runnable() {
        @Override
        public void run() {
            if (!Methods.isAppOnForeground(BaseFragmentActivity.this)) {
                // 当前处于后台
                AppInfo.setAppInBackground(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle args) {
        super.onCreate(args);
        Log.v(TAG, this.getClass().getName() + " onCreate()");
        // 插入activity队列
        ActivityStack.getInstance().add(this);
        // 初始化屏幕参数
        AppInfo.initScreenInfo(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v(TAG, this.getClass().getName() + " onNewIntent()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, this.getClass().getName() + " onStart()");
        // 删除掉检查后台操作
        AppInfo.getUIHandler().removeCallbacks(checkAppBackgroundTask);
        if (AppInfo.isAppInBackground()) {
            // 把app处于后台复位
            AppInfo.setAppInBackground(false);
            Log.v(TAG, this.getClass().getName() + " 从后台恢复");

           /* Log.v(TAG, " ------ " + UserInfo.getInstance().isLogin() + "   ");
            if (UserInfo.getInstance().isLogin() && !UserInfo.getInstance().getUnlockActivityIsOpen()
                    && !UserInfo.getInstance().getIsExperienced()) {
                if (!TextUtils.isEmpty(UserInfo.getInstance().getCurrentPatternPassword())) {
                    Intent intent = new Intent(getApplicationContext(), UnLockPatternActivity.class);
                    intent.putExtra(UnLockPatternActivity.mOpenIntent, UnLockPatternActivity.OPEN_BACK);
                    startActivity(intent);
                }
            }*/

            // 前后台切换调用函数
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {

                    //如果app刚从后台恢复
                   /* if (Methods.needGetLoginInfo()) {
                        //同步用户信息
                        Log.d("wht", "BaseFragmentActivity");
                    }

                    if (LBSDataUpdateUtil.isNeedToUpdateLBSData()) {
                        LBSDataUpdateUtil.updateLBSData();
                    }*/
                    return null;
                }

            }.execute();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, this.getClass().getName() + " onResume()");
//		TalkManager.INSTANCE.onAppFore();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, this.getClass().getName() + " onPause()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(TAG, this.getClass().getName() + " onRestoreInstanceState()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, this.getClass().getName() + " onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, this.getClass().getName() + " onStop()");

        // 延迟3秒判断当前是否处于后台
        AppInfo.getUIHandler().postDelayed(checkAppBackgroundTask, 120 * 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, this.getClass().getName() + " onDestroy()");
        ActivityStack.getInstance().remove(this);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        topFragmentName = fragment.getClass().getSimpleName();
    }

    @TargetApi(11)
    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        topFragmentName = fragment.getClass().getSimpleName();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            if (onBackPressedListener.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onKeyDownListener != null) {
            if (onKeyDownListener.onKeyDown(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 直接插入fragment
     */
    public void replaceFragment(final Class<? extends Fragment> fragmentCls, Bundle args, boolean addToBackState) {
        TerminalActivity.showFragment(this, fragmentCls, args);
        if (!addToBackState) {
            this.finish();
        }
    }

    /**
     * 设置Activity的onBackPressed事件监听
     */
    public void setOnBackPressedListener(IOnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    /**
     * 获取当前的onBackPressed事件监听
     */
    public IOnBackPressedListener getOnBackPressedListener() {
        return this.onBackPressedListener;
    }

    /**
     * 设置Activity的onKeyDown事件监听
     */
    public void setOnKeyDownListener(IOnKeyDownListener onKeyDownListener) {
        this.onKeyDownListener = onKeyDownListener;
    }

    /**
     * 获取当前的onKeyDown事件监听
     */
    public IOnKeyDownListener getOnKeyDownListener() {
        return this.onKeyDownListener;
    }
}
