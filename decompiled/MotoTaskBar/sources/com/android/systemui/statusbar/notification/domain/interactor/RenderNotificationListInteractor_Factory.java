package com.android.systemui.statusbar.notification.domain.interactor;

import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RenderNotificationListInteractor_Factory implements Factory {
    private final Provider repositoryProvider;
    private final Provider sectionStyleProvider;

    public RenderNotificationListInteractor_Factory(Provider provider, Provider provider2) {
        this.repositoryProvider = provider;
        this.sectionStyleProvider = provider2;
    }

    public static RenderNotificationListInteractor_Factory create(Provider provider, Provider provider2) {
        return new RenderNotificationListInteractor_Factory(provider, provider2);
    }

    public static RenderNotificationListInteractor newInstance(ActiveNotificationListRepository activeNotificationListRepository, SectionStyleProvider sectionStyleProvider) {
        return new RenderNotificationListInteractor(activeNotificationListRepository, sectionStyleProvider);
    }

    @Override // javax.inject.Provider
    public RenderNotificationListInteractor get() {
        return newInstance((ActiveNotificationListRepository) this.repositoryProvider.get(), (SectionStyleProvider) this.sectionStyleProvider.get());
    }
}
