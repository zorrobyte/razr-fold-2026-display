package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory implements Factory {
    private final Provider expansionStateLoggerProvider;
    private final Provider javaAdapterProvider;
    private final Provider notifLiveDataStoreProvider;
    private final Provider notifPipelineProvider;
    private final Provider notificationListenerProvider;
    private final Provider notificationPanelLoggerProvider;
    private final Provider uiBgExecutorProvider;
    private final Provider visibilityProvider;

    public NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.notificationListenerProvider = provider;
        this.uiBgExecutorProvider = provider2;
        this.notifLiveDataStoreProvider = provider3;
        this.visibilityProvider = provider4;
        this.notifPipelineProvider = provider5;
        this.javaAdapterProvider = provider6;
        this.expansionStateLoggerProvider = provider7;
        this.notificationPanelLoggerProvider = provider8;
    }

    public static NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new NotificationStatsLoggerModule_Companion_ProvideLegacyLoggerOptionalFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static Optional provideLegacyLoggerOptional(NotificationListener notificationListener, Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, JavaAdapter javaAdapter, NotificationLogger.ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
        Optional optionalProvideLegacyLoggerOptional = NotificationStatsLoggerModule.Companion.provideLegacyLoggerOptional(notificationListener, executor, notifLiveDataStore, notificationVisibilityProvider, notifPipeline, javaAdapter, expansionStateLogger, notificationPanelLogger);
        optionalProvideLegacyLoggerOptional.getClass();
        return optionalProvideLegacyLoggerOptional;
    }

    @Override // javax.inject.Provider
    public Optional get() {
        return provideLegacyLoggerOptional((NotificationListener) this.notificationListenerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (NotifLiveDataStore) this.notifLiveDataStoreProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (NotificationLogger.ExpansionStateLogger) this.expansionStateLoggerProvider.get(), (NotificationPanelLogger) this.notificationPanelLoggerProvider.get());
    }
}
