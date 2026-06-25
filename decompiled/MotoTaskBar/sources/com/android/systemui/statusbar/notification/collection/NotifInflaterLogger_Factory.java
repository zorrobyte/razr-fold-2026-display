package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifInflaterLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotifInflaterLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotifInflaterLogger_Factory create(Provider provider) {
        return new NotifInflaterLogger_Factory(provider);
    }

    public static NotifInflaterLogger newInstance(LogBuffer logBuffer) {
        return new NotifInflaterLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotifInflaterLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
