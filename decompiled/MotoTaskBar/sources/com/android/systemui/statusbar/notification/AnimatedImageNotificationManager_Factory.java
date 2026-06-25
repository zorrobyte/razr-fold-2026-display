package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AnimatedImageNotificationManager_Factory implements Factory {
    private final Provider bindEventManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider notifCollectionProvider;

    public AnimatedImageNotificationManager_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notifCollectionProvider = provider;
        this.bindEventManagerProvider = provider2;
        this.headsUpManagerProvider = provider3;
    }

    public static AnimatedImageNotificationManager_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new AnimatedImageNotificationManager_Factory(provider, provider2, provider3);
    }

    public static AnimatedImageNotificationManager newInstance(CommonNotifCollection commonNotifCollection, BindEventManager bindEventManager, HeadsUpManager headsUpManager) {
        return new AnimatedImageNotificationManager(commonNotifCollection, bindEventManager, headsUpManager);
    }

    @Override // javax.inject.Provider
    public AnimatedImageNotificationManager get() {
        return newInstance((CommonNotifCollection) this.notifCollectionProvider.get(), (BindEventManager) this.bindEventManagerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get());
    }
}
