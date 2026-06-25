package com.android.systemui.media.controls.util;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaFeatureFlag_Factory implements Factory {
    private final Provider contextProvider;

    public MediaFeatureFlag_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MediaFeatureFlag_Factory create(Provider provider) {
        return new MediaFeatureFlag_Factory(provider);
    }

    public static MediaFeatureFlag newInstance(Context context) {
        return new MediaFeatureFlag(context);
    }

    @Override // javax.inject.Provider
    public MediaFeatureFlag get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
