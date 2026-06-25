package com.android.systemui.dagger;

import android.app.IActivityManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIActivityManagerFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIActivityManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIActivityManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIActivityManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IActivityManager provideIActivityManager() {
        IActivityManager iActivityManagerProvideIActivityManager = FrameworkServicesModule.provideIActivityManager();
        iActivityManagerProvideIActivityManager.getClass();
        return iActivityManagerProvideIActivityManager;
    }

    @Override // javax.inject.Provider
    public IActivityManager get() {
        return provideIActivityManager();
    }
}
