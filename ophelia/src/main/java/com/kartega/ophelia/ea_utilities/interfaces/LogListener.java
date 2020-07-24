package com.kartega.ophelia.ea_utilities.interfaces;

import com.kartega.ophelia.ea_utilities.enums.LogType;

/**
 * Created by Ahmet Kılıç on 30.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public interface LogListener {
    void onLogRequired(@LogType int type, String message);
}
