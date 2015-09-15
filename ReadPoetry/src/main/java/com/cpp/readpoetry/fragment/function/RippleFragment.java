package com.cpp.readpoetry.fragment.function;

import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.view.RippleBackground;

/**
 * Ripple
 */
public class RippleFragment extends BaseFragment {

    RippleBackground rippleBackground;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_ripple_main;
    }

    @Override
    protected void onInitView() {
        rippleBackground = (RippleBackground) containerView.findViewById(R.id.content);
    }

    @Override
    protected void onInitListener() {
        containerView.findViewById(R.id.centerImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.startRippleAnimation();
            }
        });
    }

}
