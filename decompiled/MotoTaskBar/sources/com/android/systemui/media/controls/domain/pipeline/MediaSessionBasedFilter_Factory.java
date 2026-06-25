package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class MediaSessionBasedFilter_Factory implements Factory {
    private final Provider backgroundExecutorProvider;
    private final Provider contextProvider;
    private final Provider foregroundExecutorProvider;
    private final Provider sessionManagerProvider;

    public MediaSessionBasedFilter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.sessionManagerProvider = provider2;
        this.foregroundExecutorProvider = provider3;
        this.backgroundExecutorProvider = provider4;
    }

    public static MediaSessionBasedFilter_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new MediaSessionBasedFilter_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaSessionBasedFilter newInstance(Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        return new MediaSessionBasedFilter(context, mediaSessionManager, executor, executor2);
    }

    @Override // javax.inject.Provider
    public MediaSessionBasedFilter get() {
        return newInstance((Context) this.contextProvider.get(), (MediaSessionManager) this.sessionManagerProvider.get(), (Executor) this.foregroundExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}
