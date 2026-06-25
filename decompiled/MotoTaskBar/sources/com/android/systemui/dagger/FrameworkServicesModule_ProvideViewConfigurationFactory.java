package com.android.systemui.dagger;

import android.content.Context;
import android.view.ViewConfiguration;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideViewConfigurationFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideViewConfigurationFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideViewConfigurationFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideViewConfigurationFactory(provider);
    }

    public static ViewConfiguration provideViewConfiguration(Context context) {
        ViewConfiguration viewConfigurationProvideViewConfiguration = FrameworkServicesModule.provideViewConfiguration(context);
        viewConfigurationProvideViewConfiguration.getClass();
        return viewConfigurationProvideViewConfiguration;
    }

    @Override // javax.inject.Provider
    public ViewConfiguration get() {
        return provideViewConfiguration((Context) this.contextProvider.get());
    }
}
