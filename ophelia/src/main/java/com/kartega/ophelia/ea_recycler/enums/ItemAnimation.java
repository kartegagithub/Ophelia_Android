package com.kartega.ophelia.ea_recycler.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FADE_IN;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FADE_IN_DOWN;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FADE_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FADE_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FADE_IN_UP;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FLIP_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FLIP_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FLIP_IN_TOP;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.FLIP_IN_BOTTOM;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.LANDING;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.OVERSHOOT_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.OVERSHOOT_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SCALE_IN;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SCALE_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SLIDE_IN_BOTTOM;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SCALE_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SCALE_IN_TOP;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SCALE_IN_BOTTOM;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SLIDE_IN_LEFT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SLIDE_IN_RIGHT;
import static com.kartega.ophelia.ea_recycler.enums.ItemAnimation.SLIDE_IN_TOP;

/**
 * Created by Ahmet Kılıç on 26.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({FADE_IN, FADE_IN_DOWN, FADE_IN_LEFT, FADE_IN_RIGHT, FADE_IN_UP, FLIP_IN_RIGHT, FLIP_IN_LEFT, FLIP_IN_TOP, FLIP_IN_BOTTOM,
        LANDING, OVERSHOOT_IN_LEFT, OVERSHOOT_IN_RIGHT, SCALE_IN, SCALE_IN_LEFT, SCALE_IN_RIGHT, SCALE_IN_TOP, SCALE_IN_BOTTOM,
        SLIDE_IN_LEFT, SLIDE_IN_RIGHT, SLIDE_IN_TOP, SLIDE_IN_BOTTOM})
public @interface ItemAnimation {
    int FADE_IN = 1;
    int FADE_IN_DOWN = 2;
    int FADE_IN_LEFT = 3;
    int FADE_IN_RIGHT = 4;
    int FADE_IN_UP = 5;
    int FLIP_IN_RIGHT = 6;
    int FLIP_IN_LEFT = 7;
    int FLIP_IN_TOP = 8;
    int FLIP_IN_BOTTOM = 9;
    int LANDING = 10;
    int OVERSHOOT_IN_LEFT = 11;
    int OVERSHOOT_IN_RIGHT = 12;
    int SCALE_IN = 13;
    int SCALE_IN_LEFT = 14;
    int SCALE_IN_RIGHT = 15;
    int SCALE_IN_TOP = 16;
    int SCALE_IN_BOTTOM = 17;
    int SLIDE_IN_LEFT = 19;
    int SLIDE_IN_RIGHT = 20;
    int SLIDE_IN_TOP = 21;
    int SLIDE_IN_BOTTOM = 22;
}
