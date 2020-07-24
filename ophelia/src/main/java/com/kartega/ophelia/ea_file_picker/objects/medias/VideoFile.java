package com.kartega.ophelia.ea_file_picker.objects.medias;

import com.kartega.ophelia.ea_file_picker.objects.BaseFile;

import java.io.Serializable;

/**
 * Created by Vincent Woo
 * Date: 2016/10/11
 * Time: 15:23
 */

public class VideoFile extends BaseFile implements Serializable {
    private long duration;
    private String thumbnail;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
