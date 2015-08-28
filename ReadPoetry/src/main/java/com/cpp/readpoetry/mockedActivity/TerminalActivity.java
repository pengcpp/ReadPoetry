package com.cpp.readpoetry.mockedActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.mockedActivity.base.BaseFragmentActivity;
import com.cpp.readpoetry.mockedFragments.base.BaseFragment;
import com.cpp.readpoetry.util.ActivityStack;
import com.cpp.readpoetry.util.AppInfo;

import java.util.List;

/**
 * 公用的Activity，用来承载fragment
 */
public class TerminalActivity extends BaseFragmentActivity {

    private static final String TAG = "TerminalActivity";
    public static final String ARG_BOOL_BACK_TO_MAIN = "arg_bool_back_to_main"; // 需要返回MainActivity

    // 内部参数
    private static final String INNER_ARG_FRAGMENT_CLASS_NAME = "arg_fragment_class_name";
    private static final String INNER_ARG_FRAHMENT_ARGS = "arg_fragment_args";

    /**
     * 新建WrapIntent
     *
     * @param context
     * @param cls
     * @param args
     */
    public static WrapIntent newWrapIntent(final Context context, final Class<? extends Fragment> cls, final Bundle args) {
        return new WrapIntent(context, cls, args, TerminalActivity.class);
    }

    /**
     * 打开fragment
     *
     * @param context
     * @param cls
     * @param args
     */
    public static void showFragment(final Context context,
                                    final Class<? extends Fragment> cls, final Bundle args) {
        newWrapIntent(context, cls, args).show();
    }

    /**
     * 打开fragment
     *
     * @param context
     * @param cls
     * @param args
     * @param isRewriteOnBack 是否重写onBackProgress方法
     */
    public static void showFragment(final Context context, final Class<? extends Fragment> cls,
                                    final Bundle args, boolean isRewriteOnBack) {
        Bundle bundle = new Bundle();
        if (args != null) {
            bundle.putAll(args);
        }
        bundle.putBoolean("isRewriteOnBack", isRewriteOnBack);
        newWrapIntent(context, cls, bundle).show();
    }

    /**
     * 打开fragment
     *
     * @param context
     * @param cls
     * @param args
     * @param requestCode
     */
    public static void showFragmentForResult(final Context context,
                                             final Class<? extends Fragment> cls, final Bundle args,
                                             final int requestCode) {
        newWrapIntent(context, cls, args).showForResult(requestCode);
    }

    public static void showFragmentForResult(final Context context,
                                             final Class<? extends Fragment> cls, final Bundle args,
                                             final int requestCode, boolean isRewriteOnback) {
        Bundle bundle = new Bundle();
        if (args != null) {
            bundle.putAll(args);
        }
        bundle.putBoolean("isRewriteOnBack", isRewriteOnback);
        newWrapIntent(context, cls, bundle).showForResult(requestCode);
    }


    /**
     * 打开fragment（通过fragment打开, 为的是从fragment.onActivityForResult()接收回调,兵重写onbackprogress）
     *
     * @param fragment
     * @param cls
     * @param args
     * @param requestCode
     */
    public static void showFragmentForResult(final Fragment fragment,
                                             final Class<? extends Fragment> cls, final Bundle args,
                                             final int requestCode) {
        newWrapIntent(fragment.getActivity(), cls, args).showForResultFromFragment(requestCode, fragment);
    }

    /**
     * Intent属性封装, 使用该类, 可增大TerminalIActivity的扩展性
     */
    public static class WrapIntent {
        private Context context;
        private Intent intent = null;

        public WrapIntent(Context context, final Class<? extends Fragment> cls,
                          Bundle args) {
        }

        public WrapIntent(Context context, final Class<? extends Fragment> cls,
                          Bundle args, final Class<?> activityclss) {
            this(context, cls.getName(), args, activityclss);
        }

        public WrapIntent(Context context, final String className, Bundle args,
                          final Class<?> activityclss) {
            if (context == null) {
                context = AppInfo.getAppContext();
            }
            this.context = context;
            intent = new Intent(context, activityclss);
            intent.putExtra(INNER_ARG_FRAGMENT_CLASS_NAME, className);
            intent.putExtra(INNER_ARG_FRAHMENT_ARGS, args);
        }

        public Intent getIntent() {
            return intent;
        }

        //
        public void show() {
            if (!(context instanceof Activity))
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        //
        public void showForResult(int requestCode) {
            if (!(context instanceof Activity))
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) context).startActivityForResult(intent, requestCode);
        }

        //
        public void showForResultFromFragment(int requestCode, Fragment fragment) {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    // 是否需要在返回的时候检查是否返回MainActivity
    private boolean backToMainActivity = false;

    /**
     * 设置是否在finish时返回MainActivity
     */
    public void setBackToMainActivity(boolean backToMainActivity) {
        this.backToMainActivity = backToMainActivity;
    }

    @Override
    protected void onCreate(Bundle args) {
        super.onCreate(args);
        setContentView(R.layout.activity_wrapper_notitle);
        initActivityArgs(args);
        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_BOOL_BACK_TO_MAIN, backToMainActivity);
    }

    private boolean mIsRewriteOnback = false;  //是否重写onBackPressed()

    @Override
    public void onBackPressed() {
        if (mIsRewriteOnback) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList != null && fragmentList.size() > 0) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof BaseFragment) {
                        (((BaseFragment) fragment)).onBackPressed();
                    }
                }
            }
            return;
        }

        // 可能从notification直接跳转到当前页面，所以在退出的时候需要回到首页
        if (backToMainActivity) {
            // 使用之后马上复原, 防止再次调用
            backToMainActivity = false;
            int size = ActivityStack.getInstance().getSize();
            if (size <= 1) {
                // actvity栈中只有一个activity，说明首页没有进入，需要跳转到首页
//                MainTabHostActivity.openHomeActivity(this);
            }
        }
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        // 可能从notification直接跳转到当前页面，所以在退出的时候需要回到首页
        if (backToMainActivity) {
            // 使用之后马上复原, 防止再次调用
            backToMainActivity = false;
            int size = ActivityStack.getInstance().getSize();
            if (size <= 1) {
                // actvity栈中只有一个activity，说明首页没有进入，需要跳转到首页
//                MainTabHostActivity.openHomeActivity(this);
            }
        }
    }

    /**
     * 初始化Activity参数
     *
     * @param args
     */
    private void initActivityArgs(Bundle args) {
        if (args != null) {
            backToMainActivity = args.getBoolean(ARG_BOOL_BACK_TO_MAIN, backToMainActivity);
        }
    }

    /**
     * 初始化fragment参数
     */
    private void initFragment() {
        Intent intent = getIntent();
        String fragmentClassName = intent.getStringExtra(INNER_ARG_FRAGMENT_CLASS_NAME);
        if (TextUtils.isEmpty(fragmentClassName)) {
            Log.e(TAG, "invalid fragment class name");
            finish();
            return;
        }
        final Fragment fragment;
        try {
            Class<? extends Fragment> fragmentCls = (Class<? extends Fragment>) getClassLoader().loadClass(fragmentClassName);
            fragment = fragmentCls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            finish();
            return;
        } catch (InstantiationException e) {
            e.printStackTrace();
            finish();
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            finish();
            return;
        }
        Bundle fragmentArgs = intent.getBundleExtra(INNER_ARG_FRAHMENT_ARGS);
        if (fragmentArgs != null) {
            mIsRewriteOnback = fragmentArgs.getBoolean("isRewriteOnback");
            fragment.setArguments(fragmentArgs);
            initActivityArgs(fragmentArgs);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null && fragmentList.size() > 0) {
            for (int i = 0; i < fragmentList.size(); i++) {
                fragmentList.get(i).onActivityResult(requestCode, resultCode, data);
            }
        }
    }
//    //是否需要点击空白地方收起键盘，默认不需要
//    private static boolean mIsHideKeybord = false;
//
//    public static boolean setHideKeybordState(boolean isHideKeybord) {
//        mIsHideKeybord = isHideKeybord;
//        return mIsHideKeybord;
//    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {

//        if (ev.getAction() == MotionEvent.ACTION_DOWN && mIsHideKeybord) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            AppInfo.getUIHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    View view = getCurrentFocus();
                    if (shouldHideKeyboard(view, ev)) {
                        hideKeyboard(view.getWindowToken());
                    }
                }
            }, 50);

        }

        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (null != this.getCurrentFocus() && this.getCurrentFocus().getWindowToken() != null) {
//            Methods.showToast(getCurrentFocus().getId() + "-----");
//            Methods.showToast(getCurrentFocus().getId());
//            /**
//             * 点击空白位置 隐藏软键盘
//             */
//            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//        }
//        return super.onTouchEvent(event);
//    }

    private boolean shouldHideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] l = {0, 0};
            view.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + view.getHeight(),
                    right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideKeyboard(final IBinder token) {
        if (token != null) {
            AppInfo.getUIHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }, 0);
        }
    }
}
