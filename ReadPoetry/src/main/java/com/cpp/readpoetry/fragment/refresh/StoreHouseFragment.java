package com.cpp.readpoetry.fragment.refresh;

import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.DisplayUtil;
import com.cpp.readpoetry.view.ptrrefresh.PtrDefaultHandler;
import com.cpp.readpoetry.view.ptrrefresh.PtrFrameLayout;
import com.cpp.readpoetry.view.ptrrefresh.PtrHandler;
import com.cpp.readpoetry.view.ptrrefresh.header.StoreHouseHeader;

/**
 *
 */
public class StoreHouseFragment extends BaseFragment implements PtrHandler {

    final String TAG = "PtrRefresh";

    private PtrFrameLayout ptrFrameLayout;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_ptrrefresh_storehorse_main;
    }

    @Override
    protected void onInitView() {
        ptrFrameLayout = (PtrFrameLayout) containerView.findViewById(R.id.fragment_ptr_home_ptr_frame);
        addRefreshHeader(ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(this);

        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh(true);
            }
        }, 100);
    }


    // 刷新 Header
    public void addRefreshHeader(PtrFrameLayout ptrFrameLayout) {
        StoreHouseHeader header = new StoreHouseHeader(context);
        header.setPadding(0, DisplayUtil.dp2px(context, 15), 0, DisplayUtil.dp2px(context, 15));
        header.initWithString("Google");
        ptrFrameLayout.setDurationToCloseHeader(1000);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);

        /*PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(context);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);*/
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 1500);
    }
}
