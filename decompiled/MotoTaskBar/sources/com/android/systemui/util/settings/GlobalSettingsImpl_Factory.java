package com.android.systemui.util.settings;

import android.content.ContentResolver;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GlobalSettingsImpl_Factory implements Factory {
    private final Provider contentResolverProvider;

    public GlobalSettingsImpl_Factory(Provider provider) {
        this.contentResolverProvider = provider;
    }

    public static GlobalSettingsImpl_Factory create(Provider provider) {
        return new GlobalSettingsImpl_Factory(provider);
    }

    public static GlobalSettingsImpl newInstance(ContentResolver contentResolver) {
        return new GlobalSettingsImpl(contentResolver);
    }

    @Override // javax.inject.Provider
    public GlobalSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get());
    }
}
