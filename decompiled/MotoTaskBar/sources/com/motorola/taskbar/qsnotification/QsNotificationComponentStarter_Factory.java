package com.motorola.taskbar.qsnotification;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.statusbar.NotificationListener;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponentStarter_Factory implements Factory {
    private final Provider builderProvider;
    private final Provider contextProvider;
    private final Provider displayManagerProvider;
    private final Provider notificationListenerProvider;

    public QsNotificationComponentStarter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.displayManagerProvider = provider2;
        this.notificationListenerProvider = provider3;
        this.builderProvider = provider4;
    }

    public static QsNotificationComponentStarter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new QsNotificationComponentStarter_Factory(provider, provider2, provider3, provider4);
    }

    public static QsNotificationComponentStarter newInstance(Context context, DisplayManager displayManager, NotificationListener notificationListener, QsNotificationComponent.Builder builder) {
        return new QsNotificationComponentStarter(context, displayManager, notificationListener, builder);
    }

    @Override // javax.inject.Provider
    public QsNotificationComponentStarter get() {
        return newInstance((Context) this.contextProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (NotificationListener) this.notificationListenerProvider.get(), (QsNotificationComponent.Builder) this.builderProvider.get());
    }
}
