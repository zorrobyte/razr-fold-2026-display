package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConversationNotificationManager_Factory implements Factory {
    private final Provider bindEventManagerProvider;
    private final Provider contextProvider;
    private final Provider mainHandlerProvider;
    private final Provider notifCollectionProvider;

    public ConversationNotificationManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.bindEventManagerProvider = provider;
        this.contextProvider = provider2;
        this.notifCollectionProvider = provider3;
        this.mainHandlerProvider = provider4;
    }

    public static ConversationNotificationManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new ConversationNotificationManager_Factory(provider, provider2, provider3, provider4);
    }

    public static ConversationNotificationManager newInstance(BindEventManager bindEventManager, Context context, CommonNotifCollection commonNotifCollection, Handler handler) {
        return new ConversationNotificationManager(bindEventManager, context, commonNotifCollection, handler);
    }

    @Override // javax.inject.Provider
    public ConversationNotificationManager get() {
        return newInstance((BindEventManager) this.bindEventManagerProvider.get(), (Context) this.contextProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}
