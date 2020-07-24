package com.kartega.ophelia.ea_networking.upload.ProgressRequest;
/**
 * Created by Ahmet Kılıç on 18.12.2018.
 * Copyright © 2018, Kartega. All rights reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
interface ProgressCallback {
    void onProgressChanged(long numBytes, long totalBytes, float percent);
}