package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RowContentBindStage_Factory implements Factory {
    private final Provider binderProvider;
    private final Provider errorManagerProvider;
    private final Provider loggerProvider;

    public RowContentBindStage_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.binderProvider = provider;
        this.errorManagerProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static RowContentBindStage_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new RowContentBindStage_Factory(provider, provider2, provider3);
    }

    public static RowContentBindStage newInstance(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger) {
        return new RowContentBindStage(notificationRowContentBinder, notifInflationErrorManager, rowContentBindStageLogger);
    }

    @Override // javax.inject.Provider
    public RowContentBindStage get() {
        return newInstance((NotificationRowContentBinder) this.binderProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), (RowContentBindStageLogger) this.loggerProvider.get());
    }
}
