package com.cpp.readpoetry.fragment.refresh;

import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.DisplayUtil;
import com.cpp.readpoetry.view.ptrrefresh.PtrFrameLayout;
import com.cpp.readpoetry.view.ptrrefresh.PtrHandler;
import com.cpp.readpoetry.view.ptrrefresh.header.RentalsSunHeaderView;

/**
 * RentalsStyle Bug... 需要先向上滑下再下下拉
 */
public class RentalsStyleFragment extends BaseFragment implements PtrHandler {

    private PtrFrameLayout ptrFrameLayout;

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_ptrrefresh_rentals_main;
    }

    @Override
    protected void onInitView() {

        ptrFrameLayout = (PtrFrameLayout) containerView.findViewById(R.id.material_style_ptr_frame);
        addRefreshHeader(ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(this);

        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh(true);
            }
        }, 100);
    }

    public void addRefreshHeader(final PtrFrameLayout ptrFrameLayout) {

        final RentalsSunHeaderView header = new RentalsSunHeaderView(context);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dp2px(context, 15), 0, DisplayUtil.dp2px(context, 10));
        header.setUp(ptrFrameLayout);
        ptrFrameLayout.setDurationToCloseHeader(1000);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        return true;
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
