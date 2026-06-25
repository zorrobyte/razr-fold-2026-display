package com.android.systemui.dagger;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory implements Factory {
    private final Provider deviceProvisionedControllerProvider;

    public ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(Provider provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static DeviceProvisionedController bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        DeviceProvisionedController deviceProvisionedControllerBindDeviceProvisionedController = ReferenceSystemUIModule.bindDeviceProvisionedController(deviceProvisionedControllerImpl);
        deviceProvisionedControllerBindDeviceProvisionedController.getClass();
        return deviceProvisionedControllerBindDeviceProvisionedController;
    }

    public static ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory create(Provider provider) {
        return new ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(provider);
    }

    @Override // javax.inject.Provider
    public DeviceProvisionedController get() {
        return bindDeviceProvisionedController((DeviceProvisionedControllerImpl) this.deviceProvisionedControllerProvider.get());
    }
}
