package com.android.systemui.statusbar.notification.collection.coalescer;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GroupCoalescerLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public GroupCoalescerLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static GroupCoalescerLogger_Factory create(Provider provider) {
        return new GroupCoalescerLogger_Factory(provider);
    }

    public static GroupCoalescerLogger newInstance(LogBuffer logBuffer) {
        return new GroupCoalescerLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public GroupCoalescerLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
