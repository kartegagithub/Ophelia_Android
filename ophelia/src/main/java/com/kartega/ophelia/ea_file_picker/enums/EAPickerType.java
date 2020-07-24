package com.kartega.ophelia.ea_file_picker.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_file_picker.enums.EAPickerType.AUDIO;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerType.FILE;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerType.IMAGE;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerType.IMAGE_FROM_CAMERA;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerType.VIDEO;

/**
 * Created by Ahmet Kılıç on 2019-04-19.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({IMAGE, FILE, AUDIO, VIDEO,IMAGE_FROM_CAMERA})
public @interface EAPickerType {
    int IMAGE = 1;
    int FILE = 2;
    int AUDIO = 3;
    int VIDEO = 4;
    int IMAGE_FROM_CAMERA = 5;
}
