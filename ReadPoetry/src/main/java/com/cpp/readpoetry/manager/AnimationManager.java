package com.cpp.readpoetry.manager;

import android.app.Activity;
import com.cpp.readpoetry.R;

/**
 * Created by Three. on 2015/3/5.
 */
public class AnimationManager {

    private static final String TAG = "AnimationManager";

    public static enum ActivityAnimationType {
        SYSTEM_DEFAULT,
        FADE_DEFAULT,
        TRANSLATE_FROM_LEFT,
        TRANSLATE_FROM_RIGHT
    }

    /**
     * Activity Animation
     * <p/>
     * Call immediately after one of the flavors of startActivity or finish to
     * specify an explicit transition animation to perform next. Use 0 for no animation.
     *
     * @param activity
     * @param isEnter
     * @param animationType
     */
    public static void overridePendingTransition(Activity activity, boolean isEnter, ActivityAnimationType animationType) {
        if (activity != null) {


            int[] animationResIds = getAnimationResId(isEnter, animationType);
            if (animationResIds != null && animationResIds.length == 2) {
                activity.overridePendingTransition(animationResIds[0], animationResIds[1]);
            }
        }
    }

    private static int[] getAnimationResId(boolean isEnter, ActivityAnimationType animationType) {

        int[] animationResIds = new int[2];
        animationResIds[0] = -1;
        animationResIds[1] = -1;

        switch (animationType) {
            case SYSTEM_DEFAULT:

                break;
            case FADE_DEFAULT:
                if (isEnter) {
                    animationResIds[0] = R.anim.fade_in;
                    animationResIds[1] = R.anim.still_when_up;
                } else {
                    animationResIds[0] = R.anim.still_when_up;
                    animationResIds[1] = R.anim.fade_out;
                }
                break;
            case TRANSLATE_FROM_LEFT:
                if (isEnter) {
                    animationResIds[0] = R.anim.left_enter;
                    animationResIds[1] = R.anim.still_when_up;
                } else {
                    animationResIds[0] = R.anim.still_when_up;
                    animationResIds[1] = R.anim.left_exit;
                }
                break;
            case TRANSLATE_FROM_RIGHT:
                if (isEnter) {
                    animationResIds[0] = R.anim.right_enter;
                    animationResIds[1] = R.anim.still_when_up;
                } else {
                    animationResIds[0] = R.anim.still_when_up;
                    animationResIds[1] = R.anim.right_exit;
                }
                break;
            default:
        }
        return animationResIds;
    }
}
