package com.kartega.ophelia.ea_utilities.views;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Ahmet Kılıç on 7.02.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public class CustomCountViewPagerAdapter extends PagerAdapter {

    private int viewCount;

    public CustomCountViewPagerAdapter(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        return collection.getChildAt(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return viewCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
