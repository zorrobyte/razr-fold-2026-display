package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class OnUserInteractionCallbackImpl_Factory implements Factory {
    private final Provider headsUpManagerProvider;
    private final Provider notifCollectionProvider;
    private final Provider visibilityProvider;
    private final Provider visualStabilityCoordinatorProvider;

    public OnUserInteractionCallbackImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.visibilityProvider = provider;
        this.notifCollectionProvider = provider2;
        this.headsUpManagerProvider = provider3;
        this.visualStabilityCoordinatorProvider = provider4;
    }

    public static OnUserInteractionCallbackImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new OnUserInteractionCallbackImpl_Factory(provider, provider2, provider3, provider4);
    }

    public static OnUserInteractionCallbackImpl newInstance(NotificationVisibilityProvider notificationVisibilityProvider, NotifCollection notifCollection, HeadsUpManager headsUpManager, VisualStabilityCoordinator visualStabilityCoordinator) {
        return new OnUserInteractionCallbackImpl(notificationVisibilityProvider, notifCollection, headsUpManager, visualStabilityCoordinator);
    }

    @Override // javax.inject.Provider
    public OnUserInteractionCallbackImpl get() {
        return newInstance((NotificationVisibilityProvider) this.visibilityProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (VisualStabilityCoordinator) this.visualStabilityCoordinatorProvider.get());
    }
}
