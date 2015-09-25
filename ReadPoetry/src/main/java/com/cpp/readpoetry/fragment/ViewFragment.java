package com.cpp.readpoetry.fragment;

import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.activity.TerminalActivity;
import com.cpp.readpoetry.data.FuncItemData;
import com.cpp.readpoetry.fragment.base.BaseFuncListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 各种 View
 */
public class ViewFragment extends BaseFuncListFragment {

    @Override
    protected void onInitView() {
        super.onInitView();
        containerView.findViewById(R.id.topbar).setVisibility(View.GONE);
    }

    @Override
    protected String setTitle() {
        return "View";
    }

    @Override
    protected List<FuncItemData> itemDataList() {

        List<FuncItemData> itemDataList = new ArrayList<>();
        FuncItemData data;

        // CircleView
        data = new FuncItemData();
        data.title = "CircleView";
        data.showHeaderView = true;
        data.showDividingLine = false;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, CircleViewFragment.class, null);
            }
        };
        itemDataList.add(data);


        return itemDataList;
    }

}
