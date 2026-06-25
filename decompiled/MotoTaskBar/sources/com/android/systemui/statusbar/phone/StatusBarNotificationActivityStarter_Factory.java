package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.service.dreams.IDreamManager;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class StatusBarNotificationActivityStarter_Factory implements Factory {
    private final Provider activityIntentHelperProvider;
    private final Provider activityStarterProvider;
    private final Provider clickNotifierProvider;
    private final Provider contextProvider;
    private final Provider displayIdProvider;
    private final Provider dreamManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider launchFullScreenIntentProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider mainThreadHandlerProvider;
    private final Provider onUserInteractionCallbackProvider;
    private final Provider presenterProvider;
    private final Provider qsNotificationPanelControllerProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider uiBgExecutorProvider;
    private final Provider userTrackerProvider;
    private final Provider visibilityProvider;

    public StatusBarNotificationActivityStarter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        this.contextProvider = provider;
        this.displayIdProvider = provider2;
        this.mainThreadHandlerProvider = provider3;
        this.uiBgExecutorProvider = provider4;
        this.visibilityProvider = provider5;
        this.headsUpManagerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.clickNotifierProvider = provider8;
        this.dreamManagerProvider = provider9;
        this.remoteInputManagerProvider = provider10;
        this.lockscreenUserManagerProvider = provider11;
        this.activityIntentHelperProvider = provider12;
        this.onUserInteractionCallbackProvider = provider13;
        this.presenterProvider = provider14;
        this.launchFullScreenIntentProvider = provider15;
        this.userTrackerProvider = provider16;
        this.qsNotificationPanelControllerProvider = provider17;
    }

    public static StatusBarNotificationActivityStarter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        return new StatusBarNotificationActivityStarter_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static StatusBarNotificationActivityStarter newInstance(Context context, int i, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, IDreamManager iDreamManager, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ActivityIntentHelper activityIntentHelper, OnUserInteractionCallback onUserInteractionCallback, NotificationPresenter notificationPresenter, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, UserTracker userTracker, QSNotificationPanelController qSNotificationPanelController) {
        return new StatusBarNotificationActivityStarter(context, i, handler, executor, notificationVisibilityProvider, headsUpManager, activityStarter, notificationClickNotifier, iDreamManager, notificationRemoteInputManager, notificationLockscreenUserManager, activityIntentHelper, onUserInteractionCallback, notificationPresenter, launchFullScreenIntentProvider, userTracker, qSNotificationPanelController);
    }

    @Override // javax.inject.Provider
    public StatusBarNotificationActivityStarter get() {
        return newInstance((Context) this.contextProvider.get(), ((Integer) this.displayIdProvider.get()).intValue(), (Handler) this.mainThreadHandlerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (OnUserInteractionCallback) this.onUserInteractionCallbackProvider.get(), (NotificationPresenter) this.presenterProvider.get(), (LaunchFullScreenIntentProvider) this.launchFullScreenIntentProvider.get(), (UserTracker) this.userTrackerProvider.get(), (QSNotificationPanelController) this.qsNotificationPanelControllerProvider.get());
    }
}
