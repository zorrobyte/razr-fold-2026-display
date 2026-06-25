package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory implements Factory {
    private final Provider contextProvider;
    private final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory(FrameworkServicesModule frameworkServicesModule, Provider provider) {
        this.module = frameworkServicesModule;
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory create(FrameworkServicesModule frameworkServicesModule, Provider provider) {
        return new FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory(frameworkServicesModule, provider);
    }

    public static AmbientDisplayConfiguration provideAmbientDisplayConfiguration(FrameworkServicesModule frameworkServicesModule, Context context) {
        AmbientDisplayConfiguration ambientDisplayConfigurationProvideAmbientDisplayConfiguration = frameworkServicesModule.provideAmbientDisplayConfiguration(context);
        ambientDisplayConfigurationProvideAmbientDisplayConfiguration.getClass();
        return ambientDisplayConfigurationProvideAmbientDisplayConfiguration;
    }

    @Override // javax.inject.Provider
    public AmbientDisplayConfiguration get() {
        return provideAmbientDisplayConfiguration(this.module, (Context) this.contextProvider.get());
    }
}
