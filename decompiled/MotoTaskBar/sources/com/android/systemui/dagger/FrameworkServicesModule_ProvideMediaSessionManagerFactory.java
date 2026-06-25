package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideMediaSessionManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideMediaSessionManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideMediaSessionManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideMediaSessionManagerFactory(provider);
    }

    public static MediaSessionManager provideMediaSessionManager(Context context) {
        MediaSessionManager mediaSessionManagerProvideMediaSessionManager = FrameworkServicesModule.provideMediaSessionManager(context);
        mediaSessionManagerProvideMediaSessionManager.getClass();
        return mediaSessionManagerProvideMediaSessionManager;
    }

    @Override // javax.inject.Provider
    public MediaSessionManager get() {
        return provideMediaSessionManager((Context) this.contextProvider.get());
    }
}
