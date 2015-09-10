package com.cpp.readpoetry.data;

import android.view.View;

/**
 * Function Item
 */
public class FuncItemData {

    public String title;
    public String value;
    public int res;
    public int messageCount;

    public int itemType;

    public boolean hasNewMsg = false;
    public boolean showDividingLine = true;
    public boolean showHeaderView = false;

    public View.OnClickListener onClickListener = null;
}
