package com.kartega.ophelia.ea_file_picker.holders;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.interfaces.PickerInformationListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_file_picker.objects.Directory;
import com.kartega.ophelia.ea_file_picker.objects.medias.ImageFile;
import com.kartega.ophelia.ea_file_picker.objects.DoubleFolder;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerClickListener;
import com.kartega.ophelia.ea_utilities.tools.Calculator;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Ahmet Kılıç on 18.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class EAFolderHolder extends BaseHolder {

    private PickerInformationListener pickerInformationListener;
    private FrameLayout frameFolder1, frameFolder2;
    private ImageView ivFolder1, ivFolder2;
    private TextView tvName1, tvName2, tvCount1, tvCount2;

    /**
     * Constructor of base holder
     *
     * @param itemView view of recycler child item
     */
    public EAFolderHolder(View itemView) {
        super(itemView);
        frameFolder1 = itemView.findViewById(R.id.ly_row_folder_first);
        frameFolder2 = itemView.findViewById(R.id.ly_row_folder_second);

        ivFolder1 = itemView.findViewById(R.id.iv_folder_first);
        ivFolder2 = itemView.findViewById(R.id.iv_folder_second);

        tvName1 = itemView.findViewById(R.id.tv_folder_name_first);
        tvName2 = itemView.findViewById(R.id.tv_folder_name_second);

        tvCount1 = itemView.findViewById(R.id.tv_folder_count_first);
        tvCount2 = itemView.findViewById(R.id.tv_folder_count_second);
    }

    @Override
    public void loadItems(Context context, final Object object, int position, final EARecyclerClickListener eaRecyclerClickListener) {
        if (context instanceof PickerInformationListener)
            this.pickerInformationListener = (PickerInformationListener) context;

        if (object instanceof DoubleFolder) {

            final Directory item1 = ((DoubleFolder) object).getDirectoryFirst();
            final Directory item2 = ((DoubleFolder) object).getDirectorySecond();

            if (item1 != null) {
                frameFolder1.setVisibility(View.VISIBLE);
                tvName1.setText(item1.getName());

                tvCount1.setText(getCountStringFor(item1));

                if (ObjectUtils.arrayIsNotEmpty(item1.getFiles())) {
                    BaseFile baseFile = (BaseFile) item1.getFiles().get(0);

                    int padding = 0;
                    if (baseFile instanceof ImageFile) {
                        Picasso.get()
                                .load(new File(baseFile.getPath()))
                                .fit()
                                .centerCrop()
                                .into(ivFolder1);
                    } else {
                        padding = (int) Calculator.pxFromDp(context, 16);
                        ivFolder1.setImageResource(R.drawable.ic_folder_24dp);
                    }
                    ivFolder1.setPadding(padding, padding, padding, padding);
                } else {
                    int padding = (int) Calculator.pxFromDp(context, 16);
                    ivFolder1.setPadding(padding, padding, padding, padding);
                    ivFolder2.setImageResource(R.drawable.ic_folder_24dp);
                }

                if (eaRecyclerClickListener != null)
                    frameFolder1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eaRecyclerClickListener.onEARecyclerItemClick(item1);
                        }
                    });
            } else {
                frameFolder1.setVisibility(View.INVISIBLE);
                frameFolder1.setOnClickListener(null);
            }


            if (item2 != null) {
                frameFolder2.setVisibility(View.VISIBLE);
                tvName2.setText(item2.getName());

                tvCount2.setText(getCountStringFor(item2));
                //tvCount2.setText(String.valueOf(item2.getFiles().size()));

                if (ObjectUtils.arrayIsNotEmpty(item2.getFiles())) {
                    BaseFile baseFile = (BaseFile) item2.getFiles().get(0);

                    int padding = 0;
                    if (baseFile instanceof ImageFile) {
                        Picasso.get()
                                .load(new File(baseFile.getPath()))
                                //.load("file://"+item2.getFiles().get(0))
                                //.config(Bitmap.Config.RGB_565)
                                .fit()
                                .centerCrop()
                                .into(ivFolder2);
                    } else {
                        padding = (int) Calculator.pxFromDp(context, 16);
                        ivFolder2.setImageResource(R.drawable.ic_folder_24dp);
                    }
                    ivFolder2.setPadding(padding, padding, padding, padding);
                } else {
                    int padding = (int) Calculator.pxFromDp(context, 16);
                    ivFolder2.setPadding(padding, padding, padding, padding);
                    ivFolder2.setImageResource(R.drawable.ic_folder_24dp);
                }

                if (eaRecyclerClickListener != null)
                    frameFolder2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eaRecyclerClickListener.onEARecyclerItemClick(item2);
                        }
                    });

            } else {
                frameFolder2.setVisibility(View.INVISIBLE);
                frameFolder2.setOnClickListener(null);
            }
        }
    }

    private String getCountStringFor(Directory directory) {
        if (directory == null || !ObjectUtils.arrayIsNotEmpty(directory.getFiles()))
            return "0";
        else if (pickerInformationListener.getLimit() == 1)
            return String.valueOf(directory.getFiles().size());

        int count = 0;
        if (ObjectUtils.arrayIsNotEmpty(directory.getFiles())) {
            for (BaseFile baseFile : pickerInformationListener.getSelectedItems()) {
                if (directory.getFiles().contains(baseFile))
                    count++;
            }
        }
        return count + " / " + directory.getFiles().size();
    }

    @Override
    public int getLayoutID() {
        return R.layout.ea_picker_row_folder;
    }

    @Override
    public int getViewType() {
        return EAPickerCodes.RECYCLER_TYPE_FOLDER;
    }
}
