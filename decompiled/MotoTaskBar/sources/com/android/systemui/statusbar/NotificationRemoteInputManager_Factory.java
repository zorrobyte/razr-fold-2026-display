package com.android.systemui.statusbar;

import android.content.Context;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationRemoteInputManager_Factory implements Factory {
    private final Provider clickNotifierProvider;
    private final Provider contextProvider;
    private final Provider javaAdapterProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider notifPipelineFlagsProvider;
    private final Provider remoteInputControllerLoggerProvider;
    private final Provider remoteInputUriControllerProvider;
    private final Provider smartReplyControllerProvider;
    private final Provider visibilityProvider;

    public NotificationRemoteInputManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.notifPipelineFlagsProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.smartReplyControllerProvider = provider4;
        this.visibilityProvider = provider5;
        this.remoteInputUriControllerProvider = provider6;
        this.remoteInputControllerLoggerProvider = provider7;
        this.clickNotifierProvider = provider8;
        this.javaAdapterProvider = provider9;
    }

    public static NotificationRemoteInputManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new NotificationRemoteInputManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static NotificationRemoteInputManager newInstance(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger, NotificationClickNotifier notificationClickNotifier, JavaAdapter javaAdapter) {
        return new NotificationRemoteInputManager(context, notifPipelineFlags, notificationLockscreenUserManager, smartReplyController, notificationVisibilityProvider, remoteInputUriController, remoteInputControllerLogger, notificationClickNotifier, javaAdapter);
    }

    @Override // javax.inject.Provider
    public NotificationRemoteInputManager get() {
        return newInstance((Context) this.contextProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SmartReplyController) this.smartReplyControllerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (RemoteInputUriController) this.remoteInputUriControllerProvider.get(), (RemoteInputControllerLogger) this.remoteInputControllerLoggerProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get(), (JavaAdapter) this.javaAdapterProvider.get());
    }
}
