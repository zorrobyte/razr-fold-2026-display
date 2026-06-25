package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Optional;

/* JADX INFO: loaded from: classes.dex */
public final class FooterViewModelModule_ProvideOptionalFactory implements Factory {
    private final Provider activeNotificationsInteractorProvider;
    private final Provider seenNotificationsInteractorProvider;

    public FooterViewModelModule_ProvideOptionalFactory(Provider provider, Provider provider2) {
        this.activeNotificationsInteractorProvider = provider;
        this.seenNotificationsInteractorProvider = provider2;
    }

    public static FooterViewModelModule_ProvideOptionalFactory create(Provider provider, Provider provider2) {
        return new FooterViewModelModule_ProvideOptionalFactory(provider, provider2);
    }

    public static Optional provideOptional(javax.inject.Provider provider, javax.inject.Provider provider2) {
        Optional optionalProvideOptional = FooterViewModelModule.INSTANCE.provideOptional(provider, provider2);
        optionalProvideOptional.getClass();
        return optionalProvideOptional;
    }

    @Override // javax.inject.Provider
    public Optional get() {
        return provideOptional(this.activeNotificationsInteractorProvider, this.seenNotificationsInteractorProvider);
    }
}
