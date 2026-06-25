package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifBindPipelineLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public NotifBindPipelineLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static NotifBindPipelineLogger_Factory create(Provider provider) {
        return new NotifBindPipelineLogger_Factory(provider);
    }

    public static NotifBindPipelineLogger newInstance(LogBuffer logBuffer) {
        return new NotifBindPipelineLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public NotifBindPipelineLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
