package com.motorola.taskbar.bar;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ExternalDisplayModeManager_Factory implements Factory {
    private final Provider contextProvider;

    public ExternalDisplayModeManager_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static ExternalDisplayModeManager_Factory create(Provider provider) {
        return new ExternalDisplayModeManager_Factory(provider);
    }

    public static ExternalDisplayModeManager newInstance(Context context) {
        return new ExternalDisplayModeManager(context);
    }

    @Override // javax.inject.Provider
    public ExternalDisplayModeManager get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
