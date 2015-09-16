package com.cpp.readpoetry.fragment.function;

import android.widget.ImageView;
import android.widget.SeekBar;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.view.GlassView;
import com.cpp.readpoetry.view.TopActionBar;

/**
 *  GlassView
 */
public class GlassViewFragment extends BaseFragment {

    private GlassView mTopGlassView;
    private GlassView mBottomGlassView;

    private ImageView mBgImg;
    private SeekBar mSeekBar;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_glass_main;
    }

    @Override
    protected void onInitView() {
        ((TopActionBar) containerView.findViewById(R.id.topbar)).setSeperatorShow(false);
        ((TopActionBar) containerView.findViewById(R.id.topbar)).setBackgroundImageRes(0);

        mTopGlassView = (GlassView) containerView.findViewById(R.id.top_glass_view);
        mBottomGlassView = (GlassView) containerView.findViewById(R.id.bottom_glass_view);

        mBgImg = (ImageView) containerView.findViewById(R.id.bg_img);
        mSeekBar = (SeekBar) containerView.findViewById(R.id.seek_bar);
    }

    @Override
    protected void onInitListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // allow blur radius is 0 < r <= 25
                if (progress > 0) {
                    mTopGlassView.setBlurRadius(progress);
                    mBottomGlassView.setBlurRadius(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}
