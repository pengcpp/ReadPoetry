package com.cpp.readpoetry.data;

import android.view.View;

/**
 * 旋转轮盘的数据
 */
public class CarouselItemData {

    public int bgColor;

    public float percent;
    public String fundName;
    public String type;
    public double fundMoney;

    public View.OnClickListener onClickListener = null;

}
