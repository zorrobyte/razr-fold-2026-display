package com.android.systemui.graphics;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* JADX INFO: loaded from: classes.dex */
public final class ImageLoader_Factory implements Factory {
    private final Provider backgroundDispatcherProvider;
    private final Provider defaultContextProvider;

    public ImageLoader_Factory(Provider provider, Provider provider2) {
        this.defaultContextProvider = provider;
        this.backgroundDispatcherProvider = provider2;
    }

    public static ImageLoader_Factory create(Provider provider, Provider provider2) {
        return new ImageLoader_Factory(provider, provider2);
    }

    public static ImageLoader newInstance(Context context, CoroutineDispatcher coroutineDispatcher) {
        return new ImageLoader(context, coroutineDispatcher);
    }

    @Override // javax.inject.Provider
    public ImageLoader get() {
        return newInstance((Context) this.defaultContextProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}
