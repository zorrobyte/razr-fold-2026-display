package com.android.systemui.dagger;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class SharedLibraryModule_ProvideActivityManagerWrapperFactory implements Factory {
    private final SharedLibraryModule module;

    public SharedLibraryModule_ProvideActivityManagerWrapperFactory(SharedLibraryModule sharedLibraryModule) {
        this.module = sharedLibraryModule;
    }

    public static SharedLibraryModule_ProvideActivityManagerWrapperFactory create(SharedLibraryModule sharedLibraryModule) {
        return new SharedLibraryModule_ProvideActivityManagerWrapperFactory(sharedLibraryModule);
    }

    public static ActivityManagerWrapper provideActivityManagerWrapper(SharedLibraryModule sharedLibraryModule) {
        ActivityManagerWrapper activityManagerWrapperProvideActivityManagerWrapper = sharedLibraryModule.provideActivityManagerWrapper();
        activityManagerWrapperProvideActivityManagerWrapper.getClass();
        return activityManagerWrapperProvideActivityManagerWrapper;
    }

    @Override // javax.inject.Provider
    public ActivityManagerWrapper get() {
        return provideActivityManagerWrapper(this.module);
    }
}
