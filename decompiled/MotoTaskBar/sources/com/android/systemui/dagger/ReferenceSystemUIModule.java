package com.android.systemui.dagger;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;

/* JADX INFO: loaded from: classes.dex */
public abstract class ReferenceSystemUIModule {
    static DeviceProvisionedController bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        deviceProvisionedControllerImpl.init();
        return deviceProvisionedControllerImpl;
    }
}
