package com.android.systemui.statusbar.notification.collection.provider;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SectionStyleProvider_Factory implements Factory {
    private final Provider highPriorityProvider;

    public SectionStyleProvider_Factory(Provider provider) {
        this.highPriorityProvider = provider;
    }

    public static SectionStyleProvider_Factory create(Provider provider) {
        return new SectionStyleProvider_Factory(provider);
    }

    public static SectionStyleProvider newInstance(HighPriorityProvider highPriorityProvider) {
        return new SectionStyleProvider(highPriorityProvider);
    }

    @Override // javax.inject.Provider
    public SectionStyleProvider get() {
        return newInstance((HighPriorityProvider) this.highPriorityProvider.get());
    }
}
