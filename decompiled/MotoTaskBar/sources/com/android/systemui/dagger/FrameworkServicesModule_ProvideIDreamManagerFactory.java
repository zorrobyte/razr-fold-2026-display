package com.android.systemui.dagger;

import android.service.dreams.IDreamManager;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideIDreamManagerFactory implements Factory {

    abstract class InstanceHolder {
        static final FrameworkServicesModule_ProvideIDreamManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIDreamManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIDreamManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IDreamManager provideIDreamManager() {
        IDreamManager iDreamManagerProvideIDreamManager = FrameworkServicesModule.provideIDreamManager();
        iDreamManagerProvideIDreamManager.getClass();
        return iDreamManagerProvideIDreamManager;
    }

    @Override // javax.inject.Provider
    public IDreamManager get() {
        return provideIDreamManager();
    }
}
