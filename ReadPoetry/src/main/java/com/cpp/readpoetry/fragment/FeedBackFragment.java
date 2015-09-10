package com.cpp.readpoetry.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.view.CircleProgress;

/**
 * Created by Three. on 2015/4/24.
 */
public class FeedBackFragment extends Fragment {

    private Context mContext;

    private View mView;

    private CircleProgress mProgressView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_feedback_main, container, false);

        mContext = getActivity();

        initView();
        return mView;
    }

    private void initView() {

        mProgressView = (CircleProgress) mView.findViewById(R.id.progress);
        mProgressView.startAnim();


    }
}
