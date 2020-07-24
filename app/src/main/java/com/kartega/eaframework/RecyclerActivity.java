package com.kartega.eaframework;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.kartega.eaframework.holder.TempHolder;
import com.kartega.eaframework.object.TempObject;
import com.kartega.ophelia.EABaseActivity;
import com.kartega.ophelia.ea_recycler.EARecyclerHelper;
import com.kartega.ophelia.ea_recycler.EARecyclerView;
import com.kartega.ophelia.ea_recycler.EASwipeAndDragBuilder;
import com.kartega.ophelia.ea_recycler.enums.MovementType;
import com.kartega.ophelia.ea_recycler.enums.SwipeDirection;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EACustomDrawListener;
import com.kartega.ophelia.ea_recycler.interfaces.EACustomDrawSuperListener;
import com.kartega.ophelia.ea_recycler.interfaces.EADragListener;
import com.kartega.ophelia.ea_recycler.interfaces.EAMovementCompleteListener;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerOperationsListener;
import com.kartega.ophelia.ea_recycler.interfaces.EASwipeListener;
import com.kartega.ophelia.ea_utilities.enums.LogType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class RecyclerActivity extends EABaseActivity implements EARecyclerOperationsListener {
    private EARecyclerHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        EARecyclerView recyclerView = findViewById(R.id.recycler_view);

        helper = new EARecyclerHelper(this, recyclerView);

        new EASwipeAndDragBuilder()
                .setDefaultSwipeDirs(SwipeDirection.RIGHT)
                .enableDragAndDrop(new EADragListener() {
                    @Override
                    public void onAdapterPositionSwap(int fromPosition, int toPosition) {
                        makeLog(LogType.DEBUG, "Position swap => " + String.valueOf(fromPosition) + " , " + String.valueOf(toPosition));
                    }
                })
                .enableSwipe(new EASwipeListener() {
                    @Override
                    public void onRecyclerItemSwiped(BaseHolder holder, int position, int swipeDir) {
                        if (swipeDir == SwipeDirection.LEFT)
                            makeLog(LogType.DEBUG, "Item swiped to LEFT");
                        else if (swipeDir == SwipeDirection.RIGHT)
                            makeLog(LogType.DEBUG, "Item swiped to RIGHT");

                        ((TempObject) helper.getObjectList().get(position)).setText("This item is swiped " + (swipeDir == SwipeDirection.LEFT ? "LEFT" : "RIGHT"));
                        helper.validateItem(position);
                    }
                })
                .enableCustomDrawing(new EACustomDrawListener() {
                    @Override
                    public void onRecyclerItemDraw(EACustomDrawSuperListener superCallback, @NonNull Canvas c, @NonNull EARecyclerView recyclerView, @NonNull BaseHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                        if (actionState == MovementType.SWIPE) {
                            if (dX == 0) {
                                ((TempObject) helper.getObjectList().get(viewHolder.getAdapterPosition())).setTranslationX((int) 0);
                                ((TempHolder) viewHolder).getTextView().setTranslationX(0);
                            } else {
                                int margin = ((TempObject) helper.getObjectList().get(viewHolder.getAdapterPosition())).getTranslationX();
                                if (margin < 160) {
                                    margin = (int) dX;
                                    if (margin > 160)
                                        margin = 160;
                                    ((TempObject) helper.getObjectList().get(viewHolder.getAdapterPosition())).setTranslationX(margin);
                                    ((TempHolder) viewHolder).getTextView().setTranslationX(margin);
                                }
                            }
                        } else
                            superCallback.onRecyclerItemDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        makeLog(LogType.DEBUG, "Item Draw ---> DX: " + String.valueOf(dX) + " , DY:" + String.valueOf(dY) + " , --> " + (actionState == MovementType.SWIPE ? "SWIPE" : "DRAG"), false);
                    }
                })
                .setMovementCompleteListener(new EAMovementCompleteListener() {
                    @Override
                    public void onMovementComplete(@MovementType int type) {
                        makeLog(LogType.DEBUG, "Drag Completed --> " + (type == MovementType.SWIPE ? "SWIPE" : "DRAG"));
                    }
                })
                .attach(helper);

        helper.addNewHolder(TempHolder.class);
        helper.insertAll(getList(), false);
    }

    @Override
    public void saveLogIfNeeded(int type, String message) {

    }

    private List<TempObject> getList() {
        List<TempObject> list = new ArrayList<>();
        for (int i = helper.getObjectList().size() - 1; i < helper.getObjectList().size() + 25; i++) {
            list.add(new TempObject("Item " + String.valueOf(i + 1)));
        }
        return list;
    }

    @Override
    public void onProgressRequired(boolean show) {

    }

    @Override
    public void onNoDataViewRequired(boolean show) {

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(() -> helper.insertAll(getList(), true), 2000);
    }
}
