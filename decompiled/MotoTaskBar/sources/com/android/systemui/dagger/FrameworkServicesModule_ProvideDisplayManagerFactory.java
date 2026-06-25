package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideDisplayManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideDisplayManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDisplayManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideDisplayManagerFactory(provider);
    }

    public static DisplayManager provideDisplayManager(Context context) {
        DisplayManager displayManagerProvideDisplayManager = FrameworkServicesModule.provideDisplayManager(context);
        displayManagerProvideDisplayManager.getClass();
        return displayManagerProvideDisplayManager;
    }

    @Override // javax.inject.Provider
    public DisplayManager get() {
        return provideDisplayManager((Context) this.contextProvider.get());
    }
}
