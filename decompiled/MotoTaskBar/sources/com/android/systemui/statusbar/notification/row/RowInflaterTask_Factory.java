package com.android.systemui.statusbar.notification.row;

import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RowInflaterTask_Factory implements Factory {
    private final Provider loggerProvider;
    private final Provider systemClockProvider;

    public RowInflaterTask_Factory(Provider provider, Provider provider2) {
        this.systemClockProvider = provider;
        this.loggerProvider = provider2;
    }

    public static RowInflaterTask_Factory create(Provider provider, Provider provider2) {
        return new RowInflaterTask_Factory(provider, provider2);
    }

    public static RowInflaterTask newInstance(SystemClock systemClock, RowInflaterTaskLogger rowInflaterTaskLogger) {
        return new RowInflaterTask(systemClock, rowInflaterTaskLogger);
    }

    @Override // javax.inject.Provider
    public RowInflaterTask get() {
        return newInstance((SystemClock) this.systemClockProvider.get(), (RowInflaterTaskLogger) this.loggerProvider.get());
    }
}
