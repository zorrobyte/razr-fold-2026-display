package com.android.systemui.dagger;

import android.content.Context;
import android.view.WindowManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideWindowManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideWindowManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideWindowManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideWindowManagerFactory(provider);
    }

    public static WindowManager provideWindowManager(Context context) {
        WindowManager windowManagerProvideWindowManager = FrameworkServicesModule.provideWindowManager(context);
        windowManagerProvideWindowManager.getClass();
        return windowManagerProvideWindowManager;
    }

    @Override // javax.inject.Provider
    public WindowManager get() {
        return provideWindowManager((Context) this.contextProvider.get());
    }
}
