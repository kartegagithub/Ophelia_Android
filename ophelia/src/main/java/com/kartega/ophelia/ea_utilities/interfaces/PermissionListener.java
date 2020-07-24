package com.kartega.ophelia.ea_utilities.interfaces;

import java.util.List;

/**
 * Created by Ahmet Kılıç on 1.04.2019.
 * Copyright © 2019. All rights reserved.
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */
public interface PermissionListener {
    void onPermissionsGranted();
    void onPermissionsDenied(List<String> deniedPermissions);
}
