package com.kartega.ophelia.ea_file_picker.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.RECYCLER_TYPE_FILE;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.RECYCLER_TYPE_FOLDER;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.RECYCLER_TYPE_DOUBLE_MEDIA;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.REQUEST_CODE_DIRECTORY_DETAIL;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.REQUEST_CODE_IMAGE_CAPTURE;
import static com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes.REQUEST_CODE_VIDEO_CAPTURE;

/**
 * Created by Ahmet Kılıç on 2019-04-19.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({RECYCLER_TYPE_DOUBLE_MEDIA, RECYCLER_TYPE_FILE, RECYCLER_TYPE_FOLDER, REQUEST_CODE_DIRECTORY_DETAIL, REQUEST_CODE_VIDEO_CAPTURE, REQUEST_CODE_IMAGE_CAPTURE})
public @interface EAPickerCodes {
    int RECYCLER_TYPE_DOUBLE_MEDIA = 546;
    int RECYCLER_TYPE_FILE = 547;
    int RECYCLER_TYPE_FOLDER = 550;
    int REQUEST_CODE_DIRECTORY_DETAIL = 236;
    int REQUEST_CODE_EA_PICKER = 2376;
    int REQUEST_CODE_VIDEO_CAPTURE = 432;
    int REQUEST_CODE_IMAGE_CAPTURE = 645;
    int REQUEST_CODE_IMAGE_CAPTURE_ONLY_CAMERA = 646;
}
