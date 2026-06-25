package com.android.systemui.statusbar.notification.init;

import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsControllerImpl_Factory implements Factory {
    private final Provider animatedImageNotificationManagerProvider;
    private final Provider clickerBuilderProvider;
    private final Provider commonNotifCollectionProvider;
    private final Provider headsUpViewBinderProvider;
    private final Provider notifBindPipelineInitializerProvider;
    private final Provider notifLiveDataStoreProvider;
    private final Provider notifPipelineInitializerProvider;
    private final Provider notifPipelineProvider;
    private final Provider notificationListenerProvider;
    private final Provider notificationLoggerOptionalProvider;
    private final Provider notificationRowBinderProvider;
    private final Provider notificationsMediaManagerProvider;
    private final Provider targetSdkResolverProvider;

    public NotificationsControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.notificationListenerProvider = provider;
        this.commonNotifCollectionProvider = provider2;
        this.notifPipelineProvider = provider3;
        this.notifLiveDataStoreProvider = provider4;
        this.targetSdkResolverProvider = provider5;
        this.notifPipelineInitializerProvider = provider6;
        this.notifBindPipelineInitializerProvider = provider7;
        this.notificationLoggerOptionalProvider = provider8;
        this.notificationRowBinderProvider = provider9;
        this.notificationsMediaManagerProvider = provider10;
        this.headsUpViewBinderProvider = provider11;
        this.clickerBuilderProvider = provider12;
        this.animatedImageNotificationManagerProvider = provider13;
    }

    public static NotificationsControllerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        return new NotificationsControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13);
    }

    public static NotificationsControllerImpl newInstance(NotificationListener notificationListener, Lazy lazy, Lazy lazy2, NotifLiveDataStore notifLiveDataStore, TargetSdkResolver targetSdkResolver, Lazy lazy3, NotifBindPipelineInitializer notifBindPipelineInitializer, Optional optional, NotificationRowBinderImpl notificationRowBinderImpl, NotificationMediaManager notificationMediaManager, HeadsUpViewBinder headsUpViewBinder, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager) {
        return new NotificationsControllerImpl(notificationListener, lazy, lazy2, notifLiveDataStore, targetSdkResolver, lazy3, notifBindPipelineInitializer, optional, notificationRowBinderImpl, notificationMediaManager, headsUpViewBinder, builder, animatedImageNotificationManager);
    }

    @Override // javax.inject.Provider
    public NotificationsControllerImpl get() {
        return newInstance((NotificationListener) this.notificationListenerProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionProvider), DoubleCheck.lazy(this.notifPipelineProvider), (NotifLiveDataStore) this.notifLiveDataStoreProvider.get(), (TargetSdkResolver) this.targetSdkResolverProvider.get(), DoubleCheck.lazy(this.notifPipelineInitializerProvider), (NotifBindPipelineInitializer) this.notifBindPipelineInitializerProvider.get(), (Optional) this.notificationLoggerOptionalProvider.get(), (NotificationRowBinderImpl) this.notificationRowBinderProvider.get(), (NotificationMediaManager) this.notificationsMediaManagerProvider.get(), (HeadsUpViewBinder) this.headsUpViewBinderProvider.get(), (NotificationClicker.Builder) this.clickerBuilderProvider.get(), (AnimatedImageNotificationManager) this.animatedImageNotificationManagerProvider.get());
    }
}
