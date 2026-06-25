package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationViewFlipperViewModel_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider stackInteractorProvider;

    public NotificationViewFlipperViewModel_Factory(Provider provider, Provider provider2) {
        this.dumpManagerProvider = provider;
        this.stackInteractorProvider = provider2;
    }

    public static NotificationViewFlipperViewModel_Factory create(Provider provider, Provider provider2) {
        return new NotificationViewFlipperViewModel_Factory(provider, provider2);
    }

    public static NotificationViewFlipperViewModel newInstance(DumpManager dumpManager, NotificationStackInteractor notificationStackInteractor) {
        return new NotificationViewFlipperViewModel(dumpManager, notificationStackInteractor);
    }

    @Override // javax.inject.Provider
    public NotificationViewFlipperViewModel get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (NotificationStackInteractor) this.stackInteractorProvider.get());
    }
}
