package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationsModule_ProvideListContainerFactory implements Factory {
    private final Provider nsslControllerProvider;

    public NotificationsModule_ProvideListContainerFactory(Provider provider) {
        this.nsslControllerProvider = provider;
    }

    public static NotificationsModule_ProvideListContainerFactory create(Provider provider) {
        return new NotificationsModule_ProvideListContainerFactory(provider);
    }

    public static NotificationListContainer provideListContainer(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        NotificationListContainer notificationListContainerProvideListContainer = NotificationsModule.provideListContainer(notificationStackScrollLayoutController);
        notificationListContainerProvideListContainer.getClass();
        return notificationListContainerProvideListContainer;
    }

    @Override // javax.inject.Provider
    public NotificationListContainer get() {
        return provideListContainer((NotificationStackScrollLayoutController) this.nsslControllerProvider.get());
    }
}
