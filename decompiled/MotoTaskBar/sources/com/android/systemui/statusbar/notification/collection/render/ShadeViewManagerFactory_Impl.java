package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import dagger.internal.InstanceFactory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewManagerFactory_Impl implements ShadeViewManagerFactory {
    private final ShadeViewManager_Factory delegateFactory;

    ShadeViewManagerFactory_Impl(ShadeViewManager_Factory shadeViewManager_Factory) {
        this.delegateFactory = shadeViewManager_Factory;
    }

    public static Provider createFactoryProvider(ShadeViewManager_Factory shadeViewManager_Factory) {
        return InstanceFactory.create(new ShadeViewManagerFactory_Impl(shadeViewManager_Factory));
    }

    @Override // com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory
    public ShadeViewManager create(NotificationListContainer notificationListContainer, NotifStackController notifStackController) {
        return this.delegateFactory.get(notificationListContainer, notifStackController);
    }
}
