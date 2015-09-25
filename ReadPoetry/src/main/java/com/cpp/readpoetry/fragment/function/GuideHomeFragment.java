package com.cpp.readpoetry.fragment.function;

import android.view.View;
import com.cpp.readpoetry.activity.TerminalActivity;
import com.cpp.readpoetry.data.FuncItemData;
import com.cpp.readpoetry.fragment.base.BaseFuncListFragment;
import com.cpp.readpoetry.fragment.guide.ColorAnimFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Guide Some...
 */
public class GuideHomeFragment extends BaseFuncListFragment {

    @Override
    protected String setTitle() {
        return "Guide";
    }

    @Override
    protected List<FuncItemData> itemDataList() {

        List<FuncItemData> itemDataList = new ArrayList<>();
        FuncItemData data;

        // ColorAnimFragment
        data = new FuncItemData();
        data.title = "ColorAnim";
        data.showHeaderView = true;
        data.showDividingLine = false;
        data.itemType = 1;
        data.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TerminalActivity.showFragment(context, ColorAnimFragment.class, null);
            }
        };
        itemDataList.add(data);

        return itemDataList;
    }
}
