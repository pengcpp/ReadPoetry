package com.cpp.readpoetry.fragment.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cpp.readpoetry.util.LogUtils;

/**
 *  BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    private final String TAG = "BaseFragment";

    public Activity context;

    public View containerView;

//    protected RenrenProgressDialog mProgressDialog;

    protected boolean isViewCreated = false;

    /* 预先配置 */
    protected void onPreConfigured() {
    }

    /* 生成通用主文件布局 */
    protected abstract int onSetContainerViewId();

    /* 初始化页面控件 */
    protected abstract void onInitView();

    /* 初始化监听*/
    protected void onInitListener() {
    }

    /* 注册广播接收者*/
    protected void onRegisterReceiver() {
    }

    protected void unRegisterReceiver() {
    }

    /* 加载网络数据 */
    protected void onLoadNetworkData() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        setMenuVisibility(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i("openFragment: " + this.getClass().getSimpleName());
        context = getActivity();
        onPreConfigured();
        containerView = inflater.inflate(onSetContainerViewId(), container, false);
//        mProgressDialog = new RenrenProgressDialog(context);
//        mProgressDialog.setCancelable(false);
        onInitView();
        onInitListener();
        onRegisterReceiver();

        return containerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onLoadNetworkData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        afterActivityCreated();
        super.onActivityCreated(savedInstanceState);
        onFragmentShow();
    }

   /* protected void showProgressBar() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                        mProgressDialog.show();
                    }
                }
            });
        }
    }*/

   /* protected void dismissProgressBar() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.d(TAG, "requestCode:" + requestCode + " resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onFragmentShow() {
        LogUtils.d(TAG, "onFragmentShow: " + this.getClass().getName());
    }

    public void onFragmentHide() {
        LogUtils.d(TAG, "onFragmentHide: " + this.getClass().getName());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        LogUtils.d(TAG, "onHiddenChanged: " + this.getClass().getName());

        if (!hidden) {
            onFragmentShow();
        } else {
            onFragmentHide();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtils.d(TAG, "onResume: " + this.getClass().getName());
        super.onResume();
        // onFragmentResume();
    }

    protected void afterActivityCreated() {
        isViewCreated = true;
    }

    @Override
    public void onPause() {
        LogUtils.d(TAG, "onPause: " + this.getClass().getName());
        super.onPause();
        // onFragmentPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        LogUtils.d(TAG, "onDestroyView: " + this.getClass().getName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtils.d(TAG, "onDestroy: " + this.getClass().getName());
        unRegisterReceiver();
        /*if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }*/
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void finish() {
        getActivity().finish();
    }

}
