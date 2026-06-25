package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class StatusBarRemoteInputCallback_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider contextProvider;
    private final Provider executorProvider;
    private final Provider groupExpansionManagerProvider;
    private final Provider notificationLockscreenUserManagerProvider;

    public StatusBarRemoteInputCallback_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.groupExpansionManagerProvider = provider2;
        this.notificationLockscreenUserManagerProvider = provider3;
        this.activityStarterProvider = provider4;
        this.executorProvider = provider5;
    }

    public static StatusBarRemoteInputCallback_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new StatusBarRemoteInputCallback_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static StatusBarRemoteInputCallback newInstance(Context context, GroupExpansionManager groupExpansionManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ActivityStarter activityStarter, Executor executor) {
        return new StatusBarRemoteInputCallback(context, groupExpansionManager, notificationLockscreenUserManager, activityStarter, executor);
    }

    @Override // javax.inject.Provider
    public StatusBarRemoteInputCallback get() {
        return newInstance((Context) this.contextProvider.get(), (GroupExpansionManager) this.groupExpansionManagerProvider.get(), (NotificationLockscreenUserManager) this.notificationLockscreenUserManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (Executor) this.executorProvider.get());
    }
}
