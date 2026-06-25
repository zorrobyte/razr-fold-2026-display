package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.util.concurrency.RepeatableExecutor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SeekBarViewModel_Factory implements Factory {
    private final Provider bgExecutorProvider;

    public SeekBarViewModel_Factory(Provider provider) {
        this.bgExecutorProvider = provider;
    }

    public static SeekBarViewModel_Factory create(Provider provider) {
        return new SeekBarViewModel_Factory(provider);
    }

    public static SeekBarViewModel newInstance(RepeatableExecutor repeatableExecutor) {
        return new SeekBarViewModel(repeatableExecutor);
    }

    @Override // javax.inject.Provider
    public SeekBarViewModel get() {
        return newInstance((RepeatableExecutor) this.bgExecutorProvider.get());
    }
}
