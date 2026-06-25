package com.motorola.trackpad;

import android.content.Context;
import com.motorola.taskbar.MotoFeature;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class TrackpadGestureHandler_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider motoFeatureProvider;

    public TrackpadGestureHandler_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.motoFeatureProvider = provider2;
    }

    public static TrackpadGestureHandler_Factory create(Provider provider, Provider provider2) {
        return new TrackpadGestureHandler_Factory(provider, provider2);
    }

    public static TrackpadGestureHandler newInstance(Context context, MotoFeature motoFeature) {
        return new TrackpadGestureHandler(context, motoFeature);
    }

    @Override // javax.inject.Provider
    public TrackpadGestureHandler get() {
        return newInstance((Context) this.contextProvider.get(), (MotoFeature) this.motoFeatureProvider.get());
    }
}
