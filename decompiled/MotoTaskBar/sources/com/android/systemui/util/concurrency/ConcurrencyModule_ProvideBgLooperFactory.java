package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideBgLooperFactory implements Factory {

    abstract class InstanceHolder {
        static final ConcurrencyModule_ProvideBgLooperFactory INSTANCE = new ConcurrencyModule_ProvideBgLooperFactory();
    }

    public static ConcurrencyModule_ProvideBgLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideBgLooper() {
        Looper looperProvideBgLooper = ConcurrencyModule.provideBgLooper();
        looperProvideBgLooper.getClass();
        return looperProvideBgLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideBgLooper();
    }
}
