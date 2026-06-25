package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifInflaterImpl_Factory implements Factory {
    private final Provider errorManagerProvider;
    private final Provider loggerProvider;

    public NotifInflaterImpl_Factory(Provider provider, Provider provider2) {
        this.errorManagerProvider = provider;
        this.loggerProvider = provider2;
    }

    public static NotifInflaterImpl_Factory create(Provider provider, Provider provider2) {
        return new NotifInflaterImpl_Factory(provider, provider2);
    }

    public static NotifInflaterImpl newInstance(NotifInflationErrorManager notifInflationErrorManager, NotifInflaterLogger notifInflaterLogger) {
        return new NotifInflaterImpl(notifInflationErrorManager, notifInflaterLogger);
    }

    @Override // javax.inject.Provider
    public NotifInflaterImpl get() {
        return newInstance((NotifInflationErrorManager) this.errorManagerProvider.get(), (NotifInflaterLogger) this.loggerProvider.get());
    }
}
