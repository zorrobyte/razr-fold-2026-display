package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationDismissibilityProviderImpl_Factory implements Factory {
    private final Provider dumpManagerProvider;

    public NotificationDismissibilityProviderImpl_Factory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static NotificationDismissibilityProviderImpl_Factory create(Provider provider) {
        return new NotificationDismissibilityProviderImpl_Factory(provider);
    }

    public static NotificationDismissibilityProviderImpl newInstance(DumpManager dumpManager) {
        return new NotificationDismissibilityProviderImpl(dumpManager);
    }

    @Override // javax.inject.Provider
    public NotificationDismissibilityProviderImpl get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}
