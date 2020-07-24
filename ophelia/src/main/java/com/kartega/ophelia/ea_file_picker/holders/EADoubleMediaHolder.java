package com.kartega.ophelia.ea_file_picker.holders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.helper.PickerUtil;
import com.kartega.ophelia.ea_file_picker.helper.VideoRequestHandler;
import com.kartega.ophelia.ea_file_picker.interfaces.PickerInformationListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_file_picker.objects.DoubleMedia;
import com.kartega.ophelia.ea_file_picker.objects.medias.VideoFile;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerClickListener;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Ahmet Kılıç on 15.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class EADoubleMediaHolder extends BaseHolder {

    private FrameLayout frameImage1, frameImage2;
    private ImageView ivImage1, ivImage2;
    private CheckBox cbImage1, cbImage2;
    private View cover1, cover2;
    private TextView tvDuration1,tvDuration2;
    private PickerInformationListener pickerInformationListener;

    /**
     * Constructor of base holder
     *
     * @param itemView view of recycler child item
     */
    public EADoubleMediaHolder(View itemView) {
        super(itemView);
        frameImage1 = itemView.findViewById(R.id.ly_row_image_first);
        frameImage2 = itemView.findViewById(R.id.ly_row_image_second);

        ivImage1 = itemView.findViewById(R.id.iv_image_first);
        ivImage2 = itemView.findViewById(R.id.iv_image_second);

        cbImage1 = itemView.findViewById(R.id.cb_image_first);
        cbImage2 = itemView.findViewById(R.id.cb_image_second);

        tvDuration1 = itemView.findViewById(R.id.tv_image_duration_first);
        tvDuration2 = itemView.findViewById(R.id.tv_image_duration_second);

        cover1 = itemView.findViewById(R.id.bg_cover_check_box_first);
        cover2 = itemView.findViewById(R.id.bg_cover_check_box_second);
    }

    @Override
    public void loadItems(Context context, Object object, int position, final EARecyclerClickListener eaRecyclerClickListener) {
        if (context instanceof PickerInformationListener)
            this.pickerInformationListener = (PickerInformationListener) context;
        else {
            throw new IllegalStateException("Context must implement the PickerInformationListener!");
        }

        if (object instanceof DoubleMedia) {

            final BaseFile item1 = ((DoubleMedia) object).getFirstItem();
            final BaseFile item2 = ((DoubleMedia) object).getSecondItem();

            Picasso picassoInstance = new Picasso
                    .Builder(context.getApplicationContext())
                    .addRequestHandler(new VideoRequestHandler())
                    .build();

            if (item1 != null) {
                cover1.setVisibility(item1.isSelected() ? View.VISIBLE : View.GONE);
                frameImage1.setVisibility(View.VISIBLE);
                cbImage1.setChecked(item1.isSelected());
                if (pickerInformationListener.getLimit() == 1)
                    cbImage1.setVisibility(View.GONE);
                else
                    cbImage1.setVisibility(View.VISIBLE);

                if (item1 instanceof VideoFile){
                    tvDuration1.setVisibility(View.VISIBLE);
                    tvDuration1.setText(PickerUtil.getDurationString(((VideoFile) item1).getDuration()));
                }else
                    tvDuration1.setVisibility(View.GONE);

                if (item1 instanceof VideoFile) {
                    picassoInstance
                            .load(VideoRequestHandler.SCHEME_VIDEO + ":" + item1.getPath())
                            .fit()
                            .centerCrop()
                            .into(ivImage1);
                } else
                    Picasso.get()
                            .load(new File(item1.getPath()))
                            .fit()
                            .centerCrop()
                            .into(ivImage1);

                if (eaRecyclerClickListener != null)
                    frameImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pickerInformationListener.isLimitAvailable() || item1.isSelected()) {
                                item1.setSelected(!item1.isSelected());
                                cover1.setVisibility(item1.isSelected() ? View.VISIBLE : View.GONE);
                                cbImage1.setChecked(item1.isSelected());
                                eaRecyclerClickListener.onEARecyclerItemClick(item1);
                            } else
                                pickerInformationListener.showLimitReachedError();
                        }
                    });

            } else {
                frameImage1.setVisibility(View.INVISIBLE);
                frameImage1.setOnClickListener(null);
            }

            if (item2 != null) {
                cover2.setVisibility(item2.isSelected() ? View.VISIBLE : View.GONE);
                frameImage2.setVisibility(View.VISIBLE);
                cbImage2.setChecked(item2.isSelected());
                if (pickerInformationListener.getLimit() == 1)
                    cbImage2.setVisibility(View.GONE);
                else
                    cbImage2.setVisibility(View.VISIBLE);

                if (item2 instanceof VideoFile){
                    tvDuration2.setVisibility(View.VISIBLE);
                    tvDuration2.setText(PickerUtil.getDurationString(((VideoFile) item2).getDuration()));
                }else
                    tvDuration2.setVisibility(View.GONE);

                if (item2 instanceof VideoFile) {
                    picassoInstance
                            .load(VideoRequestHandler.SCHEME_VIDEO + ":" + item2.getPath())
                            .fit()
                            .centerCrop()
                            .into(ivImage2);
                } else
                    Picasso.get()
                            .load(new File(item2.getPath()))
                            .fit()
                            .centerCrop()
                            .into(ivImage2);

                if (eaRecyclerClickListener != null)
                    frameImage2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (pickerInformationListener.isLimitAvailable() || item2.isSelected()) {
                                item2.setSelected(!item2.isSelected());
                                cover2.setVisibility(item2.isSelected() ? View.VISIBLE : View.GONE);
                                cbImage2.setChecked(item2.isSelected());
                                eaRecyclerClickListener.onEARecyclerItemClick(item2);
                            } else
                                pickerInformationListener.showLimitReachedError();
                        }
                    });
            } else {
                frameImage2.setVisibility(View.INVISIBLE);
                frameImage2.setOnClickListener(null);
            }
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.ea_picker_row_image;
    }

    @Override
    public int getViewType() {
        return EAPickerCodes.RECYCLER_TYPE_DOUBLE_MEDIA;
    }
}
