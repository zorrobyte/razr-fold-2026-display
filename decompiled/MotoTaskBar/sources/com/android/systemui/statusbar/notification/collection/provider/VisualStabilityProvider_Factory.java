package com.android.systemui.statusbar.notification.collection.provider;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class VisualStabilityProvider_Factory implements Factory {

    abstract class InstanceHolder {
        static final VisualStabilityProvider_Factory INSTANCE = new VisualStabilityProvider_Factory();
    }

    public static VisualStabilityProvider_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static VisualStabilityProvider newInstance() {
        return new VisualStabilityProvider();
    }

    @Override // javax.inject.Provider
    public VisualStabilityProvider get() {
        return newInstance();
    }
}
