package com.android.systemui.media.controls.util;

import com.android.systemui.flags.FeatureFlagsClassic;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaFlags_Factory implements Factory {
    private final Provider featureFlagsProvider;

    public MediaFlags_Factory(Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static MediaFlags_Factory create(Provider provider) {
        return new MediaFlags_Factory(provider);
    }

    public static MediaFlags newInstance(FeatureFlagsClassic featureFlagsClassic) {
        return new MediaFlags(featureFlagsClassic);
    }

    @Override // javax.inject.Provider
    public MediaFlags get() {
        return newInstance((FeatureFlagsClassic) this.featureFlagsProvider.get());
    }
}
