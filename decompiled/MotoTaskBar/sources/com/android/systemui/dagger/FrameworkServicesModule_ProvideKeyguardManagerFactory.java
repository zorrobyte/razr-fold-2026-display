package com.android.systemui.dagger;

import android.app.KeyguardManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideKeyguardManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideKeyguardManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideKeyguardManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideKeyguardManagerFactory(provider);
    }

    public static KeyguardManager provideKeyguardManager(Context context) {
        KeyguardManager keyguardManagerProvideKeyguardManager = FrameworkServicesModule.provideKeyguardManager(context);
        keyguardManagerProvideKeyguardManager.getClass();
        return keyguardManagerProvideKeyguardManager;
    }

    @Override // javax.inject.Provider
    public KeyguardManager get() {
        return provideKeyguardManager((Context) this.contextProvider.get());
    }
}
