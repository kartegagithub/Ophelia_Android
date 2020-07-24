package com.kartega.ophelia.ea_file_picker.objects;

import com.kartega.ophelia.ea_file_picker.enums.EAPickerCodes;
import com.kartega.ophelia.ea_recycler.interfaces.EATypeInterface;

/**
 * Created by Ahmet Kılıç on 18.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class DoubleFolder<T> implements EATypeInterface {

    private Directory<T> directoryFirst;
    private Directory<T> directorySecond;

    public DoubleFolder() {
    }

    public DoubleFolder(Directory<T> directoryFirst) {
        this.directoryFirst = directoryFirst;
    }

    public Directory<T> getDirectoryFirst() {
        return directoryFirst;
    }

    public void setDirectoryFirst(Directory<T> directoryFirst) {
        this.directoryFirst = directoryFirst;
    }

    public Directory<T> getDirectorySecond() {
        return directorySecond;
    }

    public void setDirectorySecond(Directory<T> directorySecond) {
        this.directorySecond = directorySecond;
    }

    @Override
    public int getRecyclerType() {
        return EAPickerCodes.RECYCLER_TYPE_FOLDER;
    }
}
