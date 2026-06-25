package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RowContentBindStageLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public RowContentBindStageLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static RowContentBindStageLogger_Factory create(Provider provider) {
        return new RowContentBindStageLogger_Factory(provider);
    }

    public static RowContentBindStageLogger newInstance(LogBuffer logBuffer) {
        return new RowContentBindStageLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public RowContentBindStageLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
