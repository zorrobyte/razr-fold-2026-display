package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideMainLooperFactory implements Factory {

    abstract class InstanceHolder {
        static final ConcurrencyModule_ProvideMainLooperFactory INSTANCE = new ConcurrencyModule_ProvideMainLooperFactory();
    }

    public static ConcurrencyModule_ProvideMainLooperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Looper provideMainLooper() {
        Looper looperProvideMainLooper = ConcurrencyModule.provideMainLooper();
        looperProvideMainLooper.getClass();
        return looperProvideMainLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideMainLooper();
    }
}
