package com.android.systemui.flags;

import android.content.res.Resources;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FeatureFlagsClassicRelease_Factory implements Factory {
    private final Provider resourcesProvider;
    private final Provider systemPropertiesProvider;

    public FeatureFlagsClassicRelease_Factory(Provider provider, Provider provider2) {
        this.resourcesProvider = provider;
        this.systemPropertiesProvider = provider2;
    }

    public static FeatureFlagsClassicRelease_Factory create(Provider provider, Provider provider2) {
        return new FeatureFlagsClassicRelease_Factory(provider, provider2);
    }

    public static FeatureFlagsClassicRelease newInstance(Resources resources, SystemPropertiesHelper systemPropertiesHelper) {
        return new FeatureFlagsClassicRelease(resources, systemPropertiesHelper);
    }

    @Override // javax.inject.Provider
    public FeatureFlagsClassicRelease get() {
        return newInstance((Resources) this.resourcesProvider.get(), (SystemPropertiesHelper) this.systemPropertiesProvider.get());
    }
}
