package com.motorola.taskbar.model;

import android.content.Context;
import android.os.Handler;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DisplayWindowManager_Factory implements Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;

    public DisplayWindowManager_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
    }

    public static DisplayWindowManager_Factory create(Provider provider, Provider provider2) {
        return new DisplayWindowManager_Factory(provider, provider2);
    }

    public static DisplayWindowManager newInstance(Context context, Handler handler) {
        return new DisplayWindowManager(context, handler);
    }

    @Override // javax.inject.Provider
    public DisplayWindowManager get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
