package com.kartega.ophelia.ea_recycler.interfaces;

import com.kartega.ophelia.ea_recycler.enums.SwipeDirection;
import com.kartega.ophelia.ea_recycler.holders.BaseHolder;

/**
 * Created by Ahmet Kılıç on 31.01.2019.
 * Copyright © 2019, Kartega. All rights reserved.
 * <p>
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public interface EASwipeListener {
    void onRecyclerItemSwiped(BaseHolder holder, int position, @SwipeDirection int swipeDir);
}
