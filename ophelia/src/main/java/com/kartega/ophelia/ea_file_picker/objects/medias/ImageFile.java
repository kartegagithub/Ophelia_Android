package com.kartega.ophelia.ea_file_picker.objects.medias;

import com.kartega.ophelia.ea_file_picker.objects.BaseFile;

import java.io.Serializable;

/**
 * Created by Vincent Woo
 * Date: 2016/10/10
 * Time: 17:44
 */

public class ImageFile extends BaseFile implements Serializable {
    private int orientation;   //0, 90, 180, 270

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
}
