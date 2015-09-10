package com.cpp.readpoetry.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import com.github.mikephil.charting.charts.PieChart;

/**
 * 带内圈的饼状图
 * <p/>
 * 箭头旋转动画 How to do it...
 */
public class PieChartWithCircle extends PieChart {

    private final String TAG = "PieChartWithCircle";

    private boolean drawArrow = false;

    private int innerCircleColor = Color.rgb(70, 185, 243);

    private final int circleColorWidth = 5; // Methods.dp2px(FinanceApplication.getContext(), 6);

    private final int arrowHeight = 20; // Methods.dp2px(FinanceApplication.getContext(), 20)

    private final float offset = 10f; //偏移比例

    private float[] mAnglesCenter;

    private int curPosition = 0;

    public PieChartWithCircle(Context context) {
        super(context);
    }

    public PieChartWithCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartWithCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInnerCircle(canvas);

        if (drawArrow) {
            drawArrow(canvas);
        }
    }


    @Override
    protected void calcMinMax() {
        super.calcMinMax();
        calAngleCenter();
    }


    private void calAngleCenter() {

        float[] drawAngles = getDrawAngles();
        if (drawAngles == null) {
            return;
        }
        mAnglesCenter = new float[drawAngles.length];
        float angle = 0;
        for (int cn = 0; cn < drawAngles.length; cn++) {
            mAnglesCenter[cn] = drawAngles[cn] / 2 + angle;
            angle = drawAngles[cn] + angle;
        }
    }

    private void drawInnerCircle(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(innerCircleColor);
        paint.setStrokeWidth(circleColorWidth);

        PointF c = getCenterOffsets();

        canvas.drawCircle(c.x, c.y, getRadius() * (getHoleRadius() - offset) / 100, paint);
    }


    private void drawArrow(Canvas canvas) {

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(innerCircleColor);
        paint.setStrokeWidth(circleColorWidth);

        PointF c = getCenterOffsets();
        float radius = getRadius() * (getHoleRadius() - offset) / 100;

        float x1 = c.x + (float) (radius * Math.sin(2 * Math.PI / 360 * (mAnglesCenter[curPosition] - 7)));
        float y1 = c.y - (float) (radius * Math.cos(2 * Math.PI / 360 * (mAnglesCenter[curPosition] - 7)));

        float x2 = c.x + (float) (radius * Math.sin(2 * Math.PI / 360 * (mAnglesCenter[curPosition] + 7)));
        float y2 = c.y - (float) (radius * Math.cos(2 * Math.PI / 360 * (mAnglesCenter[curPosition] + 7)));

        float x0 = c.x + (float) ((arrowHeight + radius) * Math.sin(2 * Math.PI / 360 * (mAnglesCenter[curPosition])));
        float y0 = c.y - (float) ((arrowHeight + radius) * Math.cos(2 * Math.PI / 360 * (mAnglesCenter[curPosition])));

        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x0, y0);
        path.lineTo(x2, y2);
        path.close();
        canvas.drawPath(path, paint);
    }

    public void setInnerCircleColor(int innerCircleColor, int position) {
        this.innerCircleColor = innerCircleColor;
        this.curPosition = position;
        invalidate();
    }

    public void setDrawArrowEnable(boolean enable) {
        this.drawArrow = enable;
        invalidate();
    }
}
