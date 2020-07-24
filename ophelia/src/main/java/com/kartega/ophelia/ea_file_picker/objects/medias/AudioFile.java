package com.kartega.ophelia.ea_file_picker.objects.medias;


import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_file_picker.objects.BaseFile;

import java.io.Serializable;

/**
 * Created by Vincent Woo
 * Date: 2016/10/11
 * Time: 15:52
 */

public class AudioFile extends BaseFile implements Serializable {
    private long duration;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int getRecyclerType() {
        return EAPickerCodes.RECYCLER_TYPE_FILE;
    }
}
