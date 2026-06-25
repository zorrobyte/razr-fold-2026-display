package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class StatusBarNotificationPresenter_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider contextProvider;
    private final Provider headsUpProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider notificationMediaManagerProvider;
    private final Provider remoteInputManagerCallbackProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider stackScrollerControllerProvider;

    public StatusBarNotificationPresenter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.headsUpProvider = provider2;
        this.activityStarterProvider = provider3;
        this.stackScrollerControllerProvider = provider4;
        this.lockscreenUserManagerProvider = provider5;
        this.notificationMediaManagerProvider = provider6;
        this.remoteInputManagerProvider = provider7;
        this.remoteInputManagerCallbackProvider = provider8;
    }

    public static StatusBarNotificationPresenter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new StatusBarNotificationPresenter_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static StatusBarNotificationPresenter newInstance(Context context, HeadsUpManager headsUpManager, ActivityStarter activityStarter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationMediaManager notificationMediaManager, NotificationRemoteInputManager notificationRemoteInputManager, NotificationRemoteInputManager.Callback callback) {
        return new StatusBarNotificationPresenter(context, headsUpManager, activityStarter, notificationStackScrollLayoutController, notificationLockscreenUserManager, notificationMediaManager, notificationRemoteInputManager, callback);
    }

    @Override // javax.inject.Provider
    public StatusBarNotificationPresenter get() {
        return newInstance((Context) this.contextProvider.get(), (HeadsUpManager) this.headsUpProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationStackScrollLayoutController) this.stackScrollerControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (NotificationMediaManager) this.notificationMediaManagerProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotificationRemoteInputManager.Callback) this.remoteInputManagerCallbackProvider.get());
    }
}
