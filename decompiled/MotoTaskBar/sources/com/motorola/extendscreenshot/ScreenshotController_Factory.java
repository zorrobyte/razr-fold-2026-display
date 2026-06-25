package com.motorola.extendscreenshot;

import android.content.Context;
import android.os.Handler;
import com.motorola.taskbar.TaskBarServiceProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ScreenshotController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider handlerProvider;
    private final Provider taskBarServiceProxyProvider;

    public ScreenshotController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.taskBarServiceProxyProvider = provider2;
        this.handlerProvider = provider3;
    }

    public static ScreenshotController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ScreenshotController_Factory(provider, provider2, provider3);
    }

    public static ScreenshotController newInstance(Context context, TaskBarServiceProxy taskBarServiceProxy, Handler handler) {
        return new ScreenshotController(context, taskBarServiceProxy, handler);
    }

    @Override // javax.inject.Provider
    public ScreenshotController get() {
        return newInstance((Context) this.contextProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (Handler) this.handlerProvider.get());
    }
}
