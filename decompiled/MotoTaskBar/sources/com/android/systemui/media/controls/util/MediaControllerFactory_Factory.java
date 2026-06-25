package com.android.systemui.media.controls.util;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaControllerFactory_Factory implements Factory {
    private final Provider contextProvider;

    public MediaControllerFactory_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static MediaControllerFactory_Factory create(Provider provider) {
        return new MediaControllerFactory_Factory(provider);
    }

    public static MediaControllerFactory newInstance(Context context) {
        return new MediaControllerFactory(context);
    }

    @Override // javax.inject.Provider
    public MediaControllerFactory get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
