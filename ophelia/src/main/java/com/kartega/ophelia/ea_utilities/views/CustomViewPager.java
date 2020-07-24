package com.kartega.ophelia.ea_utilities.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.kartega.ophelia.ea_slider.FixedSpeedScroller;

import java.lang.reflect.Field;


/**
 * Created by Ahmet Kılıç on 7.02.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class CustomViewPager extends ViewPager {
    private boolean swipeActive;
    private int BLOCK_TIME_MILLIS;
    private int scrollDuration;
    private FixedSpeedScroller scroller;

    public CustomViewPager(Context context) {
        super(context);
        initMyPager();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyPager();
    }

    private void initMyPager() {
        enableSwipe();
        setBlockTimeMillis(100);
        setScrollDuration(300);

        OnPageChangeListener onPageChangeListener = new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                disableSwipeTemporary();
            }
        };
        addOnPageChangeListener(onPageChangeListener);
        addCustomScroller();
    }

    private void addCustomScroller() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new FixedSpeedScroller(getContext(), new LinearInterpolator());
            scroller.setFixedDuration(scrollDuration);
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set page scrolling duration in millis
     *
     * @param scrollDuration duration in millis
     */
    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
        if (scroller != null)
            this.scroller.setFixedDuration(scrollDuration);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return swipeActive && super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return swipeActive && super.onTouchEvent(ev);
    }

    public void enableSwipe() {
        this.swipeActive = true;
    }

    public void disableSwipe() {
        this.swipeActive = false;
    }

    public void disableSwipeTemporary() {
        if (BLOCK_TIME_MILLIS != 0) {
            disableSwipe();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    enableSwipe();
                }
            }, BLOCK_TIME_MILLIS);
        }
    }

    /**
     * The swipe function is temporarily disabled to prevent rapid swipe.
     *
     * @param blockTimeMillis disable duration in millis
     */
    public void setBlockTimeMillis(int blockTimeMillis) {
        BLOCK_TIME_MILLIS = blockTimeMillis;
    }
}
