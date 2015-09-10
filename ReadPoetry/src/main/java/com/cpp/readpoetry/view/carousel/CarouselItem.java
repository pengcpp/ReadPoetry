package com.cpp.readpoetry.view.carousel;

import android.content.Context;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cpp.readpoetry.R;
import com.cpp.readpoetry.data.CarouselItemData;
import com.cpp.readpoetry.util.Methods;

/**
 * Card item...
 */
public class CarouselItem extends FrameLayout implements Comparable<CarouselItem> {

    private RelativeLayout topLayout;
    private TextView fundNameText;
    private TextView fundTypeText;
    private TextView percentText;
    private TextView fundMoneyText;

    private int index;
    private float currentAngle;
    private float itemX;
    private float itemY;
    private float itemZ;
    private boolean drawn;

    // It's needed to find screen coordinates
    private Matrix mCIMatrix;

    public CarouselItem(Context context) {

        super(context);

        this.setLayoutParams(new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(context).inflate(R.layout.carousel_card_item, this, true);

        topLayout = (RelativeLayout) findViewById(R.id.top_layout);
        fundNameText = (TextView) findViewById(R.id.fund_name_text);
        fundTypeText = (TextView) findViewById(R.id.fund_type_text);
        percentText = (TextView) findViewById(R.id.percent_text);
        fundMoneyText = (TextView) findViewById(R.id.fund_count_text);

        setVisibility(VISIBLE);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setCurrentAngle(float currentAngle) {
        this.currentAngle = currentAngle;
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public int compareTo(CarouselItem another) {
        return (int) (another.itemZ - this.itemZ);
    }

    public void setItemX(float x) {
        this.itemX = x;
    }

    public float getItemX() {
        return itemX;
    }

    public void setItemY(float y) {
        this.itemY = y;
    }

    public float getItemY() {
        return itemY;
    }

    public void setItemZ(float z) {
        this.itemZ = z;
    }

    public float getItemZ() {
        return itemZ;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean isDrawn() {
        return drawn;
    }

    Matrix getCIMatrix() {
        return mCIMatrix;
    }

    void setCIMatrix(Matrix mMatrix) {
        this.mCIMatrix = mMatrix;
    }

    public void setData(CarouselItemData data) {
        topLayout.setBackgroundColor(data.bgColor);
        fundNameText.setText(data.fundName);
        fundTypeText.setText(data.type);
        percentText.setText(data.percent + "%");
        fundMoneyText.setText(Methods.currencyFormat(data.fundMoney / 100));
    }
}
