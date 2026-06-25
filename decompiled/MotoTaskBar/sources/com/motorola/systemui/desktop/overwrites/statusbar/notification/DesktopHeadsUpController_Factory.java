package com.motorola.systemui.desktop.overwrites.statusbar.notification;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DesktopHeadsUpController_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider contextProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider displayIdProvider;
    private final Provider handlerProvider;
    private final Provider notifBindPipelineProvider;
    private final Provider notifInflaterProvider;
    private final Provider userTrackerProvider;

    public DesktopHeadsUpController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.displayIdProvider = provider;
        this.contextProvider = provider2;
        this.handlerProvider = provider3;
        this.deviceProvisionedControllerProvider = provider4;
        this.notifInflaterProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.userTrackerProvider = provider7;
        this.notifBindPipelineProvider = provider8;
    }

    public static DesktopHeadsUpController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new DesktopHeadsUpController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static DesktopHeadsUpController newInstance(int i, Context context, Handler handler, DeviceProvisionedController deviceProvisionedController, NotifInflater notifInflater, ConfigurationController configurationController, UserTracker userTracker, NotifBindPipeline notifBindPipeline) {
        return new DesktopHeadsUpController(i, context, handler, deviceProvisionedController, notifInflater, configurationController, userTracker, notifBindPipeline);
    }

    @Override // javax.inject.Provider
    public DesktopHeadsUpController get() {
        return newInstance(((Integer) this.displayIdProvider.get()).intValue(), (Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (NotifInflater) this.notifInflaterProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (NotifBindPipeline) this.notifBindPipelineProvider.get());
    }
}
