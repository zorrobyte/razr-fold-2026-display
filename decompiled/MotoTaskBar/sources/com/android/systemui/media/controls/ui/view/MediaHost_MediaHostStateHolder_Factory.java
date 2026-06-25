package com.android.systemui.media.controls.ui.view;

import com.android.systemui.media.controls.ui.view.MediaHost;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class MediaHost_MediaHostStateHolder_Factory implements Factory {

    abstract class InstanceHolder {
        static final MediaHost_MediaHostStateHolder_Factory INSTANCE = new MediaHost_MediaHostStateHolder_Factory();
    }

    public static MediaHost_MediaHostStateHolder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaHost.MediaHostStateHolder newInstance() {
        return new MediaHost.MediaHostStateHolder();
    }

    @Override // javax.inject.Provider
    public MediaHost.MediaHostStateHolder get() {
        return newInstance();
    }
}
