package com.kartega.ophelia.ea_file_picker.interfaces;

import com.kartega.ophelia.ea_file_picker.objects.BaseFile;

import java.util.List;

/**
 * Created by Ahmet Kılıç on 2019-04-22.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public interface EAPickerResponseListener {
    void onFilePickComplete(List<BaseFile> files);
    void onFilePickCancelled();
}
