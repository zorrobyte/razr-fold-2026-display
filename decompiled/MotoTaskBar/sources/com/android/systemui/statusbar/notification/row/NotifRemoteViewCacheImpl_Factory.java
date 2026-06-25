package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifRemoteViewCacheImpl_Factory implements Factory {
    private final Provider collectionProvider;

    public NotifRemoteViewCacheImpl_Factory(Provider provider) {
        this.collectionProvider = provider;
    }

    public static NotifRemoteViewCacheImpl_Factory create(Provider provider) {
        return new NotifRemoteViewCacheImpl_Factory(provider);
    }

    public static NotifRemoteViewCacheImpl newInstance(CommonNotifCollection commonNotifCollection) {
        return new NotifRemoteViewCacheImpl(commonNotifCollection);
    }

    @Override // javax.inject.Provider
    public NotifRemoteViewCacheImpl get() {
        return newInstance((CommonNotifCollection) this.collectionProvider.get());
    }
}
