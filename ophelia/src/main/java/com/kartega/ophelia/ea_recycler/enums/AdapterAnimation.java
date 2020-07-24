package com.kartega.ophelia.ea_recycler.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_recycler.enums.AdapterAnimation.ALPHA_IN;
import static com.kartega.ophelia.ea_recycler.enums.AdapterAnimation.SCALE_IN;
import static com.kartega.ophelia.ea_recycler.enums.AdapterAnimation.SLIDE_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.AdapterAnimation.SLIDE_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.AdapterAnimation.SLIDE_IN_BOTTOM;

/**
 * Created by Ahmet Kılıç on 26.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({ALPHA_IN, SCALE_IN, SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_BOTTOM})
public @interface AdapterAnimation {
    int ALPHA_IN = 1;
    int SCALE_IN = 2;
    int SLIDE_IN_LEFT = 3;
    int SLIDE_IN_RIGHT = 4;
    int SLIDE_IN_BOTTOM = 5;
}
