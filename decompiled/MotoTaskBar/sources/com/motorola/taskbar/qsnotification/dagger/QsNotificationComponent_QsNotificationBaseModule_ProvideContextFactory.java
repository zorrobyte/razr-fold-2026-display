package com.motorola.taskbar.qsnotification.dagger;

import android.content.Context;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory implements Factory {
    private final Provider contextProvider;
    private final Provider displayIdProvider;

    public QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.displayIdProvider = provider2;
    }

    public static QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory create(Provider provider, Provider provider2) {
        return new QsNotificationComponent_QsNotificationBaseModule_ProvideContextFactory(provider, provider2);
    }

    public static Context provideContext(Context context, int i) {
        Context contextProvideContext = QsNotificationComponent.QsNotificationBaseModule.provideContext(context, i);
        contextProvideContext.getClass();
        return contextProvideContext;
    }

    @Override // javax.inject.Provider
    public Context get() {
        return provideContext((Context) this.contextProvider.get(), ((Integer) this.displayIdProvider.get()).intValue());
    }
}
