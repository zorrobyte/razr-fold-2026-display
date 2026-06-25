package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideResourcesFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideResourcesFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideResourcesFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideResourcesFactory(provider);
    }

    public static Resources provideResources(Context context) {
        Resources resourcesProvideResources = FrameworkServicesModule.provideResources(context);
        resourcesProvideResources.getClass();
        return resourcesProvideResources;
    }

    @Override // javax.inject.Provider
    public Resources get() {
        return provideResources((Context) this.contextProvider.get());
    }
}
