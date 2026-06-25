package com.android.systemui.statusbar.notification.collection.inflation;

import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifUiAdjustmentProvider_Factory implements Factory {
    private final Provider groupMembershipManagerProvider;
    private final Provider handlerProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider sectionStyleProvider;
    private final Provider secureSettingsProvider;
    private final Provider sensitiveNotifProtectionControllerProvider;
    private final Provider userTrackerProvider;

    public NotifUiAdjustmentProvider_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.handlerProvider = provider;
        this.secureSettingsProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.sensitiveNotifProtectionControllerProvider = provider4;
        this.sectionStyleProvider = provider5;
        this.userTrackerProvider = provider6;
        this.groupMembershipManagerProvider = provider7;
    }

    public static NotifUiAdjustmentProvider_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new NotifUiAdjustmentProvider_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static NotifUiAdjustmentProvider newInstance(Handler handler, SecureSettings secureSettings, NotificationLockscreenUserManager notificationLockscreenUserManager, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, SectionStyleProvider sectionStyleProvider, UserTracker userTracker, GroupMembershipManager groupMembershipManager) {
        return new NotifUiAdjustmentProvider(handler, secureSettings, notificationLockscreenUserManager, sensitiveNotificationProtectionController, sectionStyleProvider, userTracker, groupMembershipManager);
    }

    @Override // javax.inject.Provider
    public NotifUiAdjustmentProvider get() {
        return newInstance((Handler) this.handlerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotifProtectionControllerProvider.get(), (SectionStyleProvider) this.sectionStyleProvider.get(), (UserTracker) this.userTrackerProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get());
    }
}
