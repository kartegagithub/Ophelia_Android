package com.kartega.ophelia.ea_utilities.views;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kartega.ophelia.ea_utilities.tools.ViewTools;

import java.lang.ref.WeakReference;


/**
 * Created by Ahmet Kılıç on 8.02.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class FocusableCoordinator extends CoordinatorLayout {

    private boolean onTouchClearsFocus;
    private WeakReference<Activity> activityWeakReference;

    public FocusableCoordinator(Context context) {
        super(context);
        init();
    }

    public FocusableCoordinator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusableCoordinator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        onTouchClearsFocus = true;
    }

    public void setActivityWeakReference(Activity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    public void disableOnTouchClearsFocus() {
        onTouchClearsFocus = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onTouchClearsFocus)
                    clearFocus();
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    public void clearFocus() {
        requestFocus();
        if (activityWeakReference != null && activityWeakReference.get() != null)
            ViewTools.hideSoftKeyboard(activityWeakReference.get());
    }
}
