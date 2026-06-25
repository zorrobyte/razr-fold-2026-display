package com.android.systemui.statusbar.policy;

import com.android.internal.statusbar.IStatusBarService;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputUriController_Factory implements Factory {
    private final Provider statusBarServiceProvider;

    public RemoteInputUriController_Factory(Provider provider) {
        this.statusBarServiceProvider = provider;
    }

    public static RemoteInputUriController_Factory create(Provider provider) {
        return new RemoteInputUriController_Factory(provider);
    }

    public static RemoteInputUriController newInstance(IStatusBarService iStatusBarService) {
        return new RemoteInputUriController(iStatusBarService);
    }

    @Override // javax.inject.Provider
    public RemoteInputUriController get() {
        return newInstance((IStatusBarService) this.statusBarServiceProvider.get());
    }
}
