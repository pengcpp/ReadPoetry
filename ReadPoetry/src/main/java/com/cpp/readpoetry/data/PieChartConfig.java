package com.cpp.readpoetry.data;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * PieChart - {@link com.github.mikephil.charting.charts.PieChart}
 */
public class PieChartConfig {
    //饼状图占弧度百分比 100f-显示全图 20f-只外部20%为图中部透明空的 默认13f
    public float pieRadiusPercent = 12f;

    //Center Text
    public String centerText = "";
    public float centerTextSize = 15;
    public int centerTextColor = Color.rgb(22, 22, 22);

    //if Show Animation
    public boolean animationShowed = false;

    // Data Array
    public ArrayList<BasePieChartData> arrayList = new ArrayList<>();
}
