package com.motorola.taskbar.qsnotification.dagger;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory implements Factory {
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider mediaDataManagerProvider;
    private final Provider notifCollectionProvider;
    private final Provider notifPipelineProvider;
    private final Provider visibilityProvider;

    public QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.visibilityProvider = provider2;
        this.notifPipelineProvider = provider3;
        this.notifCollectionProvider = provider4;
        this.mediaDataManagerProvider = provider5;
        this.dumpManagerProvider = provider6;
    }

    public static QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new QsNotificationComponent_NotificationExtraModule_ProvideNotificationMediaManagerFactory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static NotificationMediaManager provideNotificationMediaManager(Context context, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, NotifCollection notifCollection, MediaDataManager mediaDataManager, DumpManager dumpManager) {
        NotificationMediaManager notificationMediaManagerProvideNotificationMediaManager = QsNotificationComponent.NotificationExtraModule.provideNotificationMediaManager(context, notificationVisibilityProvider, notifPipeline, notifCollection, mediaDataManager, dumpManager);
        notificationMediaManagerProvideNotificationMediaManager.getClass();
        return notificationMediaManagerProvideNotificationMediaManager;
    }

    @Override // javax.inject.Provider
    public NotificationMediaManager get() {
        return provideNotificationMediaManager((Context) this.contextProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (MediaDataManager) this.mediaDataManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
