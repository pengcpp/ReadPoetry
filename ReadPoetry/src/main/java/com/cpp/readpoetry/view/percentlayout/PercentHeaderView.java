package com.cpp.readpoetry.view.percentlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.PortfolioData;
import com.cpp.readpoetry.util.LogUtils;
import com.cpp.readpoetry.view.CountTextView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.math.BigDecimal;

/**
 * Percent Header with anim...
 * <p/>
 * Created by Three. on 2015/7/14.
 */
public class PercentHeaderView extends RelativeLayout {

    private final String TAG = "PercentHeaderView";

    protected int layoutResId = R.layout.percent_header_layout;

    private Context context;
    private LayoutInflater inflater;

    private ImageView mQuoraImage;
    private RatingBar mRatingBar;
    private TextView mRiskText;
//    private CountTextView mRateText;

    private PercentRelativeLayout parentPercentLayout;
    private PercentRelativeLayout childPercentLayout;

    private View left;
    private View top;
    private View right;
    private View bottom;

    private PortfolioData portfolioData;

    public PercentHeaderView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public PercentHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public PercentHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    void initView() {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId, this);

        mRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        mQuoraImage = (ImageView) findViewById(R.id.quora_image);
        mRiskText = (TextView) findViewById(R.id.risk_result_text);
//        mRateText = (CountTextView) findViewById(R.id.rate_value_text);

        parentPercentLayout = (PercentRelativeLayout) findViewById(R.id.percent_layout_parent);
        ObjectAnimator.ofFloat(parentPercentLayout, "rotation", 0f, 30f).setDuration(10).start();

        childPercentLayout = (PercentRelativeLayout) findViewById(R.id.percent_layout_child);
        left = findViewById(R.id.left);
        top = findViewById(R.id.top);
        right = findViewById(R.id.right);
        bottom = findViewById(R.id.bottom);

        performButtonClick();
    }

    void refreshView() {
        if (portfolioData == null)
            return;
        mRatingBar.setRating((float) portfolioData.riskScore / 2);
        mRiskText.setText(Double.toString(portfolioData.riskScore));
//        mRateText.setShowType(CountTextView.PERCENT_TYPE);
//        mRateText.showNumberWithAnimation(new BigDecimal(portfolioData.incomeRateOfYear).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
        reSizePercentLayout();
    }

    void reSizePercentLayout() {
        if (portfolioData.fundList.size() == 0 || portfolioData.fundList == null)
            return;
        int dateSize = portfolioData.fundList.size();

        switch (dateSize) {
            case 1:
                dd1();
                break;
            case 2:
                dd2();
                break;
            case 3:
                dd3();
                break;
            case 4:
                dd4();
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                dd5(dateSize);
                break;
            default:
        }

        invalidate();
        showAnimation(dateSize);
    }

    void dd1() {
        PortfolioData.FundItemData leftData = portfolioData.fundList.get(0);
        float leftProportion = 1;
        PercentRelativeLayout.LayoutParams leftLayoutParams = (PercentRelativeLayout.LayoutParams) left.getLayoutParams();
        leftLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(leftProportion, true);
        left.setLayoutParams(leftLayoutParams);
        left.setBackgroundColor(leftData.color);

        float rightProportion = 0;
        PercentRelativeLayout.LayoutParams rightLayoutParams = (PercentRelativeLayout.LayoutParams) right.getLayoutParams();
        rightLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(rightProportion, true);
        right.setLayoutParams(rightLayoutParams);
    }

    void dd2() {
        PortfolioData.FundItemData leftData = portfolioData.fundList.get(0);
        float leftProportion = (float) (leftData.proportion / 100);
        PercentRelativeLayout.LayoutParams leftLayoutParams = (PercentRelativeLayout.LayoutParams) left.getLayoutParams();
        leftLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(leftProportion, true);
        left.setLayoutParams(leftLayoutParams);
        left.setBackgroundColor(leftData.color);

        PortfolioData.FundItemData topData = portfolioData.fundList.get(1);
        float topProportion = (float) (topData.proportion / 100) / (100 - leftProportion);
        PercentRelativeLayout.LayoutParams topLayoutParams = (PercentRelativeLayout.LayoutParams) top.getLayoutParams();
        topLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(topProportion, true);
        top.setLayoutParams(topLayoutParams);
        top.setBackgroundColor(topData.color);

        float bottomProportion = 0;
        PercentRelativeLayout.LayoutParams bottomLayoutParams = (PercentRelativeLayout.LayoutParams) bottom.getLayoutParams();
        bottomLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(bottomProportion, true);
        bottom.setLayoutParams(bottomLayoutParams);
    }

    void dd3() {
        PortfolioData.FundItemData leftData = portfolioData.fundList.get(0);
        float leftProportion = (float) (leftData.proportion / 100);
        PercentRelativeLayout.LayoutParams leftLayoutParams = (PercentRelativeLayout.LayoutParams) left.getLayoutParams();
        leftLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(leftProportion, true);
        left.setLayoutParams(leftLayoutParams);
        left.setBackgroundColor(leftData.color);

        PortfolioData.FundItemData topData = portfolioData.fundList.get(1);
        float topProportion = (float) (topData.proportion / 100) / (1 - leftProportion);
        PercentRelativeLayout.LayoutParams topLayoutParams = (PercentRelativeLayout.LayoutParams) top.getLayoutParams();
        topLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(topProportion, true);
        top.setLayoutParams(topLayoutParams);
        top.setBackgroundColor(topData.color);

        PortfolioData.FundItemData rightData = portfolioData.fundList.get(2);
        float rightProportion = 1 - leftProportion;
        PercentRelativeLayout.LayoutParams rightLayoutParams = (PercentRelativeLayout.LayoutParams) right.getLayoutParams();
        rightLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(rightProportion, true);
        right.setLayoutParams(rightLayoutParams);
        right.setBackgroundColor(rightData.color);
    }

    //TODO  附加的cutAdd需要改下 具体情况具体分析... 构图时周围View margin为负
    void dd4() {

        float cutAttach = 0.04f;

        PortfolioData.FundItemData leftData = portfolioData.fundList.get(0);
        float leftProportion = (float) (leftData.proportion / 100) + cutAttach;
        PercentRelativeLayout.LayoutParams leftLayoutParams = (PercentRelativeLayout.LayoutParams) left.getLayoutParams();
        leftLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(leftProportion, true);
        left.setLayoutParams(leftLayoutParams);
        left.setBackgroundColor(leftData.color);

        PortfolioData.FundItemData topData = portfolioData.fundList.get(1);
        float topProportion = (float) (topData.proportion / 100) / (1 - leftProportion) + cutAttach;
        PercentRelativeLayout.LayoutParams topLayoutParams = (PercentRelativeLayout.LayoutParams) top.getLayoutParams();
        topLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(topProportion, true);
        top.setLayoutParams(topLayoutParams);
        top.setBackgroundColor(topData.color);

        PortfolioData.FundItemData rightData = portfolioData.fundList.get(2);
        float rightProportion = (float) (rightData.proportion / 100) / (1 - topProportion) + cutAttach;
        PercentRelativeLayout.LayoutParams rightLayoutParams = (PercentRelativeLayout.LayoutParams) right.getLayoutParams();
        rightLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(rightProportion, true);
        right.setLayoutParams(rightLayoutParams);
        right.setBackgroundColor(rightData.color);

        PortfolioData.FundItemData bottomData = portfolioData.fundList.get(3);
        float bottomProportion = 1 - topProportion;
        PercentRelativeLayout.LayoutParams bottomLayoutParams = (PercentRelativeLayout.LayoutParams) bottom.getLayoutParams();
        bottomLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(bottomProportion, true);
        bottom.setLayoutParams(bottomLayoutParams);
        bottom.setBackgroundColor(bottomData.color);
    }

    //TODO  附加的cutAdd需要改下 具体情况具体分析... 构图时周围View margin为负
    void dd5(int size) {

        float cutAttach = 0.08f;
        if (portfolioData.fundList.get(4).proportion >= 10) {
            cutAttach = 0.04f;
        }

        PortfolioData.FundItemData leftData = portfolioData.fundList.get(0);
        float leftProportion = (float) (leftData.proportion / 100) + cutAttach;
        PercentRelativeLayout.LayoutParams leftLayoutParams = (PercentRelativeLayout.LayoutParams) left.getLayoutParams();
        leftLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(leftProportion, true);
        left.setLayoutParams(leftLayoutParams);
        left.setBackgroundColor(leftData.color);
        LogUtils.logInfo(TAG, "leftProportion: " + leftProportion);

        PortfolioData.FundItemData topData = portfolioData.fundList.get(1);
        float topProportion = (float) (topData.proportion / 100) / (1 - leftProportion) + cutAttach;
        PercentRelativeLayout.LayoutParams topLayoutParams = (PercentRelativeLayout.LayoutParams) top.getLayoutParams();
        topLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(topProportion, true);
        top.setLayoutParams(topLayoutParams);
        top.setBackgroundColor(topData.color);
        LogUtils.logInfo(TAG, "topProportion: " + topProportion);

        PortfolioData.FundItemData rightData = portfolioData.fundList.get(2);
        float rightProportion = (float) (rightData.proportion / 100) / (1 - topProportion);
        PercentRelativeLayout.LayoutParams rightLayoutParams = (PercentRelativeLayout.LayoutParams) right.getLayoutParams();
        rightLayoutParams.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(rightProportion, true);
        right.setLayoutParams(rightLayoutParams);
        right.setBackgroundColor(rightData.color);
        LogUtils.logInfo(TAG, "rightProportion: " + rightProportion);

        PortfolioData.FundItemData bottomData = portfolioData.fundList.get(3);
        float bottomProportion = (float) (bottomData.proportion / 100) / (1 - topProportion) + cutAttach;
        PercentRelativeLayout.LayoutParams bottomLayoutParams = (PercentRelativeLayout.LayoutParams) bottom.getLayoutParams();
        bottomLayoutParams.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(bottomProportion, true);
        bottom.setLayoutParams(bottomLayoutParams);
        bottom.setBackgroundColor(bottomData.color);
        LogUtils.logInfo(TAG, "bottomProportion: " + bottomProportion);

        if (size == 5) {
            PortfolioData.FundItemData middleData = portfolioData.fundList.get(4);
            childPercentLayout.setBackgroundColor(middleData.color);
        }
        if (size > 5)
            addNewPercentView(size - 4);
    }

    void addNewPercentView(int newCount) {
        if (newCount == 2) {
            View view1 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams1 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams1.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f, true);
            layoutParams1.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            view1.setBackgroundColor(portfolioData.fundList.get(4).color);
            childPercentLayout.addView(view1, layoutParams1);

            View view4 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams4 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams4.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.5f, true);
            layoutParams4.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            view4.setBackgroundColor(portfolioData.fundList.get(5).color);
            childPercentLayout.addView(view4, layoutParams4);

        } else if (newCount == 3) {
            View view1 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams1 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams1.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.66f, true);
            layoutParams1.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            view1.setBackgroundColor(portfolioData.fundList.get(4).color);
            childPercentLayout.addView(view1, layoutParams1);

            View view2 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams2 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams2.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.33f, true);
            layoutParams2.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            view2.setBackgroundColor(portfolioData.fundList.get(5).color);
            childPercentLayout.addView(view2, layoutParams2);

            View view3 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams3 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams3.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.34f, true);
            layoutParams3.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            view3.setBackgroundColor(portfolioData.fundList.get(6).color);
            childPercentLayout.addView(view3, layoutParams3);

        } else if (newCount == 4) {
            View view1 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams1 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams1.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
            layoutParams1.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            view1.setBackgroundColor(portfolioData.fundList.get(4).color);
            childPercentLayout.addView(view1, layoutParams1);

            View view2 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams2 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams2.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
            layoutParams2.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            view2.setBackgroundColor(portfolioData.fundList.get(5).color);
            childPercentLayout.addView(view2, layoutParams2);

            View view3 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams3 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams3.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
            layoutParams3.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            view3.setBackgroundColor(portfolioData.fundList.get(6).color);
            childPercentLayout.addView(view3, layoutParams3);

            View view4 = new View(context);
            PercentRelativeLayout.LayoutParams layoutParams4 =
                    new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams4.getPercentLayoutInfo().widthPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(0.25f, true);
            layoutParams4.getPercentLayoutInfo().heightPercent = new PercentLayoutHelper.PercentLayoutInfo.PercentVal(1, true);
            layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            view4.setBackgroundColor(portfolioData.fundList.get(7).color);
            childPercentLayout.addView(view4, layoutParams4);
        }
    }

    void showAnimation(int size) {
        if (size <= 1) return;

        float translationX = left.getTranslationX();    //移动前的X轴方向上的坐标
        float translationY = left.getTranslationY();    //移动前的Y轴方向上的坐标
//        ObjectAnimator.ofFloat(left, "translationX", -800, translationX).setDuration(800).start();

        translationX = top.getTranslationX();
        translationY = top.getTranslationY();
        ObjectAnimator.ofFloat(top, "translationY", -800, translationY).setDuration(300).start();

        if (size < 3) return;

        translationX = bottom.getTranslationX();
        translationY = bottom.getTranslationY();
        ObjectAnimator.ofFloat(bottom, "translationX", -800, translationX).setDuration(300).start();

        if (size < 4) return;

        translationX = right.getTranslationX();
        translationY = right.getTranslationY();
        ObjectAnimator.ofFloat(right, "translationX", 800, translationY).setDuration(300).start();

        if (size < 5) return;

        translationX = childPercentLayout.getTranslationX();
        translationY = childPercentLayout.getTranslationY();
        ObjectAnimator.ofFloat(childPercentLayout, "translationY", -800, translationY).setDuration(300).start();
    }

    void performButtonClick() {
        mQuoraImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quoraImageClickListener != null) {
                    quoraImageClickListener.onQuoraImageClick(view);
                }
            }
        });
    }

    private OnQuoraImageClickListener quoraImageClickListener;

    public interface OnQuoraImageClickListener {
        void onQuoraImageClick(View view);
    }

    public void setQuoraImageClickListener(OnQuoraImageClickListener quoraImageClickListener) {
        this.quoraImageClickListener = quoraImageClickListener;
    }

    public void setPortfolioData(PortfolioData portfolioData) {
        this.portfolioData = portfolioData;
        refreshView();
    }
}
