package com.motorola.taskbar.model;

import android.content.Context;
import android.os.Handler;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DisplayConfigurationController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider handlerProvider;

    public DisplayConfigurationController_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
    }

    public static DisplayConfigurationController_Factory create(Provider provider, Provider provider2) {
        return new DisplayConfigurationController_Factory(provider, provider2);
    }

    public static DisplayConfigurationController newInstance(Context context, Handler handler) {
        return new DisplayConfigurationController(context, handler);
    }

    @Override // javax.inject.Provider
    public DisplayConfigurationController get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get());
    }
}
