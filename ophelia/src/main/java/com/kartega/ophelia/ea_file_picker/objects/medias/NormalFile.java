package com.kartega.ophelia.ea_file_picker.objects.medias;


import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_recycler.interfaces.EATypeInterface;

import java.io.Serializable;

/**
 * Created by Vincent Woo
 * Date: 2016/10/12
 * Time: 14:45
 */

public class NormalFile extends BaseFile implements Serializable, EATypeInterface {
    private String mimeType;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public int getRecyclerType() {
        return EAPickerCodes.RECYCLER_TYPE_FILE;
    }
}
