package com.android.systemui.statusbar;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationInteractionTracker_Factory implements Factory {
    private final Provider clickerProvider;

    public NotificationInteractionTracker_Factory(Provider provider) {
        this.clickerProvider = provider;
    }

    public static NotificationInteractionTracker_Factory create(Provider provider) {
        return new NotificationInteractionTracker_Factory(provider);
    }

    public static NotificationInteractionTracker newInstance(NotificationClickNotifier notificationClickNotifier) {
        return new NotificationInteractionTracker(notificationClickNotifier);
    }

    @Override // javax.inject.Provider
    public NotificationInteractionTracker get() {
        return newInstance((NotificationClickNotifier) this.clickerProvider.get());
    }
}
