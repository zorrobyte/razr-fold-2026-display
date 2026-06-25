package com.android.systemui.media.controls.data.repository;

import android.content.Context;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaFilterRepository_Factory implements Factory {
    private final Provider applicationContextProvider;
    private final Provider configurationControllerProvider;
    private final Provider systemClockProvider;

    public MediaFilterRepository_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.applicationContextProvider = provider;
        this.systemClockProvider = provider2;
        this.configurationControllerProvider = provider3;
    }

    public static MediaFilterRepository_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new MediaFilterRepository_Factory(provider, provider2, provider3);
    }

    public static MediaFilterRepository newInstance(Context context, SystemClock systemClock, ConfigurationController configurationController) {
        return new MediaFilterRepository(context, systemClock, configurationController);
    }

    @Override // javax.inject.Provider
    public MediaFilterRepository get() {
        return newInstance((Context) this.applicationContextProvider.get(), (SystemClock) this.systemClockProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}
