package com.android.systemui.statusbar.notification.interruption;

import android.os.Handler;
import com.android.systemui.Dumpable;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;

/* JADX INFO: compiled from: KeyguardNotificationVisibilityProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class KeyguardNotificationVisibilityProviderImpl implements Dumpable, KeyguardNotificationVisibilityProvider {
    private final GlobalSettings globalSettings;
    private final Handler handler;
    private final HighPriorityProvider highPriorityProvider;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final ListenerSet onStateChangedListeners;
    private final SecureSettings secureSettings;
    private final UserTracker userTracker;

    public KeyguardNotificationVisibilityProviderImpl(Handler handler, NotificationLockscreenUserManager notificationLockscreenUserManager, HighPriorityProvider highPriorityProvider, UserTracker userTracker, SecureSettings secureSettings, GlobalSettings globalSettings) {
        handler.getClass();
        notificationLockscreenUserManager.getClass();
        highPriorityProvider.getClass();
        userTracker.getClass();
        secureSettings.getClass();
        globalSettings.getClass();
        this.handler = handler;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.highPriorityProvider = highPriorityProvider;
        this.userTracker = userTracker;
        this.secureSettings = secureSettings;
        this.globalSettings = globalSettings;
        this.onStateChangedListeners = new ListenerSet();
    }

    @Override // com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider
    public boolean shouldHideNotification(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return false;
    }
}
