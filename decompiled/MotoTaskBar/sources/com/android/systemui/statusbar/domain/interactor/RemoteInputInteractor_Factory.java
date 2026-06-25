package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.RemoteInputRepository;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputInteractor_Factory implements Factory {
    private final Provider remoteInputRepositoryProvider;

    public RemoteInputInteractor_Factory(Provider provider) {
        this.remoteInputRepositoryProvider = provider;
    }

    public static RemoteInputInteractor_Factory create(Provider provider) {
        return new RemoteInputInteractor_Factory(provider);
    }

    public static RemoteInputInteractor newInstance(RemoteInputRepository remoteInputRepository) {
        return new RemoteInputInteractor(remoteInputRepository);
    }

    @Override // javax.inject.Provider
    public RemoteInputInteractor get() {
        return newInstance((RemoteInputRepository) this.remoteInputRepositoryProvider.get());
    }
}
