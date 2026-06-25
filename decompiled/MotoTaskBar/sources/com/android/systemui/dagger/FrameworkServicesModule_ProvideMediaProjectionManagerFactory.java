package com.android.systemui.dagger;

import android.content.Context;
import android.media.projection.MediaProjectionManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideMediaProjectionManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideMediaProjectionManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideMediaProjectionManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideMediaProjectionManagerFactory(provider);
    }

    public static MediaProjectionManager provideMediaProjectionManager(Context context) {
        MediaProjectionManager mediaProjectionManagerProvideMediaProjectionManager = FrameworkServicesModule.provideMediaProjectionManager(context);
        mediaProjectionManagerProvideMediaProjectionManager.getClass();
        return mediaProjectionManagerProvideMediaProjectionManager;
    }

    @Override // javax.inject.Provider
    public MediaProjectionManager get() {
        return provideMediaProjectionManager((Context) this.contextProvider.get());
    }
}
