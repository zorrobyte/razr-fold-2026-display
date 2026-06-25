package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationListViewModel_Factory implements Factory {
    private final Provider activeNotificationsInteractorProvider;
    private final Provider bgDispatcherProvider;
    private final Provider dumpManagerProvider;
    private final Provider footerProvider;
    private final Provider headsUpNotificationInteractorProvider;
    private final Provider loggerProvider;
    private final Provider notificationStackInteractorProvider;
    private final Provider remoteInputInteractorProvider;
    private final Provider seenNotificationsInteractorProvider;

    public NotificationListViewModel_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.footerProvider = provider;
        this.loggerProvider = provider2;
        this.activeNotificationsInteractorProvider = provider3;
        this.notificationStackInteractorProvider = provider4;
        this.headsUpNotificationInteractorProvider = provider5;
        this.remoteInputInteractorProvider = provider6;
        this.seenNotificationsInteractorProvider = provider7;
        this.bgDispatcherProvider = provider8;
        this.dumpManagerProvider = provider9;
    }

    public static NotificationListViewModel_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new NotificationListViewModel_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static NotificationListViewModel newInstance(Optional optional, Optional optional2, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationStackInteractor notificationStackInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, RemoteInputInteractor remoteInputInteractor, SeenNotificationsInteractor seenNotificationsInteractor, CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        return new NotificationListViewModel(optional, optional2, activeNotificationsInteractor, notificationStackInteractor, headsUpNotificationInteractor, remoteInputInteractor, seenNotificationsInteractor, coroutineDispatcher, dumpManager);
    }

    @Override // javax.inject.Provider
    public NotificationListViewModel get() {
        return newInstance((Optional) this.footerProvider.get(), (Optional) this.loggerProvider.get(), (ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (NotificationStackInteractor) this.notificationStackInteractorProvider.get(), (HeadsUpNotificationInteractor) this.headsUpNotificationInteractorProvider.get(), (RemoteInputInteractor) this.remoteInputInteractorProvider.get(), (SeenNotificationsInteractor) this.seenNotificationsInteractorProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
