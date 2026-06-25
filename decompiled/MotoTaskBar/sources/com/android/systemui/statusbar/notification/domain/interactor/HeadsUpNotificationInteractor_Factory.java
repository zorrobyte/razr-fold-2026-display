package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpNotificationInteractor_Factory implements Factory {
    private final Provider repositoryProvider;

    public HeadsUpNotificationInteractor_Factory(Provider provider) {
        this.repositoryProvider = provider;
    }

    public static HeadsUpNotificationInteractor_Factory create(Provider provider) {
        return new HeadsUpNotificationInteractor_Factory(provider);
    }

    public static HeadsUpNotificationInteractor newInstance(HeadsUpRepository headsUpRepository) {
        return new HeadsUpNotificationInteractor(headsUpRepository);
    }

    @Override // javax.inject.Provider
    public HeadsUpNotificationInteractor get() {
        return newInstance((HeadsUpRepository) this.repositoryProvider.get());
    }
}
