package com.kartega.ophelia.ea_file_picker.interfaces;


import com.kartega.ophelia.ea_file_picker.objects.BaseFile;
import com.kartega.ophelia.ea_file_picker.objects.Directory;

import java.util.List;

/**
 * Created by Vincent Woo
 * Date: 2016/10/11
 * Time: 11:39
 */

public interface FilterResultCallback<T extends BaseFile> {
    void onResult(List<Directory<T>> directories);
}
