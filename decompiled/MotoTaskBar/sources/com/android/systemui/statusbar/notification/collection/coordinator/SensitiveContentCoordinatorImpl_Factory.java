package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SensitiveContentCoordinatorImpl_Factory implements Factory {
    private final Provider dynamicPrivacyControllerProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider selectedUserInteractorProvider;
    private final Provider sensitiveNotificationProtectionControllerProvider;

    public SensitiveContentCoordinatorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.dynamicPrivacyControllerProvider = provider;
        this.lockscreenUserManagerProvider = provider2;
        this.selectedUserInteractorProvider = provider3;
        this.sensitiveNotificationProtectionControllerProvider = provider4;
    }

    public static SensitiveContentCoordinatorImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new SensitiveContentCoordinatorImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static SensitiveContentCoordinatorImpl newInstance(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        return new SensitiveContentCoordinatorImpl(dynamicPrivacyController, notificationLockscreenUserManager, selectedUserInteractor, sensitiveNotificationProtectionController);
    }

    @Override // javax.inject.Provider
    public SensitiveContentCoordinatorImpl get() {
        return newInstance((DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get());
    }
}
