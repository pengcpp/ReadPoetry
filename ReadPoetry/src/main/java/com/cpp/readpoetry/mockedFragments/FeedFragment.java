package com.cpp.readpoetry.mockedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.PortfolioData;
import com.cpp.readpoetry.view.percentlayout.PercentHeaderView;
import com.cpp.readpoetry.view.percentlayout.PercentRelativeLayout;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Three. on 2015/3/9.
 */
public class FeedFragment extends Fragment {

    private Context mContext;

    private View mView;

    private PercentHeaderView headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.feed_fragment_main, container, false);

        mContext = getActivity();

        initView();
        return mView;
    }

    private void initView() {
        headerView = new PercentHeaderView(mContext);
        ((ViewGroup) mView).addView(headerView);
        headerView.setPortfolioData(new PortfolioData());
    }


}
