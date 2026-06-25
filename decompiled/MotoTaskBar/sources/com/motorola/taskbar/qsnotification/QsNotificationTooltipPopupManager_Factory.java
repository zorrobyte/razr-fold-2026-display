package com.motorola.taskbar.qsnotification;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationTooltipPopupManager_Factory implements Factory {
    private final Provider contextProvider;

    public QsNotificationTooltipPopupManager_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static QsNotificationTooltipPopupManager_Factory create(Provider provider) {
        return new QsNotificationTooltipPopupManager_Factory(provider);
    }

    public static QsNotificationTooltipPopupManager newInstance(Context context) {
        return new QsNotificationTooltipPopupManager(context);
    }

    @Override // javax.inject.Provider
    public QsNotificationTooltipPopupManager get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
