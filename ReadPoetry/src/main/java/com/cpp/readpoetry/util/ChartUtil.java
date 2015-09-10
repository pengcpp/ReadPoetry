package com.cpp.readpoetry.util;

import android.graphics.Color;
import android.text.TextUtils;
import com.cpp.readpoetry.data.BasePieChartData;
import com.cpp.readpoetry.data.PieChartConfig;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

/**
 * 图表配置
 * <p/>
 * 饼状图 {@link #initPieChart}
 */
public class ChartUtil {

    private static final String TAG = "ChartUtil";

    /**
     * 饼状图初始化:
     * <p/>
     * mChart = (PieChart) containerView.findViewById(R.id.pie_chart);
     * pieChartConfig = new PieChartConfig();
     * pieChartConfig.pieRadiusPercent = 30;
     * pieChartConfig.centerText = "2015年四季度";
     * pieChartConfig.centerTextColor = Color.rgb(255, 72, 0);
     * pieChartConfig.centerTextSize = 20;
     * pieChartConfig.animationShowed = false;
     * pieChartConfig.arrayList = TestData.initBasePieChartList();
     * ChartUtil.initPieChart(mChart, pieChartConfig);
     *
     * @param pieChart
     * @param chartConfig
     */
    public static void initPieChart(PieChart pieChart, PieChartConfig chartConfig) {

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setNoDataText("");
        pieChart.setNoDataTextDescription("");

        pieChart.setDragDecelerationFrictionCoef(0);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);
        pieChart.getLegend().setEnabled(false);

        pieChart.setTransparentCircleColor(Color.TRANSPARENT);

        pieChart.setHoleRadius(100f - chartConfig.pieRadiusPercent);
        pieChart.setTransparentCircleRadius(100f - chartConfig.pieRadiusPercent);

        if (!TextUtils.isEmpty(chartConfig.centerText)) {
            pieChart.setDrawCenterText(true);
            pieChart.setCenterText(chartConfig.centerText);
            pieChart.setCenterTextColor(chartConfig.centerTextColor);
            pieChart.setCenterTextSize(chartConfig.centerTextSize);
        } else {
            pieChart.setDrawCenterText(false);
        }

        pieChart.setDrawSliceText(false);
        pieChart.setUsePercentValues(false);

        pieChart.setRotationAngle(270);
        pieChart.setRotationEnabled(false);
        pieChart.setNoDataText("");
        pieChart.highlightValues(null);

        ArrayList<BasePieChartData> arrayList = chartConfig.arrayList;

        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        for (int i = 0; i < arrayList.size(); i++) {
            xValues.add(arrayList.get(i).itemName);
            yValues.add(new Entry(arrayList.get(i).itemPercent, i));
            colors.add(arrayList.get(i).itemColor);
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);

        PieData data = new PieData(xValues, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);

        pieChart.invalidate();
        if (chartConfig.animationShowed) {
            pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        }
    }


}
