package com.android.systemui.media.controls.data.repository;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.util.MediaFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDataRepository_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider mediaFlagsProvider;

    public MediaDataRepository_Factory(Provider provider, Provider provider2) {
        this.mediaFlagsProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public static MediaDataRepository_Factory create(Provider provider, Provider provider2) {
        return new MediaDataRepository_Factory(provider, provider2);
    }

    public static MediaDataRepository newInstance(MediaFlags mediaFlags, DumpManager dumpManager) {
        return new MediaDataRepository(mediaFlags, dumpManager);
    }

    @Override // javax.inject.Provider
    public MediaDataRepository get() {
        return newInstance((MediaFlags) this.mediaFlagsProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
