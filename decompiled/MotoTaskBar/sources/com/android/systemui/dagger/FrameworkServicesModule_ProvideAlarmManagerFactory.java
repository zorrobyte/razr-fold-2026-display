package com.android.systemui.dagger;

import android.app.AlarmManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideAlarmManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideAlarmManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAlarmManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideAlarmManagerFactory(provider);
    }

    public static AlarmManager provideAlarmManager(Context context) {
        AlarmManager alarmManagerProvideAlarmManager = FrameworkServicesModule.provideAlarmManager(context);
        alarmManagerProvideAlarmManager.getClass();
        return alarmManagerProvideAlarmManager;
    }

    @Override // javax.inject.Provider
    public AlarmManager get() {
        return provideAlarmManager((Context) this.contextProvider.get());
    }
}
