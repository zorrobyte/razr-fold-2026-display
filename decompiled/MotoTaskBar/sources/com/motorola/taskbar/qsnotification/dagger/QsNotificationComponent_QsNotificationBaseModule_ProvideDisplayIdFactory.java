package com.motorola.taskbar.qsnotification.dagger;

import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory implements Factory {
    private final Provider displayIdProvider;

    public QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory(Provider provider) {
        this.displayIdProvider = provider;
    }

    public static QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory create(Provider provider) {
        return new QsNotificationComponent_QsNotificationBaseModule_ProvideDisplayIdFactory(provider);
    }

    public static int provideDisplayId(int i) {
        return QsNotificationComponent.QsNotificationBaseModule.provideDisplayId(i);
    }

    @Override // javax.inject.Provider
    public Integer get() {
        return Integer.valueOf(provideDisplayId(((Integer) this.displayIdProvider.get()).intValue()));
    }
}
