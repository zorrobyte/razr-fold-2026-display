package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifBindPipeline_Factory implements Factory {
    private final Provider collectionProvider;
    private final Provider loggerProvider;
    private final Provider processorFactoryProvider;

    public NotifBindPipeline_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.collectionProvider = provider;
        this.loggerProvider = provider2;
        this.processorFactoryProvider = provider3;
    }

    public static NotifBindPipeline_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new NotifBindPipeline_Factory(provider, provider2, provider3);
    }

    public static NotifBindPipeline newInstance(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, NotificationEntryProcessorFactory notificationEntryProcessorFactory) {
        return new NotifBindPipeline(commonNotifCollection, notifBindPipelineLogger, notificationEntryProcessorFactory);
    }

    @Override // javax.inject.Provider
    public NotifBindPipeline get() {
        return newInstance((CommonNotifCollection) this.collectionProvider.get(), (NotifBindPipelineLogger) this.loggerProvider.get(), (NotificationEntryProcessorFactory) this.processorFactoryProvider.get());
    }
}
