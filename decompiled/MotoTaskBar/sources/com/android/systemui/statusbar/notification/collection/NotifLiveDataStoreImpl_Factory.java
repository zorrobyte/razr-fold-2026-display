package com.android.systemui.statusbar.notification.collection;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotifLiveDataStoreImpl_Factory implements Factory {
    private final Provider mainExecutorProvider;

    public NotifLiveDataStoreImpl_Factory(Provider provider) {
        this.mainExecutorProvider = provider;
    }

    public static NotifLiveDataStoreImpl_Factory create(Provider provider) {
        return new NotifLiveDataStoreImpl_Factory(provider);
    }

    public static NotifLiveDataStoreImpl newInstance(Executor executor) {
        return new NotifLiveDataStoreImpl(executor);
    }

    @Override // javax.inject.Provider
    public NotifLiveDataStoreImpl get() {
        return newInstance((Executor) this.mainExecutorProvider.get());
    }
}
