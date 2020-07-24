package com.kartega.ophelia.ea_utilities.tools;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by Ahmet Kılıç on 6.02.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class ColorUtils {
    public static int getColor(Context context, int colorID) {
        return ResourcesCompat.getColor(context.getResources(), colorID, null);
    }
}
