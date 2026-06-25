package com.android.systemui.media.controls.ui.controller;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class MediaHostStatesManager_Factory implements Factory {

    abstract class InstanceHolder {
        static final MediaHostStatesManager_Factory INSTANCE = new MediaHostStatesManager_Factory();
    }

    public static MediaHostStatesManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaHostStatesManager newInstance() {
        return new MediaHostStatesManager();
    }

    @Override // javax.inject.Provider
    public MediaHostStatesManager get() {
        return newInstance();
    }
}
