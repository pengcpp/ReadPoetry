package com.cpp.readpoetry.view.carousel;

import android.view.animation.AnimationUtils;

/**
 * This class encapsulates rotation.  The duration of the rotation
 * can be passed in the constructor and specifies the maximum time that
 * the rotation animation should take.  Past this time, the rotation is
 * automatically moved to its final stage and computeRotationOffset()
 * will always return false to indicate that scrolling is over.
 */
public class Rotator {

    private int mMode;
    private float mStartAngle;
    private float mCurrAngle;

    private long mStartTime;
    private long mDuration;

    private float mDeltaAngle;  //δ

    private boolean mFinished;

    private float mCoeffVelocity = 0.05f;
    private float mVelocity; //v

    private static final int DEFAULT_DURATION = 250;
    private static final int SCROLL_MODE = 0;
    private static final int FLING_MODE = 1;

    private final float mDeceleration = 240.0f;


    /**
     * Create a Scroller with the specified interpolator. If the interpolator is
     * null, the default (viscous) interpolator will be used.
     */
    public Rotator() {
        mFinished = true;
    }

    /**
     * Returns whether the scroller has finished scrolling.
     *
     * @return True if the scroller has finished scrolling, false otherwise.
     */
    public final boolean isFinished() {
        return mFinished;
    }

    /**
     * Force the finished field to a particular value.
     *
     * @param finished The new finished value.
     */
    public final void forceFinished(boolean finished) {
        mFinished = finished;
    }

    /**
     * Returns how long the scroll event will take, in milliseconds.
     *
     * @return The duration of the scroll in milliseconds.
     */
    public final long getDuration() {
        return mDuration;
    }

    /**
     * Returns the current X offset in the scroll.
     *
     * @return The new X offset as an absolute distance from the origin.
     */
    public final float getCurrAngle() {
        return mCurrAngle;
    }


    /**
     * @return The original velocity less the deceleration. Result may be
     * negative.
     * @hide Returns the current velocity.
     */
    public float getCurrVelocity() {
        return mCoeffVelocity * mVelocity - mDeceleration * timePassed() /*2000.0f*/;
    }

    /**
     * Returns the start X offset in the scroll.
     *
     * @return The start X offset as an absolute distance from the origin.
     */
    public final float getStartAngle() {
        return mStartAngle;
    }


    /**
     * Returns the time elapsed since the beginning of the scrolling.
     *
     * @return The elapsed time in milliseconds.
     */
    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - mStartTime);
    }

    /**
     * Extend the scroll animation. This allows a running animation to scroll
     * further and longer.
     */
    public void extendDuration(int extend) {
        int passed = timePassed();
        mDuration = passed + extend;
        mFinished = false;
    }

    /**
     * Stops the animation. Contrary to {@link #forceFinished(boolean)},
     * aborting the animating cause the scroller to move to the final x and y
     * position
     *
     * @see #forceFinished(boolean)
     */
    public void abortAnimation() {
        mFinished = true;
    }


    /**
     * Call this when you want to know the new location.  If it returns true,
     * the animation is not yet finished.  loc will be altered to provide the
     * new location.
     */
    public boolean computeAngleOffset() {
        if (mFinished) {
            return false;
        }

        long systemClock = AnimationUtils.currentAnimationTimeMillis();
        long timePassed = systemClock - mStartTime;

        if (timePassed < mDuration) {
            switch (mMode) {
                case SCROLL_MODE:

                    float sc = (float) timePassed / mDuration;
                    mCurrAngle = mStartAngle + Math.round(mDeltaAngle * sc);
                    break;

                case FLING_MODE:

                    float timePassedSeconds = timePassed / 1000.0f;
                    float distance;

                    if (mVelocity < 0) {
                        distance = mCoeffVelocity * mVelocity * timePassedSeconds -
                                (mDeceleration * timePassedSeconds * timePassedSeconds / 2.0f);
                    } else {
                        distance = -mCoeffVelocity * mVelocity * timePassedSeconds -
                                (mDeceleration * timePassedSeconds * timePassedSeconds / 2.0f);
                    }

                    mCurrAngle = mStartAngle - Math.signum(mVelocity) * Math.round(distance);

                    break;
            }
            return true;
        } else {
            mFinished = true;
            return false;
        }
    }

    public void startRotate(float startAngle, float dAngle, int duration) {
        mMode = SCROLL_MODE;
        mFinished = false;
        mDuration = duration;
        mStartTime = AnimationUtils.currentAnimationTimeMillis();
        mStartAngle = startAngle;
        mDeltaAngle = dAngle;
    }

    public void startRotate(float startAngle, float dAngle) {
        startRotate(startAngle, dAngle, DEFAULT_DURATION);
    }


    /**
     * Start scrolling based on a fling gesture. The distance travelled will
     * depend on the initial velocity of the fling.
     *
     * @param velocityAngle Initial velocity of the fling (X) measured in pixels per second.
     */
    public void fling(float velocityAngle) {

        mMode = FLING_MODE;
        mFinished = false;

        float velocity = velocityAngle;

        mVelocity = velocity;
        mDuration = (int) (1000.0f * Math.sqrt(2.0f * mCoeffVelocity *
                Math.abs(velocity) / mDeceleration));

        mStartTime = AnimationUtils.currentAnimationTimeMillis();
    }


}
