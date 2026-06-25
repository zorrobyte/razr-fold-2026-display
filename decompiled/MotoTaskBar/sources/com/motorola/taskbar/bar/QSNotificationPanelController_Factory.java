package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Handler;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QSNotificationPanelController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider handlerProvider;
    private final Provider qsNotificationComponentStarterProvider;

    public QSNotificationPanelController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.qsNotificationComponentStarterProvider = provider3;
    }

    public static QSNotificationPanelController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new QSNotificationPanelController_Factory(provider, provider2, provider3);
    }

    public static QSNotificationPanelController newInstance(Context context, Handler handler, QsNotificationComponentStarter qsNotificationComponentStarter) {
        return new QSNotificationPanelController(context, handler, qsNotificationComponentStarter);
    }

    @Override // javax.inject.Provider
    public QSNotificationPanelController get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (QsNotificationComponentStarter) this.qsNotificationComponentStarterProvider.get());
    }
}
