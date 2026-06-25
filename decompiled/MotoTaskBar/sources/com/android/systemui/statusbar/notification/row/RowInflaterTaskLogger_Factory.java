package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RowInflaterTaskLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public RowInflaterTaskLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static RowInflaterTaskLogger_Factory create(Provider provider) {
        return new RowInflaterTaskLogger_Factory(provider);
    }

    public static RowInflaterTaskLogger newInstance(LogBuffer logBuffer) {
        return new RowInflaterTaskLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public RowInflaterTaskLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
