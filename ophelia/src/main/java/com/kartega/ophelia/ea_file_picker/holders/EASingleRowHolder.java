package com.kartega.ophelia.ea_file_picker.holders;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kartega.ophelia.R;
import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.interfaces.PickerInformationListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;
import com.kartega.ophelia.ea_recycler.interfaces.EARecyclerClickListener;
import com.kartega.ophelia.ea_utilities.tools.FileUtils;


/**
 * Created by Ahmet Kılıç on 15.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class EASingleRowHolder extends BaseHolder {

    private View ly_row;
    private TextView tvExtension, tvName;
    private CheckBox checkBox;
    private PickerInformationListener pickerInformationListener;

    /**
     * Constructor of base holder
     *
     * @param itemView view of recycler child item
     */
    public EASingleRowHolder(View itemView) {
        super(itemView);
        ly_row = itemView.findViewById(R.id.ly_row);
        tvExtension = itemView.findViewById(R.id.tv_row_file_extension);
        tvName = itemView.findViewById(R.id.tv_row_file_name);
        checkBox = itemView.findViewById(R.id.cb_file);
    }

    @Override
    public void loadItems(Context context, final Object object, int position, final EARecyclerClickListener eaRecyclerClickListener) {
        if (context instanceof PickerInformationListener)
            this.pickerInformationListener = (PickerInformationListener) context;
        else {
            throw new IllegalStateException("Context must implement the PickerInformationListener!");
        }

        if (object instanceof BaseFile) {
            if (pickerInformationListener.getLimit() == 1)
                checkBox.setVisibility(View.GONE);
            else
                checkBox.setVisibility(View.VISIBLE);

            tvName.setText(((BaseFile) object).getName());
            tvExtension.setText(FileUtils.getExtensionFromPath(((BaseFile) object).getPath()));
            tvExtension.setBackgroundColor(getColorForExtension(context, FileUtils.getExtensionFromPath(((BaseFile) object).getPath())));

            checkBox.setChecked(((BaseFile) object).isSelected());

            if (eaRecyclerClickListener != null)
                ly_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pickerInformationListener.isLimitAvailable() || ((BaseFile) object).isSelected()) {
                            ((BaseFile) object).setSelected(!((BaseFile) object).isSelected());
                            checkBox.setChecked(((BaseFile) object).isSelected());
                            eaRecyclerClickListener.onEARecyclerItemClick(object);
                        } else
                            pickerInformationListener.showLimitReachedError();
                    }
                });
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.ea_picker_row_file;
    }

    @Override
    public int getViewType() {
        return EAPickerCodes.RECYCLER_TYPE_FILE;
    }

    private @ColorInt
    int getColorForExtension(Context context, String extension) {
        switch (extension.toUpperCase()) {
            case "DOC":
            case "DOCX":
            case "TXT":
            case "RTF":
            case "WPD":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_word, null);
            case "JPG":
            case "JPEG":
            case "PNG":
            case "BMP":
            case "GIF":
            case "PSD":
            case "TIF":
            case "SVG":
            case "DDS":
            case "THM":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_image, null);
            case "PDF":
            case "INDD":
            case "PCT":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_pdf, null);
            case "WAV":
            case "AIF":
            case "MP3":
            case "MID":
            case "AU":
            case "AUD":
            case "IFF":
            case "M3U":
            case "M4A":
            case "MPA":
            case "WMA":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_audio, null);
            case "3G2":
            case "3GP":
            case "ASF":
            case "AVI":
            case "FLV":
            case "M4V":
            case "MOV":
            case "MP4":
            case "MPG":
            case "RM":
            case "SRT":
            case "SWF":
            case "VOB":
            case "WMV":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_video, null);
            case "PPT":
            case "PPTX":
            case "CSV":
            case "XML":
            case "VCF":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_power_point, null);
            case "XLS":
            case "XLSX":
            case "XLR":
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_excel, null);
            default:
                return ResourcesCompat.getColor(context.getResources(), R.color.ea_picker_row_other, null);
        }
    }
}
