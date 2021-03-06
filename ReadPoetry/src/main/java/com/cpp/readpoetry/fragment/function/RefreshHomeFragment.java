package com.cpp.readpoetry.fragment.function;

import android.view.View;
import com.cpp.readpoetry.activity.TerminalActivity;
import com.cpp.readpoetry.data.FuncItemData;
import com.cpp.readpoetry.fragment.base.BaseFuncListFragment;
import com.cpp.readpoetry.fragment.refresh.RentalsStyleFragment;
import com.cpp.readpoetry.fragment.refresh.StoreHouseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Refresh
 */
public class RefreshHomeFragment extends BaseFuncListFragment {

    @Override
    protected String setTitle() {
        return "Refresh";
    }

    @Override
    protected List<FuncItemData> itemDataList() {

        List<FuncItemData> itemDataList = new ArrayList<>();
        FuncItemData data;

        // PercentLayoutFragment
        data = new FuncItemData();
        data.title = "StoreHouse";
        data.showHeaderView = true;
        data.showDividingLine = true;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, StoreHouseFragment.class, null);
            }
        };
        itemDataList.add(data);

        // CarouselFragment
        data = new FuncItemData();
        data.title = "RentalsStyle";
        data.showDividingLine = false;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, RentalsStyleFragment.class, null);
            }
        };
        itemDataList.add(data);

        return itemDataList;
    }

}
