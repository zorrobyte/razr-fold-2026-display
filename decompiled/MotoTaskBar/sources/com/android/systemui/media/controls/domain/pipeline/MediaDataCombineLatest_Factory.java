package com.android.systemui.media.controls.domain.pipeline;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDataCombineLatest_Factory implements Factory {

    abstract class InstanceHolder {
        static final MediaDataCombineLatest_Factory INSTANCE = new MediaDataCombineLatest_Factory();
    }

    public static MediaDataCombineLatest_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaDataCombineLatest newInstance() {
        return new MediaDataCombineLatest();
    }

    @Override // javax.inject.Provider
    public MediaDataCombineLatest get() {
        return newInstance();
    }
}
