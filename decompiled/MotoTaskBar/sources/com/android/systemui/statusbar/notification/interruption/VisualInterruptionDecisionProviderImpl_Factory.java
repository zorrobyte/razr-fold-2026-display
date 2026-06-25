package com.android.systemui.statusbar.notification.interruption;

import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionDecisionProviderImpl_Factory implements Factory {
    private final Provider ambientDisplayConfigurationProvider;
    private final Provider avalancheProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider displayIdProvider;
    private final Provider globalSettingsProvider;
    private final Provider headsUpManagerProvider;
    private final Provider keyguardNotificationVisibilityProvider;
    private final Provider loggerProvider;
    private final Provider mainHandlerProvider;
    private final Provider powerManagerProvider;
    private final Provider systemClockProvider;
    private final Provider systemSettingsProvider;
    private final Provider uiEventLoggerProvider;
    private final Provider userTrackerProvider;

    public VisualInterruptionDecisionProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14) {
        this.ambientDisplayConfigurationProvider = provider;
        this.deviceProvisionedControllerProvider = provider2;
        this.globalSettingsProvider = provider3;
        this.headsUpManagerProvider = provider4;
        this.keyguardNotificationVisibilityProvider = provider5;
        this.loggerProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.displayIdProvider = provider8;
        this.powerManagerProvider = provider9;
        this.systemClockProvider = provider10;
        this.uiEventLoggerProvider = provider11;
        this.userTrackerProvider = provider12;
        this.avalancheProvider = provider13;
        this.systemSettingsProvider = provider14;
    }

    public static VisualInterruptionDecisionProviderImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14) {
        return new VisualInterruptionDecisionProviderImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static VisualInterruptionDecisionProviderImpl newInstance(AmbientDisplayConfiguration ambientDisplayConfiguration, DeviceProvisionedController deviceProvisionedController, GlobalSettings globalSettings, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler, int i, PowerManager powerManager, SystemClock systemClock, UiEventLogger uiEventLogger, UserTracker userTracker, AvalancheProvider avalancheProvider, SystemSettings systemSettings) {
        return new VisualInterruptionDecisionProviderImpl(ambientDisplayConfiguration, deviceProvisionedController, globalSettings, headsUpManager, keyguardNotificationVisibilityProvider, visualInterruptionDecisionLogger, handler, i, powerManager, systemClock, uiEventLogger, userTracker, avalancheProvider, systemSettings);
    }

    @Override // javax.inject.Provider
    public VisualInterruptionDecisionProviderImpl get() {
        return newInstance((AmbientDisplayConfiguration) this.ambientDisplayConfigurationProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (VisualInterruptionDecisionLogger) this.loggerProvider.get(), (Handler) this.mainHandlerProvider.get(), ((Integer) this.displayIdProvider.get()).intValue(), (PowerManager) this.powerManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (AvalancheProvider) this.avalancheProvider.get(), (SystemSettings) this.systemSettingsProvider.get());
    }
}
