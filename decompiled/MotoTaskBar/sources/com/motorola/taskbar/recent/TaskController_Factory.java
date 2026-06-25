package com.motorola.taskbar.recent;

import android.content.Context;
import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskController_Factory implements Factory {
    private final Provider bgLooperProvider;
    private final Provider contextProvider;
    private final Provider mainLooperProvider;

    public TaskController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.mainLooperProvider = provider2;
        this.bgLooperProvider = provider3;
    }

    public static TaskController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new TaskController_Factory(provider, provider2, provider3);
    }

    public static TaskController newInstance(Context context, Looper looper, Looper looper2) {
        return new TaskController(context, looper, looper2);
    }

    @Override // javax.inject.Provider
    public TaskController get() {
        return newInstance((Context) this.contextProvider.get(), (Looper) this.mainLooperProvider.get(), (Looper) this.bgLooperProvider.get());
    }
}
