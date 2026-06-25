package com.android.systemui.dagger;

import android.app.IWallpaperManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIWallPaperManagerFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIWallPaperManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWallPaperManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIWallPaperManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IWallpaperManager provideIWallPaperManager() {
        return FrameworkServicesModule.provideIWallPaperManager();
    }

    @Override // javax.inject.Provider
    public IWallpaperManager get() {
        return provideIWallPaperManager();
    }
}
