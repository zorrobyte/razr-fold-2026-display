package com.android.systemui.util.concurrency;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ConcurrencyModule_ProvideNotifInflationLooperFactory implements Factory {
    private final Provider bgLooperProvider;

    public ConcurrencyModule_ProvideNotifInflationLooperFactory(Provider provider) {
        this.bgLooperProvider = provider;
    }

    public static ConcurrencyModule_ProvideNotifInflationLooperFactory create(Provider provider) {
        return new ConcurrencyModule_ProvideNotifInflationLooperFactory(provider);
    }

    public static Looper provideNotifInflationLooper(Looper looper) {
        Looper looperProvideNotifInflationLooper = ConcurrencyModule.provideNotifInflationLooper(looper);
        looperProvideNotifInflationLooper.getClass();
        return looperProvideNotifInflationLooper;
    }

    @Override // javax.inject.Provider
    public Looper get() {
        return provideNotifInflationLooper((Looper) this.bgLooperProvider.get());
    }
}
