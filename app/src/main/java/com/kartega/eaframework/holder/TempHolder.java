package com.kartega.eaframework.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kartega.eaframework.R;
import com.kartega.eaframework.object.TempObject;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerClickListener;


public class TempHolder extends BaseHolder {

    private TextView textView;
    private LinearLayout linearLayout;

    public TempHolder(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.row_main);
        textView = itemView.findViewById(R.id.tv_row);
    }

    @Override
    public void loadItems(Context context, Object object, int position, EARecyclerClickListener eARecyclerClickListener) {
        textView.setText(object.toString());
        int x = ((TempObject) object).getTranslationX();
        updateTextViewMargin(x);
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void updateTextViewMargin(int marginLeft) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.leftMargin = marginLeft;
        textView.setLayoutParams(params);
        textView.setTranslationX(0);
    }

    @Override
    public int getLayoutID() {
        return R.layout.row_temp;
    }

    @Override
    public int getViewType() {
        return 10;
    }
}
