package com.cpp.readpoetry.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by Three. on 2015/9/25.
 *
 * Solve findViewById... V.f
 */
public class V {

    /**
     * activity.findViewById()
     */
    public static <T extends View> T f(Activity context, int id) {
        return (T) context.findViewById(id);
    }

    /**
     * rootView.findViewById()
     */
    public static <T extends View> T f(View rootView, int id) {
        return (T) rootView.findViewById(id);
    }
}
