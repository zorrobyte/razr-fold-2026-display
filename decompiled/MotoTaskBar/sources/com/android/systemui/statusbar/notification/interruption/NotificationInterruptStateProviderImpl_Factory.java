package com.android.systemui.statusbar.notification.interruption;

import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationInterruptStateProviderImpl_Factory implements Factory {
    private final Provider ambientDisplayConfigurationProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider flagsProvider;
    private final Provider globalSettingsProvider;
    private final Provider headsUpManagerProvider;
    private final Provider keyguardNotificationVisibilityProvider;
    private final Provider loggerProvider;
    private final Provider mainHandlerProvider;
    private final Provider powerManagerProvider;
    private final Provider systemClockProvider;
    private final Provider uiEventLoggerProvider;
    private final Provider userTrackerProvider;

    public NotificationInterruptStateProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.powerManagerProvider = provider;
        this.ambientDisplayConfigurationProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.loggerProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.flagsProvider = provider6;
        this.keyguardNotificationVisibilityProvider = provider7;
        this.uiEventLoggerProvider = provider8;
        this.userTrackerProvider = provider9;
        this.deviceProvisionedControllerProvider = provider10;
        this.systemClockProvider = provider11;
        this.globalSettingsProvider = provider12;
    }

    public static NotificationInterruptStateProviderImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        return new NotificationInterruptStateProviderImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static NotificationInterruptStateProviderImpl newInstance(PowerManager powerManager, AmbientDisplayConfiguration ambientDisplayConfiguration, HeadsUpManager headsUpManager, NotificationInterruptLogger notificationInterruptLogger, Handler handler, NotifPipelineFlags notifPipelineFlags, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, UiEventLogger uiEventLogger, UserTracker userTracker, DeviceProvisionedController deviceProvisionedController, SystemClock systemClock, GlobalSettings globalSettings) {
        return new NotificationInterruptStateProviderImpl(powerManager, ambientDisplayConfiguration, headsUpManager, notificationInterruptLogger, handler, notifPipelineFlags, keyguardNotificationVisibilityProvider, uiEventLogger, userTracker, deviceProvisionedController, systemClock, globalSettings);
    }

    @Override // javax.inject.Provider
    public NotificationInterruptStateProviderImpl get() {
        return newInstance((PowerManager) this.powerManagerProvider.get(), (AmbientDisplayConfiguration) this.ambientDisplayConfigurationProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (NotificationInterruptLogger) this.loggerProvider.get(), (Handler) this.mainHandlerProvider.get(), (NotifPipelineFlags) this.flagsProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (SystemClock) this.systemClockProvider.get(), (GlobalSettings) this.globalSettingsProvider.get());
    }
}
