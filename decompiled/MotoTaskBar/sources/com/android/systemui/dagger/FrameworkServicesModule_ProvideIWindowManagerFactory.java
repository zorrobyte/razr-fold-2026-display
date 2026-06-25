package com.android.systemui.dagger;

import android.view.IWindowManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIWindowManagerFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIWindowManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWindowManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIWindowManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IWindowManager provideIWindowManager() {
        IWindowManager iWindowManagerProvideIWindowManager = FrameworkServicesModule.provideIWindowManager();
        iWindowManagerProvideIWindowManager.getClass();
        return iWindowManagerProvideIWindowManager;
    }

    @Override // javax.inject.Provider
    public IWindowManager get() {
        return provideIWindowManager();
    }
}
