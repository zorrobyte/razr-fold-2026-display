package com.android.systemui.statusbar.notification.collection;

import android.os.Handler;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotifCollection_Factory implements Factory {
    private final Provider bgExecutorProvider;
    private final Provider clockProvider;
    private final Provider dismissibilityProvider;
    private final Provider dumpManagerProvider;
    private final Provider logBufferEulogizerProvider;
    private final Provider loggerProvider;
    private final Provider mainHandlerProvider;
    private final Provider notifPipelineFlagsProvider;
    private final Provider statusBarServiceProvider;

    public NotifCollection_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.statusBarServiceProvider = provider;
        this.clockProvider = provider2;
        this.notifPipelineFlagsProvider = provider3;
        this.loggerProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.bgExecutorProvider = provider6;
        this.logBufferEulogizerProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.dismissibilityProvider = provider9;
    }

    public static NotifCollection_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new NotifCollection_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static NotifCollection newInstance(IStatusBarService iStatusBarService, SystemClock systemClock, NotifPipelineFlags notifPipelineFlags, NotifCollectionLogger notifCollectionLogger, Handler handler, Executor executor, LogBufferEulogizer logBufferEulogizer, DumpManager dumpManager, NotificationDismissibilityProvider notificationDismissibilityProvider) {
        return new NotifCollection(iStatusBarService, systemClock, notifPipelineFlags, notifCollectionLogger, handler, executor, logBufferEulogizer, dumpManager, notificationDismissibilityProvider);
    }

    @Override // javax.inject.Provider
    public NotifCollection get() {
        return newInstance((IStatusBarService) this.statusBarServiceProvider.get(), (SystemClock) this.clockProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (NotifCollectionLogger) this.loggerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.bgExecutorProvider.get(), (LogBufferEulogizer) this.logBufferEulogizerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (NotificationDismissibilityProvider) this.dismissibilityProvider.get());
    }
}
