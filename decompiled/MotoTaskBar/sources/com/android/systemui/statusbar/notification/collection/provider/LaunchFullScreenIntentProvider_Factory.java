package com.android.systemui.statusbar.notification.collection.provider;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class LaunchFullScreenIntentProvider_Factory implements Factory {

    abstract class InstanceHolder {
        static final LaunchFullScreenIntentProvider_Factory INSTANCE = new LaunchFullScreenIntentProvider_Factory();
    }

    public static LaunchFullScreenIntentProvider_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LaunchFullScreenIntentProvider newInstance() {
        return new LaunchFullScreenIntentProvider();
    }

    @Override // javax.inject.Provider
    public LaunchFullScreenIntentProvider get() {
        return newInstance();
    }
}
