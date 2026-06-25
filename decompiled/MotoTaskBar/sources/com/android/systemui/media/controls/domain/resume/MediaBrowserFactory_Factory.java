package com.android.systemui.media.controls.domain.resume;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaBrowserFactory_Factory implements Factory {
    private final Provider contextProvider;

    public MediaBrowserFactory_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MediaBrowserFactory_Factory create(Provider provider) {
        return new MediaBrowserFactory_Factory(provider);
    }

    public static MediaBrowserFactory newInstance(Context context) {
        return new MediaBrowserFactory(context);
    }

    @Override // javax.inject.Provider
    public MediaBrowserFactory get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
