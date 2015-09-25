package com.cpp.readpoetry.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.TextView;
import com.cpp.readpoetry.util.DisplayUtil;

/**
 * Created by Three. on 2015/9/22.
 */
public class CircleBorderColorTextView extends TextView {

    private Paint mBgPaint = new Paint();
    private Paint mBgPaintInner = new Paint();

    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    public CircleBorderColorTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CircleBorderColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
        mBgPaintInner.setColor(Color.WHITE);
        mBgPaintInner.setAntiAlias(true);
    }

    public CircleBorderColorTextView(Context context) {
        super(context);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
        mBgPaintInner.setColor(Color.WHITE);
        mBgPaintInner.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max, max);
    }

    @Override
    public void setBackgroundColor(int color) {
        mBgPaint.setColor(color);
        mBgPaintInner.setColor(Color.WHITE);
    }

    public void setNotifyText(int text) {
        setText(text + "");
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(pfd);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, mBgPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2 - DisplayUtil.dp2px(getContext(), 1), mBgPaintInner);
        super.draw(canvas);
    }

}