package com.android.systemui.dagger;

import android.content.Context;
import android.os.UserManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideUserManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideUserManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideUserManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideUserManagerFactory(provider);
    }

    public static UserManager provideUserManager(Context context) {
        UserManager userManagerProvideUserManager = FrameworkServicesModule.provideUserManager(context);
        userManagerProvideUserManager.getClass();
        return userManagerProvideUserManager;
    }

    @Override // javax.inject.Provider
    public UserManager get() {
        return provideUserManager((Context) this.contextProvider.get());
    }
}
