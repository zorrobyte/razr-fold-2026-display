package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpViewBinder_Factory implements Factory {
    private final Provider bindStageProvider;
    private final Provider loggerProvider;
    private final Provider notificationMessagingUtilProvider;

    public HeadsUpViewBinder_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.notificationMessagingUtilProvider = provider;
        this.bindStageProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static HeadsUpViewBinder_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new HeadsUpViewBinder_Factory(provider, provider2, provider3);
    }

    public static HeadsUpViewBinder newInstance(NotificationMessagingUtil notificationMessagingUtil, RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        return new HeadsUpViewBinder(notificationMessagingUtil, rowContentBindStage, headsUpViewBinderLogger);
    }

    @Override // javax.inject.Provider
    public HeadsUpViewBinder get() {
        return newInstance((NotificationMessagingUtil) this.notificationMessagingUtilProvider.get(), (RowContentBindStage) this.bindStageProvider.get(), (HeadsUpViewBinderLogger) this.loggerProvider.get());
    }
}
