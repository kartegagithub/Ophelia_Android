package com.kartega.ophelia.ea_recycler.interfaces;

import com.kartega.ophelia.ea_recycler.enums.MovementType;

/**
 * Created by Ahmet Kılıç on 31.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public interface EAMovementCompleteListener {
    void onMovementComplete(@MovementType int type);
}
