package com.motorola.taskbar.recent;

import android.content.Context;
import android.os.Looper;
import com.motorola.taskbar.MotoFeature;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskSyncreticController_Factory implements Factory {
    private final Provider bgLooperProvider;
    private final Provider contextProvider;
    private final Provider controllerProvider;
    private final Provider mainLooperProvider;
    private final Provider motoFeatureProvider;

    public TaskSyncreticController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.controllerProvider = provider2;
        this.mainLooperProvider = provider3;
        this.bgLooperProvider = provider4;
        this.motoFeatureProvider = provider5;
    }

    public static TaskSyncreticController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new TaskSyncreticController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static TaskSyncreticController newInstance(Context context, TaskController taskController, Looper looper, Looper looper2, MotoFeature motoFeature) {
        return new TaskSyncreticController(context, taskController, looper, looper2, motoFeature);
    }

    @Override // javax.inject.Provider
    public TaskSyncreticController get() {
        return newInstance((Context) this.contextProvider.get(), (TaskController) this.controllerProvider.get(), (Looper) this.mainLooperProvider.get(), (Looper) this.bgLooperProvider.get(), (MotoFeature) this.motoFeatureProvider.get());
    }
}
