package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationLoggerViewModel_Factory implements Factory {
    private final Provider activeNotificationsInteractorProvider;

    public NotificationLoggerViewModel_Factory(Provider provider) {
        this.activeNotificationsInteractorProvider = provider;
    }

    public static NotificationLoggerViewModel_Factory create(Provider provider) {
        return new NotificationLoggerViewModel_Factory(provider);
    }

    public static NotificationLoggerViewModel newInstance(ActiveNotificationsInteractor activeNotificationsInteractor) {
        return new NotificationLoggerViewModel(activeNotificationsInteractor);
    }

    @Override // javax.inject.Provider
    public NotificationLoggerViewModel get() {
        return newInstance((ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get());
    }
}
