package com.kartega.ophelia.ea_file_picker.objects;

import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_recycler.interfaces.EATypeInterface;

import java.io.Serializable;

/**
 * Created by Ahmet Kılıç on 15.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class DoubleMedia implements Serializable, EATypeInterface {

    private BaseFile firstItem;
    private BaseFile secondItem;

    public DoubleMedia() {

    }

    public DoubleMedia(BaseFile firstItem) {
        this.firstItem = firstItem;
    }

    public BaseFile getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(BaseFile firstItem) {
        this.firstItem = firstItem;
    }

    public BaseFile getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(BaseFile secondItem) {
        this.secondItem = secondItem;
    }

    @Override
    public int getRecyclerType() {
        return EAPickerCodes.RECYCLER_TYPE_DOUBLE_MEDIA;
    }
}
