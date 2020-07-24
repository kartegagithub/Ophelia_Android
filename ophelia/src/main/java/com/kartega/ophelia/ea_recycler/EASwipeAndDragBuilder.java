package com.kartega.ophelia.ea_recycler;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.kartega.ophelia.ea_recycler.interfaces.EACustomDrawListener;
import com.kartega.ophelia.ea_recycler.interfaces.EADragListener;
import com.kartega.ophelia.ea_recycler.interfaces.EAMovementCompleteListener;
import com.kartega.ophelia.ea_recycler.interfaces.EASwipeListener;

/**
 * Created by Ahmet Kılıç on 31.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class EASwipeAndDragBuilder {
    private EADragListener dragListener;
    private EASwipeListener swipeListener;
    private EACustomDrawListener drawListener;
    private EAMovementCompleteListener completeListener;
    private boolean isSwipeActive;
    private boolean isDragActive;

    private int defaultSwipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
    private int defaultDragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

    public EASwipeAndDragBuilder() {
        dragListener = null;
        drawListener = null;
        swipeListener = null;
    }

    /**
     * Change the default drag directions.
     * Default value is : ItemTouchHelper.UP | ItemTouchHelper.DOWN
     *
     * @param defaultDragDirs drag direction value
     */
    public EASwipeAndDragBuilder setDefaultDragDirs(int defaultDragDirs) {
        this.defaultDragDirs = defaultDragDirs;
        return this;
    }

    /**
     * Change the default swipe directions.
     * Default value is : ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
     *
     * @param defaultSwipeDirs swipe direction value
     */
    public EASwipeAndDragBuilder setDefaultSwipeDirs(int defaultSwipeDirs) {
        this.defaultSwipeDirs = defaultSwipeDirs;
        return this;
    }

    public EASwipeAndDragBuilder enableDragAndDrop() {
        isDragActive = true;
        return this;
    }

    public EASwipeAndDragBuilder enableDragAndDrop(EADragListener dragListener) {
        isDragActive = true;
        this.dragListener = dragListener;
        return this;
    }

    public EASwipeAndDragBuilder enableSwipe(EASwipeListener swipeListener) {
        isSwipeActive = true;
        this.swipeListener = swipeListener;
        return this;
    }

    public EASwipeAndDragBuilder enableCustomDrawing(EACustomDrawListener drawListener) {
        this.drawListener = drawListener;
        return this;
    }

    public EASwipeAndDragBuilder setMovementCompleteListener(EAMovementCompleteListener completeListener) {
        this.completeListener = completeListener;
        return this;
    }

    public void attach(EARecyclerHelper helper) {
        int swipeDirs = 0;
        if (isSwipeActive)
            swipeDirs = defaultSwipeDirs;

        TouchHandler handler = new TouchHandler(defaultDragDirs, swipeDirs, helper);

        handler.setDragActive(isDragActive);

        handler.setDragListener(dragListener);
        handler.setSwipeListener(swipeListener);
        handler.setDrawListener(drawListener);
        handler.setCompleteListener(completeListener);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(handler);
        itemTouchHelper.attachToRecyclerView(helper.getRecyclerView());
    }

}
