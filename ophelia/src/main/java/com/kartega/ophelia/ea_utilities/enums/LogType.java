package com.kartega.ophelia.ea_utilities.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_utilities.enums.LogType.DEBUG;
import static com.kartega.ophelia.ea_utilities.enums.LogType.ERROR;
import static com.kartega.ophelia.ea_utilities.enums.LogType.CRITICAL_ERROR;
import static com.kartega.ophelia.ea_utilities.enums.LogType.INFORMATION;
import static com.kartega.ophelia.ea_utilities.enums.LogType.WARNING;

/**
 * Created by Ahmet Kılıç on 18.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({CRITICAL_ERROR, ERROR, INFORMATION, WARNING, DEBUG})
public @interface LogType {
    int CRITICAL_ERROR = 1;
    int ERROR = 2;
    int INFORMATION = 3;
    int WARNING = 4;
    int DEBUG = 5;
}
