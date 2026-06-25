package com.android.systemui.statusbar.notification.collection.provider;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SectionHeaderVisibilityProvider_Factory implements Factory {
    private final Provider contextProvider;

    public SectionHeaderVisibilityProvider_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static SectionHeaderVisibilityProvider_Factory create(Provider provider) {
        return new SectionHeaderVisibilityProvider_Factory(provider);
    }

    public static SectionHeaderVisibilityProvider newInstance(Context context) {
        return new SectionHeaderVisibilityProvider(context);
    }

    @Override // javax.inject.Provider
    public SectionHeaderVisibilityProvider get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
