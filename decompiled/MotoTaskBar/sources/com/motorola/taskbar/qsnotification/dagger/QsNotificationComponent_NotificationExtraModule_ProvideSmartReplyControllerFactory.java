package com.motorola.taskbar.qsnotification.dagger;

import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory implements Factory {
    private final Provider clickNotifierProvider;
    private final Provider dumpManagerProvider;
    private final Provider statusBarServiceProvider;
    private final Provider visibilityProvider;

    public QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.dumpManagerProvider = provider;
        this.visibilityProvider = provider2;
        this.statusBarServiceProvider = provider3;
        this.clickNotifierProvider = provider4;
    }

    public static QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new QsNotificationComponent_NotificationExtraModule_ProvideSmartReplyControllerFactory(provider, provider2, provider3, provider4);
    }

    public static SmartReplyController provideSmartReplyController(DumpManager dumpManager, NotificationVisibilityProvider notificationVisibilityProvider, IStatusBarService iStatusBarService, NotificationClickNotifier notificationClickNotifier) {
        SmartReplyController smartReplyControllerProvideSmartReplyController = QsNotificationComponent.NotificationExtraModule.provideSmartReplyController(dumpManager, notificationVisibilityProvider, iStatusBarService, notificationClickNotifier);
        smartReplyControllerProvideSmartReplyController.getClass();
        return smartReplyControllerProvideSmartReplyController;
    }

    @Override // javax.inject.Provider
    public SmartReplyController get() {
        return provideSmartReplyController((DumpManager) this.dumpManagerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get());
    }
}
