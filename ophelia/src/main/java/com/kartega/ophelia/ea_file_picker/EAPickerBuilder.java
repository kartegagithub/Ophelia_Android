package com.kartega.ophelia.ea_file_picker;

import com.kartega.ophelia.ea_file_picker.enums.EAPickerType;
import com.kartega.ophelia.ea_file_picker.interfaces.EAPickerResponseListener;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_utilities.tools.ObjectUtils;

import java.util.List;

/**
 * Created by Ahmet Kılıç on 2019-04-22.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class EAPickerBuilder {
    @EAPickerType
    private int pickerType;
    private List<BaseFile> selectedItems;
    private int limit;
    private boolean useCamera;
    private String[] suffix;
    private EAPickerResponseListener responseListener;

    public  EAPickerBuilder() {
        this.limit = 1;
        this.pickerType = EAPickerType.FILE;
        this.suffix = new String[]{"xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf"};
        this.useCamera = false;
    }

    public EAPickerBuilder setPickerType(@EAPickerType int pickerType) {
        this.pickerType = pickerType;
        return this;
    }

    public EAPickerBuilder setSelectedItems(List<BaseFile> selectedItems) {
        this.selectedItems = selectedItems;
        checkLimitAndSelectedItems();
        return this;
    }

    public EAPickerBuilder setLimit(int limit) {
        this.limit = limit;
        checkLimitAndSelectedItems();
        return this;
    }

    public EAPickerBuilder setUseCamera(boolean useCamera) {
        this.useCamera = useCamera;
        return this;
    }

    public EAPickerBuilder setSuffix(String[] suffix) {
        this.suffix = suffix;
        return this;
    }

    public EAPickerBuilder setResponseListener(EAPickerResponseListener responseListener) {
        this.responseListener = responseListener;
        return this;
    }

    public int getPickerType() {
        return pickerType;
    }

    public List<BaseFile> getSelectedItems() {
        return selectedItems;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isUseCamera() {
        return useCamera;
    }

    public String[] getSuffix() {
        return suffix;
    }

    public EAPickerResponseListener getResponseListener() {
        return responseListener;
    }


    private void checkLimitAndSelectedItems() {
        if (ObjectUtils.arrayIsNotEmpty(selectedItems) && limit > selectedItems.size()) {
            throw new IllegalArgumentException("Limit can not be smaller than the size of the selected items!");
        }
    }
}