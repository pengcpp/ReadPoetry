package com.cpp.readpoetry.manager;

import android.app.Activity;
import com.cpp.readpoetry.R;

import static com.cpp.readpoetry.util.Logger.logInfo;

/**
 * Created by Three. on 2015/3/5.
 */
public class AnimationManager {

    private static final String TAG = "AnimationManager";

    /**
     * Activity Animation Type...
     */
    public static enum ActivityAction {
        ENTER,
        EXIT,
        ENTER_WITH_LAST_EXIT
    }

    public static enum ActivityAnimationType {
        SYSTEM_DEFAULT,
        FADE_DEFAULT,

        TRANSLATE_ENTER_FROM_LEFT,
        TRANSLATE_EXIT_TO_LEFT,
        TRANSLATE_ENTER_FROM_RIGHT,
        TRANSLATE_EXIT_TO_RIGHT
    }

    /**
     * Activity Animation
     * <p/>
     * Call immediately after one of the flavors of startActivity or finish to
     * specify an explicit transition animation to perform next. Use 0 for no animation.
     *
     * @param activity
     * @param activityAction
     * @param animationType
     */
    public static void overridePendingTransition(Activity activity, ActivityAction activityAction, ActivityAnimationType animationType) {

        logInfo(TAG, "activity: " + activity.getClass().getName() + " Action: " + activityAction.name() + " Type: " + animationType.name());

        int[] animationResIds = getAnimationResId(activityAction, animationType);
        if (animationResIds != null && animationResIds.length == 2) {
            activity.overridePendingTransition(animationResIds[0], animationResIds[1]);
        }
    }

    private static int[] getAnimationResId(ActivityAction activityAction, ActivityAnimationType animationType) {

        int[] animationResIds = new int[2];
        animationResIds[0] = 0;
        animationResIds[1] = 0;


        switch (activityAction) {
            case ENTER: {
                switch (animationType) {
                    case SYSTEM_DEFAULT:
                        animationResIds[0] = android.support.v7.appcompat.R.anim.abc_slide_in_bottom;
                        break;
                    case FADE_DEFAULT:
                        animationResIds[0] = R.anim.fade_in;
                        break;
                    case TRANSLATE_ENTER_FROM_LEFT:
                        animationResIds[0] = R.anim.activity_left_enter;
                        break;
                    case TRANSLATE_ENTER_FROM_RIGHT:
                        animationResIds[0] = R.anim.activity_right_enter;
                        break;
                    default:
                }
            }
            break;
            case EXIT: {
                switch (animationType) {
                    case SYSTEM_DEFAULT:
                        animationResIds[1] = android.support.v7.appcompat.R.anim.abc_slide_out_bottom;
                        break;
                    case FADE_DEFAULT:
                        animationResIds[1] = R.anim.fade_out;
                        break;
                    case TRANSLATE_EXIT_TO_LEFT:
                        animationResIds[1] = R.anim.activity_left_exit;
                        break;
                    case TRANSLATE_EXIT_TO_RIGHT:
                        animationResIds[1] = R.anim.activity_right_enter;
                        break;
                    default:
                }
            }
            break;
            // TODO
            case ENTER_WITH_LAST_EXIT: {
                switch (animationType) {
                    case SYSTEM_DEFAULT:

                        break;
                    case FADE_DEFAULT:
                        animationResIds[0] = R.anim.still_when_up;
                        animationResIds[1] = R.anim.fade_out;
                        break;
                    case TRANSLATE_ENTER_FROM_LEFT:

                        break;
                    case TRANSLATE_ENTER_FROM_RIGHT:

                        break;
                    default:
                }
            }
            break;
            default:
        }

        return animationResIds;
    }
}
