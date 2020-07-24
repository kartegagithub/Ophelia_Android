package com.kartega.ophelia.ea_file_picker.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ahmet Kılıç on 15.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class SelectedItemsExtraObject implements Serializable {
    private List<BaseFile> selectedItems;

    public SelectedItemsExtraObject(List<BaseFile> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<BaseFile> getSelectedItems() {
        return selectedItems;
    }
}
