package com.cpp.readpoetry.util;

import android.content.res.Resources;
import android.graphics.Point;

/**
 * Created by Three. on 2015/3/4.
 *
 */
public class DisplayUtil {


    /**
     *
     * @param res
     * @return
     */
    public static Point getUserPhotoSize(Resources res) {
        int size = (int) (64.0F * res.getDisplayMetrics().density);
        return new Point(size, size);
    }
}
