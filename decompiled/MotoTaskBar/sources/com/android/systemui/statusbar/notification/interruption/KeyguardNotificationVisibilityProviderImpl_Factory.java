package com.android.systemui.statusbar.notification.interruption;

import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class KeyguardNotificationVisibilityProviderImpl_Factory implements Factory {
    private final Provider globalSettingsProvider;
    private final Provider handlerProvider;
    private final Provider highPriorityProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider secureSettingsProvider;
    private final Provider userTrackerProvider;

    public KeyguardNotificationVisibilityProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.handlerProvider = provider;
        this.lockscreenUserManagerProvider = provider2;
        this.highPriorityProvider = provider3;
        this.userTrackerProvider = provider4;
        this.secureSettingsProvider = provider5;
        this.globalSettingsProvider = provider6;
    }

    public static KeyguardNotificationVisibilityProviderImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new KeyguardNotificationVisibilityProviderImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static KeyguardNotificationVisibilityProviderImpl newInstance(Handler handler, NotificationLockscreenUserManager notificationLockscreenUserManager, HighPriorityProvider highPriorityProvider, UserTracker userTracker, SecureSettings secureSettings, GlobalSettings globalSettings) {
        return new KeyguardNotificationVisibilityProviderImpl(handler, notificationLockscreenUserManager, highPriorityProvider, userTracker, secureSettings, globalSettings);
    }

    @Override // javax.inject.Provider
    public KeyguardNotificationVisibilityProviderImpl get() {
        return newInstance((Handler) this.handlerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (UserTracker) this.userTrackerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get());
    }
}
