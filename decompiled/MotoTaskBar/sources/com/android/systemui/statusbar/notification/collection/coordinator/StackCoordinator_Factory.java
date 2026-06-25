package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class StackCoordinator_Factory implements Factory {
    private final Provider activeNotificationsInteractorProvider;
    private final Provider groupExpansionManagerImplProvider;
    private final Provider renderListInteractorProvider;
    private final Provider sensitiveNotificationProtectionControllerProvider;

    public StackCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.groupExpansionManagerImplProvider = provider;
        this.renderListInteractorProvider = provider2;
        this.activeNotificationsInteractorProvider = provider3;
        this.sensitiveNotificationProtectionControllerProvider = provider4;
    }

    public static StackCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new StackCoordinator_Factory(provider, provider2, provider3, provider4);
    }

    public static StackCoordinator newInstance(GroupExpansionManagerImpl groupExpansionManagerImpl, RenderNotificationListInteractor renderNotificationListInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        return new StackCoordinator(groupExpansionManagerImpl, renderNotificationListInteractor, activeNotificationsInteractor, sensitiveNotificationProtectionController);
    }

    @Override // javax.inject.Provider
    public StackCoordinator get() {
        return newInstance((GroupExpansionManagerImpl) this.groupExpansionManagerImplProvider.get(), (RenderNotificationListInteractor) this.renderListInteractorProvider.get(), (ActiveNotificationsInteractor) this.activeNotificationsInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get());
    }
}
