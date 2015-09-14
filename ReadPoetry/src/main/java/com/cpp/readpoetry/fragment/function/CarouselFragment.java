package com.cpp.readpoetry.fragment.function;

import android.view.Gravity;
import android.view.View;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.BasePieChartData;
import com.cpp.readpoetry.data.CarouselItemData;
import com.cpp.readpoetry.data.PieChartConfig;
import com.cpp.readpoetry.data.TestData;
import com.cpp.readpoetry.fragment.base.BaseFragment;
import com.cpp.readpoetry.util.ChartUtil;
import com.cpp.readpoetry.util.LogUtils;
import com.cpp.readpoetry.view.PieChartWithCircle;
import com.cpp.readpoetry.view.carousel.Carousel;
import com.cpp.readpoetry.view.carousel.CarouselAdapter;

import java.util.ArrayList;

/**
 *
 */
public class CarouselFragment extends BaseFragment implements CarouselAdapter.OnItemClickListener,
        CarouselAdapter.OnItemSelectedListener {

    final String TAG = "CarouselFragment";

    private PieChartWithCircle mPieChart;
    private Carousel mCarousel;

    private PieChartConfig pieChartConfig;

    private ArrayList<CarouselItemData> carouselList = TestData.initCarouselList();
    public ArrayList<BasePieChartData> fundList = TestData.initPieChartList();

    @Override
    protected int onSetContainerViewId() {
        return R.layout.fragment_carousel_main;
    }

    @Override
    protected void onInitView() {

        initPieChart();
        initCarousel();
    }

    private void initPieChart() {
        mPieChart = (PieChartWithCircle) containerView.findViewById(R.id.pie_chart);
        mPieChart.setTouchEnabled(false);
        mPieChart.setNoDataText("");
        mPieChart.invalidate();

        pieChartConfig = new PieChartConfig();
        pieChartConfig.animationShowed = true;
        pieChartConfig.arrayList = fundList;
        pieChartConfig.pieRadiusPercent = 10f;
        ChartUtil.initPieChart(mPieChart, pieChartConfig);
        mPieChart.setDrawArrowEnable(true);
    }

    private void initCarousel() {
        mCarousel = (Carousel) containerView.findViewById(R.id.carousel);
        mCarousel.setGravity(Gravity.CENTER_VERTICAL);
        mCarousel.setOnItemClickListener(this);
        mCarousel.setOnItemSelectedListener(this);
        mCarousel.setData(carouselList);
        mCarousel.setSelection(0, false);
        mCarousel.requestFocus();
    }

    @Override
    public void onItemClick(CarouselAdapter<?> parent, View view, int position, long id) {
        LogUtils.i(TAG, "Carousel onItemClick position: " + position + " id:" + id);
    }

    @Override
    public void onItemSelected(CarouselAdapter<?> parent, View view, int position, long id) {
        LogUtils.i(TAG, "Carousel onItemSelected position: " + position + " id:" + id);
        mPieChart.setInnerCircleColor(mPieChart.getData().getDataSet().getColor(position), position);
    }

    @Override
    public void onNothingSelected(CarouselAdapter<?> parent) {
        LogUtils.i(TAG, "Carousel onNothingSelected: ");
    }
}
