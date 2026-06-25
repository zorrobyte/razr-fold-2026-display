package com.android.systemui.statusbar.notification.collection;

import android.os.Handler;
import com.android.systemui.statusbar.NotificationListener;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DesktopUnreadNotificationMonitor_Factory implements Factory {
    private final Provider bgHandlerProvider;
    private final Provider listBuilderProvider;
    private final Provider notificationListenerProvider;

    public DesktopUnreadNotificationMonitor_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.listBuilderProvider = provider;
        this.notificationListenerProvider = provider2;
        this.bgHandlerProvider = provider3;
    }

    public static DesktopUnreadNotificationMonitor_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new DesktopUnreadNotificationMonitor_Factory(provider, provider2, provider3);
    }

    public static DesktopUnreadNotificationMonitor newInstance(ShadeListBuilder shadeListBuilder, NotificationListener notificationListener, Handler handler) {
        return new DesktopUnreadNotificationMonitor(shadeListBuilder, notificationListener, handler);
    }

    @Override // javax.inject.Provider
    public DesktopUnreadNotificationMonitor get() {
        return newInstance((ShadeListBuilder) this.listBuilderProvider.get(), (NotificationListener) this.notificationListenerProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
