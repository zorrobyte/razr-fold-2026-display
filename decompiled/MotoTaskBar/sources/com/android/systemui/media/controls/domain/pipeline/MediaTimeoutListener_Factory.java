package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaTimeoutListener_Factory implements Factory {
    private final Provider mainExecutorProvider;
    private final Provider mediaControllerFactoryProvider;
    private final Provider mediaFlagsProvider;
    private final Provider systemClockProvider;

    public MediaTimeoutListener_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.mediaControllerFactoryProvider = provider;
        this.mainExecutorProvider = provider2;
        this.systemClockProvider = provider3;
        this.mediaFlagsProvider = provider4;
    }

    public static MediaTimeoutListener_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new MediaTimeoutListener_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaTimeoutListener newInstance(MediaControllerFactory mediaControllerFactory, DelayableExecutor delayableExecutor, SystemClock systemClock, MediaFlags mediaFlags) {
        return new MediaTimeoutListener(mediaControllerFactory, delayableExecutor, systemClock, mediaFlags);
    }

    @Override // javax.inject.Provider
    public MediaTimeoutListener get() {
        return newInstance((MediaControllerFactory) this.mediaControllerFactoryProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaFlags) this.mediaFlagsProvider.get());
    }
}
