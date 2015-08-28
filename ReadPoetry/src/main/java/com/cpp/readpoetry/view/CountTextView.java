package com.cpp.readpoetry.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import com.nineoldandroids.animation.ObjectAnimator;

import java.text.DecimalFormat;

/**
 * 数字动画增长TextView
 */
public class CountTextView extends TextView {

    int duration = 1000;
    float number;

    private int showType = 0;
    public static final int PERCENT_TYPE = 1;
    public static final int CURRENCY_TYPE = 2;
    public static final int CURRENCY_TYPE_DECIMAL_4 = 3;

    public CountTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public CountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public CountTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
//        setTypeface(typeface);
    }

    public void showNumberWithAnimation(float number) {
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number", 0, number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
        if (showType == PERCENT_TYPE) {
            setText(new DecimalFormat("####").format(number) + "%");
        } else if (showType == CURRENCY_TYPE) {
            setText(new DecimalFormat("#,###,###,##0.00").format(number));
        } else if (showType == CURRENCY_TYPE_DECIMAL_4) {
            setText(new DecimalFormat("#,###,###,##0.0000").format(number));
        } else {
            setText(new DecimalFormat("####").format(number));
        }
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
