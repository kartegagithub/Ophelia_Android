package com.kartega.ophelia.ea_recycler;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kartega.ophelia.ea_recycler.enums.MovementType;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EACustomDrawListener;
import com.kartega.ophelia.ea_recycler.interfaces.EACustomDrawSuperListener;
import com.kartega.ophelia.ea_recycler.interfaces.EADragListener;
import com.kartega.ophelia.ea_recycler.interfaces.EAMovementCompleteListener;
import com.kartega.ophelia.ea_recycler.interfaces.EASwipeListener;
import com.kartega.ophelia.ea_recycler.interfaces.TouchHelperFunctions;

import java.util.Collections;

/**
 * Created by Ahmet Kılıç on 31.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
class TouchHandler extends ItemTouchHelper.SimpleCallback implements EACustomDrawSuperListener {

    private TouchHelperFunctions helperFunctions;
    private EASwipeListener swipeListener;
    private EADragListener dragListener;
    private EACustomDrawListener drawListener;
    private EAMovementCompleteListener completeListener;

    private boolean isDragActive;

    private @MovementType
    int currentMovementType;

    TouchHandler(int dragDirs, int swipeDirs, TouchHelperFunctions functions) {
        super(dragDirs, swipeDirs);
        this.helperFunctions = functions;
        swipeListener = null;
        dragListener = null;
        drawListener = null;
        completeListener = null;
    }

    void setDragActive(boolean dragActive) {
        isDragActive = dragActive;
    }

    void setSwipeListener(EASwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }

    void setDragListener(EADragListener dragListener) {
        this.dragListener = dragListener;
    }

    void setCompleteListener(EAMovementCompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    void setDrawListener(EACustomDrawListener drawListener) {
        this.drawListener = drawListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (!isDragActive)
            return false;

        final int fromPosition = viewHolder.getAdapterPosition();
        final int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(helperFunctions.onAdapterRequired().getItems(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(helperFunctions.onAdapterRequired().getItems(), i, i - 1);
            }
        }

        helperFunctions.onAdapterRequired().notifyItemMoved(fromPosition, toPosition);

        if (dragListener != null)
            dragListener.onAdapterPositionSwap(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (swipeListener != null)
            swipeListener.onRecyclerItemSwiped((BaseHolder) viewHolder, viewHolder.getAdapterPosition(), i);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        currentMovementType = actionState;
        if (drawListener != null)
            drawListener.onRecyclerItemDraw(TouchHandler.this, c, (EARecyclerView) recyclerView, (BaseHolder) viewHolder, dX, dY, actionState, isCurrentlyActive);
        else
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (completeListener != null)
            completeListener.onMovementComplete(currentMovementType);
    }

    @Override
    public void onRecyclerItemDraw(@NonNull Canvas c, @NonNull EARecyclerView recyclerView, @NonNull BaseHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
