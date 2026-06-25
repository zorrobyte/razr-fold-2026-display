package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GroupCoalescer_Factory implements Factory {
    private final Provider clockProvider;
    private final Provider loggerProvider;
    private final Provider mainExecutorProvider;

    public GroupCoalescer_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.mainExecutorProvider = provider;
        this.clockProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static GroupCoalescer_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new GroupCoalescer_Factory(provider, provider2, provider3);
    }

    public static GroupCoalescer newInstance(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger) {
        return new GroupCoalescer(delayableExecutor, systemClock, groupCoalescerLogger);
    }

    @Override // javax.inject.Provider
    public GroupCoalescer get() {
        return newInstance((DelayableExecutor) this.mainExecutorProvider.get(), (SystemClock) this.clockProvider.get(), (GroupCoalescerLogger) this.loggerProvider.get());
    }
}
