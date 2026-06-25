package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.model.HardwareDisplayController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ExternalDisplayTutorialManager_Factory implements Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider hardwareDisplayControllerProvider;
    private final Provider mainHandlerProvider;

    public ExternalDisplayTutorialManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.mainHandlerProvider = provider2;
        this.bgHandlerProvider = provider3;
        this.hardwareDisplayControllerProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
    }

    public static ExternalDisplayTutorialManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new ExternalDisplayTutorialManager_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ExternalDisplayTutorialManager newInstance(Context context, Handler handler, Handler handler2, HardwareDisplayController hardwareDisplayController, DeviceProvisionedController deviceProvisionedController) {
        return new ExternalDisplayTutorialManager(context, handler, handler2, hardwareDisplayController, deviceProvisionedController);
    }

    @Override // javax.inject.Provider
    public ExternalDisplayTutorialManager get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.bgHandlerProvider.get(), (HardwareDisplayController) this.hardwareDisplayControllerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get());
    }
}
