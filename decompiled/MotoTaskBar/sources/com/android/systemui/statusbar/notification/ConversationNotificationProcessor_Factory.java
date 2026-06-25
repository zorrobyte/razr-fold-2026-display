package com.android.systemui.statusbar.notification;

import android.content.pm.LauncherApps;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConversationNotificationProcessor_Factory implements Factory {
    private final Provider conversationNotificationManagerProvider;
    private final Provider launcherAppsProvider;

    public ConversationNotificationProcessor_Factory(Provider provider, Provider provider2) {
        this.launcherAppsProvider = provider;
        this.conversationNotificationManagerProvider = provider2;
    }

    public static ConversationNotificationProcessor_Factory create(Provider provider, Provider provider2) {
        return new ConversationNotificationProcessor_Factory(provider, provider2);
    }

    public static ConversationNotificationProcessor newInstance(LauncherApps launcherApps, ConversationNotificationManager conversationNotificationManager) {
        return new ConversationNotificationProcessor(launcherApps, conversationNotificationManager);
    }

    @Override // javax.inject.Provider
    public ConversationNotificationProcessor get() {
        return newInstance((LauncherApps) this.launcherAppsProvider.get(), (ConversationNotificationManager) this.conversationNotificationManagerProvider.get());
    }
}
