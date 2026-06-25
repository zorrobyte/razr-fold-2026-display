package com.motorola.taskbar;

import android.content.Context;
import android.os.Handler;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskBarServiceProxy_Factory implements Factory {
    private final Provider bgHandlerProvider;
    private final Provider contextProvider;
    private final Provider handlerProvider;

    public TaskBarServiceProxy_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.bgHandlerProvider = provider3;
    }

    public static TaskBarServiceProxy_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new TaskBarServiceProxy_Factory(provider, provider2, provider3);
    }

    public static TaskBarServiceProxy newInstance(Context context, Handler handler, Handler handler2) {
        return new TaskBarServiceProxy(context, handler, handler2);
    }

    @Override // javax.inject.Provider
    public TaskBarServiceProxy get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
