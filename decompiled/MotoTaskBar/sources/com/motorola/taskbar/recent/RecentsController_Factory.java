package com.motorola.taskbar.recent;

import android.content.Context;
import com.android.systemui.plugins.moto.ActivityStarter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class RecentsController_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider contextProvider;

    public RecentsController_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.activityStarterProvider = provider2;
    }

    public static RecentsController_Factory create(Provider provider, Provider provider2) {
        return new RecentsController_Factory(provider, provider2);
    }

    public static RecentsController newInstance(Context context, ActivityStarter activityStarter) {
        return new RecentsController(context, activityStarter);
    }

    @Override // javax.inject.Provider
    public RecentsController get() {
        return newInstance((Context) this.contextProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
    }
}
