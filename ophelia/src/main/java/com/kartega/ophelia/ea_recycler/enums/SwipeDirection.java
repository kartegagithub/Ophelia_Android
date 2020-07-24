package com.kartega.ophelia.ea_recycler.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_recycler.enums.SwipeDirection.LEFT;
import static com.kartega.ophelia.ea_recycler.enums.SwipeDirection.RIGHT;

/**
 * Created by Ahmet Kılıç on 31.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({LEFT, RIGHT})
public @interface SwipeDirection {
    int LEFT = 4;
    int RIGHT = 8;
}
