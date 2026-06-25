package com.android.systemui.dagger;

import android.content.ContentResolver;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideContentResolverFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideContentResolverFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideContentResolverFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideContentResolverFactory(provider);
    }

    public static ContentResolver provideContentResolver(Context context) {
        ContentResolver contentResolverProvideContentResolver = FrameworkServicesModule.provideContentResolver(context);
        contentResolverProvideContentResolver.getClass();
        return contentResolverProvideContentResolver;
    }

    @Override // javax.inject.Provider
    public ContentResolver get() {
        return provideContentResolver((Context) this.contextProvider.get());
    }
}
