package com.android.systemui.statusbar.notification.collection.coordinator.dagger;

import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class CoordinatorsModule_NotifCoordinatorsFactory implements Factory {
    private final Provider factoryProvider;

    public CoordinatorsModule_NotifCoordinatorsFactory(Provider provider) {
        this.factoryProvider = provider;
    }

    public static CoordinatorsModule_NotifCoordinatorsFactory create(Provider provider) {
        return new CoordinatorsModule_NotifCoordinatorsFactory(provider);
    }

    public static NotifCoordinators notifCoordinators(CoordinatorsSubcomponent.Factory factory) {
        NotifCoordinators notifCoordinators = CoordinatorsModule.notifCoordinators(factory);
        notifCoordinators.getClass();
        return notifCoordinators;
    }

    @Override // javax.inject.Provider
    public NotifCoordinators get() {
        return notifCoordinators((CoordinatorsSubcomponent.Factory) this.factoryProvider.get());
    }
}
