package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.qs.dagger.QSSceneComponent;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.DesktopUnreadNotificationMonitor;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationCentralSurfaces_Factory implements Factory {
    private final Provider configurationControllerProvider;
    private final Provider delayableExecutorProvider;
    private final Provider displayContextProvider;
    private final Provider displayIdProvider;
    private final Provider headsUpManagerProvider;
    private final Provider mainHandlerProvider;
    private final Provider notificationActivityStarterProvider;
    private final Provider notificationStackScrollLayoutControllerProvider;
    private final Provider notificationsControllerProvider;
    private final Provider qsNotificationPanelControllerProvider;
    private final Provider qsSceneComponentFactoryProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider statusBarNotificationPresenterProvider;
    private final Provider unreadNotificationMonitorProvider;
    private final Provider visualInterruptionDecisionProvider;

    public QsNotificationCentralSurfaces_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        this.displayIdProvider = provider;
        this.displayContextProvider = provider2;
        this.notificationsControllerProvider = provider3;
        this.remoteInputManagerProvider = provider4;
        this.unreadNotificationMonitorProvider = provider5;
        this.notificationStackScrollLayoutControllerProvider = provider6;
        this.statusBarNotificationPresenterProvider = provider7;
        this.notificationActivityStarterProvider = provider8;
        this.configurationControllerProvider = provider9;
        this.visualInterruptionDecisionProvider = provider10;
        this.qsSceneComponentFactoryProvider = provider11;
        this.headsUpManagerProvider = provider12;
        this.qsNotificationPanelControllerProvider = provider13;
        this.mainHandlerProvider = provider14;
        this.delayableExecutorProvider = provider15;
    }

    public static QsNotificationCentralSurfaces_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        return new QsNotificationCentralSurfaces_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static QsNotificationCentralSurfaces newInstance(int i, Context context, NotificationsController notificationsController, NotificationRemoteInputManager notificationRemoteInputManager, DesktopUnreadNotificationMonitor desktopUnreadNotificationMonitor, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarNotificationPresenter statusBarNotificationPresenter, NotificationActivityStarter notificationActivityStarter, ConfigurationController configurationController, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, QSSceneComponent.Factory factory, HeadsUpManager headsUpManager, QSNotificationPanelController qSNotificationPanelController, Handler handler, DelayableExecutor delayableExecutor) {
        return new QsNotificationCentralSurfaces(i, context, notificationsController, notificationRemoteInputManager, desktopUnreadNotificationMonitor, notificationStackScrollLayoutController, statusBarNotificationPresenter, notificationActivityStarter, configurationController, visualInterruptionDecisionProvider, factory, headsUpManager, qSNotificationPanelController, handler, delayableExecutor);
    }

    @Override // javax.inject.Provider
    public QsNotificationCentralSurfaces get() {
        return newInstance(((Integer) this.displayIdProvider.get()).intValue(), (Context) this.displayContextProvider.get(), (NotificationsController) this.notificationsControllerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (DesktopUnreadNotificationMonitor) this.unreadNotificationMonitorProvider.get(), (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get(), (StatusBarNotificationPresenter) this.statusBarNotificationPresenterProvider.get(), (NotificationActivityStarter) this.notificationActivityStarterProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (VisualInterruptionDecisionProvider) this.visualInterruptionDecisionProvider.get(), (QSSceneComponent.Factory) this.qsSceneComponentFactoryProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (QSNotificationPanelController) this.qsNotificationPanelControllerProvider.get(), (Handler) this.mainHandlerProvider.get(), (DelayableExecutor) this.delayableExecutorProvider.get());
    }
}
